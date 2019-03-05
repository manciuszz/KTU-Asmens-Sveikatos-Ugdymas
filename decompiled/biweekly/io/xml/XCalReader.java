package biweekly.io.xml;

import biweekly.ICalendar;
import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.io.CannotParseException;
import biweekly.io.ParseWarnings;
import biweekly.io.SkipMeException;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.scribe.property.ICalPropertyScribe.Result;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.property.Xml;
import biweekly.util.XmlUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.xml.namespace.QName;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XCalReader implements Closeable {
    private volatile ScribeIndex index;
    private final Object lock;
    private volatile ICalendar readICal;
    private final BlockingQueue<Object> readerBlock;
    private final Source source;
    private final Closeable stream;
    private final ReadThread thread;
    private final BlockingQueue<Object> threadBlock;
    private volatile TransformerException thrown;
    private final ParseWarnings warnings;

    private class ContentHandlerImpl extends DefaultHandler {
        private final Document DOC;
        private final StringBuilder characterBuffer;
        private final LinkedList<ICalComponent> componentStack;
        private ICalComponent curComponent;
        private QName paramName;
        private ICalParameters parameters;
        private Element parent;
        private Element propertyElement;
        private final XCalStructure structure;

        private ContentHandlerImpl() {
            this.DOC = XmlUtils.createDocument();
            this.structure = new XCalStructure();
            this.characterBuffer = new StringBuilder();
            this.componentStack = new LinkedList();
        }

        public void characters(char[] buffer, int start, int length) throws SAXException {
            this.characterBuffer.append(buffer, start, length);
        }

        public void startElement(String namespace, String localName, String qName, Attributes attributes) throws SAXException {
            QName qname = new QName(namespace, localName);
            String textContent = this.characterBuffer.toString();
            this.characterBuffer.setLength(0);
            if (!this.structure.isEmpty()) {
                ElementType parentType = this.structure.peek();
                ElementType typeToPush = null;
                if (parentType != null) {
                    switch (parentType) {
                        case icalendar:
                            if (XCalQNames.VCALENDAR.equals(qname)) {
                                ICalComponent component = XCalReader.this.index.getComponentScribe(localName).emptyInstance();
                                this.curComponent = component;
                                XCalReader.this.readICal = (ICalendar) component;
                                typeToPush = ElementType.component;
                                break;
                            }
                            break;
                        case component:
                            if (!XCalQNames.PROPERTIES.equals(qname)) {
                                if (XCalQNames.COMPONENTS.equals(qname)) {
                                    this.componentStack.add(this.curComponent);
                                    this.curComponent = null;
                                    typeToPush = ElementType.components;
                                    break;
                                }
                            }
                            typeToPush = ElementType.properties;
                            break;
                            break;
                        case components:
                            if (XCalNamespaceContext.XCAL_NS.equals(namespace)) {
                                this.curComponent = XCalReader.this.index.getComponentScribe(localName).emptyInstance();
                                ((ICalComponent) this.componentStack.getLast()).addComponent(this.curComponent);
                                typeToPush = ElementType.component;
                                break;
                            }
                            break;
                        case properties:
                            this.propertyElement = createElement(namespace, localName, attributes);
                            this.parameters = new ICalParameters();
                            this.parent = this.propertyElement;
                            typeToPush = ElementType.property;
                            break;
                        case property:
                            if (XCalQNames.PARAMETERS.equals(qname)) {
                                typeToPush = ElementType.parameters;
                                break;
                            }
                            break;
                        case parameters:
                            if (XCalNamespaceContext.XCAL_NS.equals(namespace)) {
                                this.paramName = qname;
                                typeToPush = ElementType.parameter;
                                break;
                            }
                            break;
                        case parameter:
                            if (XCalNamespaceContext.XCAL_NS.equals(namespace)) {
                                typeToPush = ElementType.parameterValue;
                                break;
                            }
                            break;
                    }
                }
                if (!(this.propertyElement == null || typeToPush == ElementType.property || typeToPush == ElementType.parameters || this.structure.isUnderParameters())) {
                    if (textContent.length() > 0) {
                        this.parent.appendChild(this.DOC.createTextNode(textContent));
                    }
                    Element element = createElement(namespace, localName, attributes);
                    this.parent.appendChild(element);
                    this.parent = element;
                }
                this.structure.push(typeToPush);
            } else if (XCalQNames.ICALENDAR.equals(qname)) {
                this.structure.push(ElementType.icalendar);
            }
        }

        public void endElement(String namespace, String localName, String qName) throws SAXException {
            String textContent = this.characterBuffer.toString();
            this.characterBuffer.setLength(0);
            if (!this.structure.isEmpty()) {
                ElementType type = this.structure.pop();
                if (type != null || (this.propertyElement != null && !this.structure.isUnderParameters())) {
                    if (type != null) {
                        switch (type) {
                            case component:
                                this.curComponent = null;
                                if (XCalQNames.VCALENDAR.getNamespaceURI().equals(namespace) && XCalQNames.VCALENDAR.getLocalPart().equals(localName)) {
                                    try {
                                        XCalReader.this.readerBlock.put(XCalReader.this.lock);
                                        XCalReader.this.threadBlock.take();
                                        return;
                                    } catch (InterruptedException e) {
                                        throw new SAXException(e);
                                    }
                                }
                            case components:
                                this.curComponent = (ICalComponent) this.componentStack.removeLast();
                                break;
                            case property:
                                this.propertyElement.appendChild(this.DOC.createTextNode(textContent));
                                String propertyName = localName;
                                try {
                                    Result<? extends ICalProperty> result = XCalReader.this.index.getPropertyScribe(new QName(this.propertyElement.getNamespaceURI(), this.propertyElement.getLocalName())).parseXml(this.propertyElement, this.parameters);
                                    this.curComponent.addProperty(result.getProperty());
                                    for (Warning warning : result.getWarnings()) {
                                        XCalReader.this.warnings.add(null, propertyName, warning);
                                    }
                                } catch (SkipMeException e2) {
                                    XCalReader.this.warnings.add(null, propertyName, 22, e2.getMessage());
                                } catch (CannotParseException e3) {
                                    String xml = XmlUtils.toString(this.propertyElement);
                                    XCalReader.this.warnings.add(null, propertyName, 33, xml, e3.getMessage());
                                    this.curComponent.addProperty(XCalReader.this.index.getPropertyScribe(Xml.class).parseXml(this.propertyElement, this.parameters).getProperty());
                                }
                                this.propertyElement = null;
                                break;
                            case parameterValue:
                                this.parameters.put(this.paramName.getLocalPart(), textContent);
                                break;
                        }
                    }
                    if (this.propertyElement != null && type != ElementType.property && type != ElementType.parameters && !this.structure.isUnderParameters()) {
                        if (textContent.length() > 0) {
                            this.parent.appendChild(this.DOC.createTextNode(textContent));
                        }
                        this.parent = (Element) this.parent.getParentNode();
                    }
                }
            }
        }

        private Element createElement(String namespace, String localName, Attributes attributes) {
            Element element = this.DOC.createElementNS(namespace, localName);
            for (int i = 0; i < attributes.getLength(); i++) {
                if (!attributes.getQName(i).startsWith("xmlns:")) {
                    element.setAttribute(attributes.getLocalName(i), attributes.getValue(i));
                }
            }
            return element;
        }
    }

    private enum ElementType {
        icalendar,
        components,
        properties,
        component,
        property,
        parameters,
        parameter,
        parameterValue
    }

    private class ReadThread extends Thread {
        private volatile boolean closed = false;
        private volatile boolean finished = false;
        private final SAXResult result;
        private volatile boolean started = false;
        private final Transformer transformer;

        public ReadThread() {
            setName(getClass().getSimpleName());
            try {
                this.transformer = TransformerFactory.newInstance().newTransformer();
                this.transformer.setErrorListener(new ErrorListener(XCalReader.this) {
                    public void error(TransformerException e) {
                    }

                    public void fatalError(TransformerException e) {
                    }

                    public void warning(TransformerException e) {
                    }
                });
                this.result = new SAXResult(new ContentHandlerImpl());
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        public void run() {
            this.started = true;
            try {
                this.transformer.transform(XCalReader.this.source, this.result);
                this.finished = true;
                try {
                    XCalReader.this.readerBlock.put(XCalReader.this.lock);
                } catch (InterruptedException e) {
                }
            } catch (TransformerException e2) {
                if (!XCalReader.this.thread.closed) {
                    XCalReader.this.thrown = e2;
                }
                this.finished = true;
                try {
                    XCalReader.this.readerBlock.put(XCalReader.this.lock);
                } catch (InterruptedException e3) {
                }
            } catch (Throwable th) {
                this.finished = true;
                try {
                    XCalReader.this.readerBlock.put(XCalReader.this.lock);
                } catch (InterruptedException e4) {
                }
            }
        }
    }

    private class XCalStructure {
        private final List<ElementType> stack;

        private XCalStructure() {
            this.stack = new ArrayList();
        }

        public ElementType pop() {
            return this.stack.isEmpty() ? null : (ElementType) this.stack.remove(this.stack.size() - 1);
        }

        public ElementType peek() {
            return this.stack.isEmpty() ? null : (ElementType) this.stack.get(this.stack.size() - 1);
        }

        public void push(ElementType type) {
            this.stack.add(type);
        }

        public boolean isUnderParameters() {
            ElementType nonNull = null;
            for (int i = this.stack.size() - 1; i >= 0; i--) {
                ElementType type = (ElementType) this.stack.get(i);
                if (type != null) {
                    nonNull = type;
                    break;
                }
            }
            if (nonNull == ElementType.parameters || nonNull == ElementType.parameter || nonNull == ElementType.parameterValue) {
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            return this.stack.isEmpty();
        }
    }

    public XCalReader(String str) {
        this(new StringReader(str));
    }

    public XCalReader(InputStream in) {
        this.warnings = new ParseWarnings();
        this.index = new ScribeIndex();
        this.thread = new ReadThread();
        this.lock = new Object();
        this.readerBlock = new ArrayBlockingQueue(1);
        this.threadBlock = new ArrayBlockingQueue(1);
        this.source = new StreamSource(in);
        this.stream = in;
    }

    public XCalReader(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    public XCalReader(Reader reader) {
        this.warnings = new ParseWarnings();
        this.index = new ScribeIndex();
        this.thread = new ReadThread();
        this.lock = new Object();
        this.readerBlock = new ArrayBlockingQueue(1);
        this.threadBlock = new ArrayBlockingQueue(1);
        this.source = new StreamSource(reader);
        this.stream = reader;
    }

    public XCalReader(Node node) {
        this.warnings = new ParseWarnings();
        this.index = new ScribeIndex();
        this.thread = new ReadThread();
        this.lock = new Object();
        this.readerBlock = new ArrayBlockingQueue(1);
        this.threadBlock = new ArrayBlockingQueue(1);
        this.source = new DOMSource(node);
        this.stream = null;
    }

    public void registerScribe(ICalComponentScribe<? extends ICalComponent> scribe) {
        this.index.register((ICalComponentScribe) scribe);
    }

    public void registerScribe(ICalPropertyScribe<? extends ICalProperty> scribe) {
        this.index.register((ICalPropertyScribe) scribe);
    }

    public ScribeIndex getScribeIndex() {
        return this.index;
    }

    public void setScribeIndex(ScribeIndex index) {
        this.index = index;
    }

    public List<String> getWarnings() {
        return this.warnings.copy();
    }

    public ICalendar readNext() throws TransformerException {
        this.readICal = null;
        this.warnings.clear();
        this.thrown = null;
        if (!this.thread.started) {
            this.thread.start();
        } else if (this.thread.finished || this.thread.closed) {
            return null;
        } else {
            try {
                this.threadBlock.put(this.lock);
            } catch (InterruptedException e) {
                return null;
            }
        }
        try {
            this.readerBlock.take();
            if (this.thrown == null) {
                return this.readICal;
            }
            throw this.thrown;
        } catch (InterruptedException e2) {
            return null;
        }
    }

    public void close() throws IOException {
        if (this.thread.isAlive()) {
            this.thread.closed = true;
            this.thread.interrupt();
        }
        if (this.stream != null) {
            this.stream.close();
        }
    }
}
