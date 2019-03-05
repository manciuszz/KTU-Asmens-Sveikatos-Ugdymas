package biweekly.io.xml;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.ICalendar;
import biweekly.component.ICalComponent;
import biweekly.io.SkipMeException;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.property.Xml;
import biweekly.util.IOUtils;
import biweekly.util.StringUtils;
import biweekly.util.XmlUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class XCalWriter implements Closeable {
    private final Document DOC;
    private final TransformerHandler handler;
    private final boolean icalendarElementExists;
    private final String indent;
    private ScribeIndex index;
    private int level;
    private final Map<String, ICalDataType> parameterDataTypes;
    private boolean started;
    private boolean textNodeJustPrinted;
    private final Writer writer;

    public XCalWriter(OutputStream out) {
        this(IOUtils.utf8Writer(out));
    }

    public XCalWriter(OutputStream out, String indent) {
        this(IOUtils.utf8Writer(out), indent);
    }

    public XCalWriter(File file) throws IOException {
        this(IOUtils.utf8Writer(file));
    }

    public XCalWriter(File file, String indent) throws IOException {
        this(IOUtils.utf8Writer(file), indent);
    }

    public XCalWriter(Writer writer) {
        this(writer, null);
    }

    public XCalWriter(Writer writer, String indent) {
        this(writer, indent, null);
    }

    public XCalWriter(Node parent) {
        this(null, null, parent);
    }

    private XCalWriter(Writer writer, String indent, Node parent) {
        this.DOC = XmlUtils.createDocument();
        this.parameterDataTypes = new HashMap();
        registerParameterDataType(ICalParameters.CN, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.ALTREP, ICalDataType.URI);
        registerParameterDataType(ICalParameters.CUTYPE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.DELEGATED_FROM, ICalDataType.CAL_ADDRESS);
        registerParameterDataType(ICalParameters.DELEGATED_TO, ICalDataType.CAL_ADDRESS);
        registerParameterDataType(ICalParameters.DIR, ICalDataType.URI);
        registerParameterDataType(ICalParameters.ENCODING, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.FMTTYPE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.FBTYPE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.LANGUAGE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.MEMBER, ICalDataType.CAL_ADDRESS);
        registerParameterDataType(ICalParameters.PARTSTAT, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.RANGE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.RELATED, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.RELTYPE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.ROLE, ICalDataType.TEXT);
        registerParameterDataType(ICalParameters.RSVP, ICalDataType.BOOLEAN);
        registerParameterDataType(ICalParameters.SENT_BY, ICalDataType.CAL_ADDRESS);
        registerParameterDataType(ICalParameters.TZID, ICalDataType.TEXT);
        this.level = 0;
        this.textNodeJustPrinted = false;
        this.started = false;
        this.index = new ScribeIndex();
        this.writer = writer;
        this.indent = indent;
        if (parent instanceof Document) {
            Node root = parent.getFirstChild();
            if (root != null) {
                parent = root;
            }
        }
        this.icalendarElementExists = isICalendarElement(parent);
        try {
            this.handler = ((SAXTransformerFactory) TransformerFactory.newInstance()).newTransformerHandler();
            this.handler.setResult(writer == null ? new DOMResult(parent) : new StreamResult(writer));
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isICalendarElement(Node node) {
        if (node != null && (node instanceof Element) && XCalQNames.ICALENDAR.getNamespaceURI().equals(node.getNamespaceURI()) && XCalQNames.ICALENDAR.getLocalPart().equals(node.getLocalName())) {
            return true;
        }
        return false;
    }

    public void registerScribe(ICalPropertyScribe<? extends ICalProperty> scribe) {
        this.index.register((ICalPropertyScribe) scribe);
    }

    public void registerScribe(ICalComponentScribe<? extends ICalComponent> scribe) {
        this.index.register((ICalComponentScribe) scribe);
    }

    public ScribeIndex getScribeIndex() {
        return this.index;
    }

    public void setScribeIndex(ScribeIndex scribe) {
        this.index = scribe;
    }

    public void write(ICalendar ical) throws SAXException {
        this.index.hasScribesFor(ical);
        if (!this.started) {
            this.handler.startDocument();
            if (!this.icalendarElementExists) {
                start(XCalQNames.ICALENDAR);
                this.level++;
            }
            this.started = true;
        }
        write((ICalComponent) ical);
    }

    public void registerParameterDataType(String parameterName, ICalDataType dataType) {
        parameterName = parameterName.toLowerCase();
        if (dataType == null) {
            this.parameterDataTypes.remove(parameterName);
        } else {
            this.parameterDataTypes.put(parameterName, dataType);
        }
    }

    private void write(ICalComponent component) throws SAXException {
        ICalComponentScribe scribe = this.index.getComponentScribe(component);
        String name = scribe.getComponentName().toLowerCase();
        start(name);
        this.level++;
        if (!scribe.getProperties(component).isEmpty()) {
            start(XCalQNames.PROPERTIES);
            this.level++;
            for (ICalProperty property : scribe.getProperties(component)) {
                write(property);
            }
            this.level--;
            end(XCalQNames.PROPERTIES);
        }
        if (!scribe.getComponents(component).isEmpty()) {
            start(XCalQNames.COMPONENTS);
            this.level++;
            for (ICalComponent subComponent : scribe.getComponents(component)) {
                write(subComponent);
            }
            this.level--;
            end(XCalQNames.COMPONENTS);
        }
        this.level--;
        end(name);
    }

    private void write(ICalProperty property) throws SAXException {
        Element propertyElement;
        ICalPropertyScribe scribe = this.index.getPropertyScribe(property);
        ICalParameters parameters = scribe.prepareParameters(property);
        if (property instanceof Xml) {
            Document value = (Document) ((Xml) property).getValue();
            if (value != null) {
                propertyElement = XmlUtils.getRootElement(value);
            } else {
                return;
            }
        }
        QName qname = scribe.getQName();
        propertyElement = this.DOC.createElementNS(qname.getNamespaceURI(), qname.getLocalPart());
        try {
            scribe.writeXml(property, propertyElement);
        } catch (SkipMeException e) {
            return;
        }
        start(propertyElement);
        this.level++;
        write(parameters);
        write(propertyElement);
        this.level--;
        end(propertyElement);
    }

    private void write(Element propertyElement) throws SAXException {
        NodeList children = propertyElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                Element element = (Element) child;
                if (element.hasChildNodes()) {
                    start(element);
                    this.level++;
                    write(element);
                    this.level--;
                    end(element);
                } else {
                    childless(element);
                }
            } else if (child instanceof Text) {
                text(((Text) child).getTextContent());
            }
        }
    }

    private void write(ICalParameters parameters) throws SAXException {
        if (!parameters.isEmpty()) {
            start(XCalQNames.PARAMETERS);
            this.level++;
            Iterator it = parameters.iterator();
            while (it.hasNext()) {
                Entry<String, List<String>> parameter = (Entry) it.next();
                String parameterName = ((String) parameter.getKey()).toLowerCase();
                start(parameterName);
                this.level++;
                for (String parameterValue : (List) parameter.getValue()) {
                    ICalDataType dataType = (ICalDataType) this.parameterDataTypes.get(parameterName);
                    String dataTypeElementName = dataType == null ? EnvironmentCompat.MEDIA_UNKNOWN : dataType.getName().toLowerCase();
                    start(dataTypeElementName);
                    text(parameterValue);
                    end(dataTypeElementName);
                }
                this.level--;
                end(parameterName);
            }
            this.level--;
            end(XCalQNames.PARAMETERS);
        }
    }

    private void indent() throws SAXException {
        if (this.indent != null) {
            StringBuilder sb = new StringBuilder(StringUtils.NEWLINE);
            for (int i = 0; i < this.level; i++) {
                sb.append(this.indent);
            }
            String str = sb.toString();
            this.handler.ignorableWhitespace(str.toCharArray(), 0, str.length());
        }
    }

    private void childless(Element element) throws SAXException {
        Attributes attributes = getElementAttributes(element);
        indent();
        this.handler.startElement(element.getNamespaceURI(), "", element.getLocalName(), attributes);
        this.handler.endElement(element.getNamespaceURI(), "", element.getLocalName());
    }

    private void start(Element element) throws SAXException {
        start(element.getNamespaceURI(), element.getLocalName(), getElementAttributes(element));
    }

    private void start(String element) throws SAXException {
        start(element, null);
    }

    private void start(QName qname) throws SAXException {
        start(qname, null);
    }

    private void start(QName qname, Attributes attributes) throws SAXException {
        start(qname.getNamespaceURI(), qname.getLocalPart(), attributes);
    }

    private void start(String element, Attributes attributes) throws SAXException {
        start(XCalNamespaceContext.XCAL_NS, element, attributes);
    }

    private void start(String namespace, String element, Attributes attributes) throws SAXException {
        indent();
        this.handler.startElement(namespace, "", element, attributes);
    }

    private void end(Element element) throws SAXException {
        end(element.getNamespaceURI(), element.getLocalName());
    }

    private void end(String element) throws SAXException {
        end(XCalNamespaceContext.XCAL_NS, element);
    }

    private void end(QName qname) throws SAXException {
        end(qname.getNamespaceURI(), qname.getLocalPart());
    }

    private void end(String namespace, String element) throws SAXException {
        if (!this.textNodeJustPrinted) {
            indent();
        }
        this.handler.endElement(namespace, "", element);
        this.textNodeJustPrinted = false;
    }

    private void text(String text) throws SAXException {
        this.handler.characters(text.toCharArray(), 0, text.length());
        this.textNodeJustPrinted = true;
    }

    private Attributes getElementAttributes(Element element) {
        AttributesImpl attributes = new AttributesImpl();
        NamedNodeMap attributeNodes = element.getAttributes();
        for (int i = 0; i < attributeNodes.getLength(); i++) {
            Node node = attributeNodes.item(i);
            attributes.addAttribute(node.getNamespaceURI(), "", node.getLocalName(), "", node.getNodeValue());
        }
        return attributes;
    }

    public void close() throws IOException {
        try {
            if (!this.started) {
                this.handler.startDocument();
                if (!this.icalendarElementExists) {
                    start(XCalQNames.ICALENDAR);
                    this.level++;
                }
            }
            if (!this.icalendarElementExists) {
                this.level--;
                end(XCalQNames.ICALENDAR);
            }
            this.handler.endDocument();
            if (this.writer != null) {
                this.writer.close();
            }
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }
}
