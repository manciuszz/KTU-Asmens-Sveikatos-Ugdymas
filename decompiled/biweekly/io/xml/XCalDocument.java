package biweekly.io.xml;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.ICalendar;
import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.io.CannotParseException;
import biweekly.io.ParseWarnings;
import biweekly.io.SkipMeException;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.component.ICalendarScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.scribe.property.ICalPropertyScribe.Result;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.property.Xml;
import biweekly.util.IOUtils;
import biweekly.util.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XCalDocument {
    private static final ICalendarScribe icalMarshaller = ScribeIndex.getICalendarScribe();
    private static final XCalNamespaceContext nsContext = new XCalNamespaceContext("xcal");
    private final Document document;
    private ScribeIndex index;
    private final Map<String, ICalDataType> parameterDataTypes;
    private final List<ParseWarnings> parseWarnings;
    private Element root;

    public XCalDocument(String xml) throws SAXException {
        this(XmlUtils.toDocument(xml));
    }

    public XCalDocument(InputStream in) throws SAXException, IOException {
        this(XmlUtils.toDocument(in));
    }

    public XCalDocument(File file) throws SAXException, IOException {
        this(readFile(file));
    }

    private static Document readFile(File file) throws SAXException, IOException {
        InputStream in = new FileInputStream(file);
        try {
            Document toDocument = XmlUtils.toDocument(in);
            return toDocument;
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public XCalDocument(Reader reader) throws SAXException, IOException {
        this(XmlUtils.toDocument(reader));
    }

    public XCalDocument(Document document) {
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
        this.index = new ScribeIndex();
        this.parseWarnings = new ArrayList();
        this.document = document;
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(nsContext);
        try {
            this.root = (Element) xpath.evaluate("//" + nsContext.getPrefix() + ":" + XCalQNames.ICALENDAR.getLocalPart(), document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
        }
    }

    public XCalDocument() {
        this(createRoot());
    }

    private static Document createRoot() {
        Document document = XmlUtils.createDocument();
        document.appendChild(document.createElementNS(XCalQNames.ICALENDAR.getNamespaceURI(), XCalQNames.ICALENDAR.getLocalPart()));
        return document;
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

    public void setScribeIndex(ScribeIndex index) {
        this.index = index;
    }

    public void registerParameterDataType(String parameterName, ICalDataType dataType) {
        parameterName = parameterName.toLowerCase();
        if (dataType == null) {
            this.parameterDataTypes.remove(parameterName);
        } else {
            this.parameterDataTypes.put(parameterName, dataType);
        }
    }

    public Document getDocument() {
        return this.document;
    }

    public List<List<String>> getParseWarnings() {
        List<List<String>> warnings = new ArrayList();
        for (ParseWarnings pw : this.parseWarnings) {
            warnings.add(pw.copy());
        }
        return warnings;
    }

    public List<ICalendar> parseAll() {
        this.parseWarnings.clear();
        if (this.root == null) {
            return Collections.emptyList();
        }
        List<ICalendar> icals = new ArrayList();
        for (Element vcalendarElement : getVCalendarElements()) {
            ParseWarnings warnings = new ParseWarnings();
            icals.add(parseICal(vcalendarElement, warnings));
            this.parseWarnings.add(warnings);
        }
        return icals;
    }

    public ICalendar parseFirst() {
        this.parseWarnings.clear();
        if (this.root == null) {
            return null;
        }
        ParseWarnings warnings = new ParseWarnings();
        this.parseWarnings.add(warnings);
        List<Element> vcalendarElements = getVCalendarElements();
        if (vcalendarElements.isEmpty()) {
            return null;
        }
        return parseICal((Element) vcalendarElements.get(0), warnings);
    }

    public void add(ICalendar ical) {
        this.index.hasScribesFor(ical);
        Element element = buildComponentElement(ical);
        if (this.root == null) {
            this.root = buildElement(XCalQNames.ICALENDAR);
            Element documentRoot = XmlUtils.getRootElement(this.document);
            if (documentRoot == null) {
                this.document.appendChild(this.root);
            } else {
                documentRoot.appendChild(this.root);
            }
        }
        this.root.appendChild(element);
    }

    public String write() {
        return write(-1);
    }

    public String write(int indent) {
        Writer sw = new StringWriter();
        try {
            write(sw, indent);
        } catch (TransformerException e) {
        }
        return sw.toString();
    }

    public void write(OutputStream out) throws TransformerException {
        write(out, -1);
    }

    public void write(OutputStream out, int indent) throws TransformerException {
        write(IOUtils.utf8Writer(out), indent);
    }

    public void write(File file) throws TransformerException, IOException {
        write(file, -1);
    }

    public void write(File file, int indent) throws TransformerException, IOException {
        Writer writer = IOUtils.utf8Writer(file);
        try {
            write(writer, indent);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public void write(Writer writer) throws TransformerException {
        write(writer, -1);
    }

    public void write(Writer writer, int indent) throws TransformerException {
        Map<String, String> properties = new HashMap();
        if (indent >= 0) {
            properties.put("indent", "yes");
            properties.put("{http://xml.apache.org/xslt}indent-amount", indent + "");
        }
        XmlUtils.toWriter(this.document, writer, properties);
    }

    private Element buildComponentElement(ICalComponent component) {
        ICalComponentScribe componentScribe = this.index.getComponentScribe(component);
        Element componentElement = buildElement(componentScribe.getComponentName().toLowerCase());
        Element propertiesWrapperElement = buildElement(XCalQNames.PROPERTIES);
        for (ICalProperty property : componentScribe.getProperties(component)) {
            Element propertyElement = buildPropertyElement(property);
            if (propertyElement != null) {
                propertiesWrapperElement.appendChild(propertyElement);
            }
        }
        if (propertiesWrapperElement.hasChildNodes()) {
            componentElement.appendChild(propertiesWrapperElement);
        }
        Element componentsWrapperElement = buildElement(XCalQNames.COMPONENTS);
        for (ICalComponent subComponent : componentScribe.getComponents(component)) {
            Element subComponentElement = buildComponentElement(subComponent);
            if (subComponentElement != null) {
                componentsWrapperElement.appendChild(subComponentElement);
            }
        }
        if (componentsWrapperElement.hasChildNodes()) {
            componentElement.appendChild(componentsWrapperElement);
        }
        return componentElement;
    }

    private Element buildPropertyElement(ICalProperty property) {
        Element propertyElement;
        ICalParameters parameters;
        if (property instanceof Xml) {
            Document value = (Document) ((Xml) property).getValue();
            if (value == null) {
                return null;
            }
            propertyElement = (Element) this.document.importNode(XmlUtils.getRootElement(value), true);
            parameters = property.getParameters();
        } else {
            ICalPropertyScribe propertyScribe = this.index.getPropertyScribe(property);
            propertyElement = buildElement(propertyScribe.getQName());
            try {
                propertyScribe.writeXml(property, propertyElement);
                parameters = propertyScribe.prepareParameters(property);
            } catch (SkipMeException e) {
                return null;
            }
        }
        Element parametersWrapperElement = buildParametersElement(parameters);
        if (!parametersWrapperElement.hasChildNodes()) {
            return propertyElement;
        }
        propertyElement.insertBefore(parametersWrapperElement, propertyElement.getFirstChild());
        return propertyElement;
    }

    private Element buildParametersElement(ICalParameters parameters) {
        Element parametersWrapperElement = buildElement(XCalQNames.PARAMETERS);
        Iterator it = parameters.iterator();
        while (it.hasNext()) {
            Entry<String, List<String>> parameter = (Entry) it.next();
            String name = ((String) parameter.getKey()).toLowerCase();
            ICalDataType dataType = (ICalDataType) this.parameterDataTypes.get(name);
            String dataTypeStr = dataType == null ? EnvironmentCompat.MEDIA_UNKNOWN : dataType.getName().toLowerCase();
            Element parameterElement = buildAndAppendElement(name, parametersWrapperElement);
            for (String parameterValue : (List) parameter.getValue()) {
                buildAndAppendElement(dataTypeStr, parameterElement).setTextContent(parameterValue);
            }
        }
        return parametersWrapperElement;
    }

    private ICalendar parseICal(Element icalElement, ParseWarnings warnings) {
        ICalComponent root = parseComponent(icalElement, warnings);
        if (root instanceof ICalendar) {
            return (ICalendar) root;
        }
        ICalComponent ical = (ICalendar) icalMarshaller.emptyInstance();
        ical.addComponent(root);
        return ical;
    }

    private ICalComponent parseComponent(Element componentElement, ParseWarnings warnings) {
        ICalComponent component = this.index.getComponentScribe(componentElement.getLocalName()).emptyInstance();
        for (Element propertyWrapperElement : getChildElements(componentElement, XCalQNames.PROPERTIES)) {
            for (Element propertyElement : XmlUtils.toElementList(propertyWrapperElement.getChildNodes())) {
                ICalProperty property = parseProperty(propertyElement, warnings);
                if (property != null) {
                    component.addProperty(property);
                }
            }
        }
        for (Element componentWrapperElement : getChildElements(componentElement, XCalQNames.COMPONENTS)) {
            for (Element subComponentElement : XmlUtils.toElementList(componentWrapperElement.getChildNodes())) {
                if (XCalNamespaceContext.XCAL_NS.equals(subComponentElement.getNamespaceURI())) {
                    component.addComponent(parseComponent(subComponentElement, warnings));
                }
            }
        }
        return component;
    }

    private ICalProperty parseProperty(Element propertyElement, ParseWarnings warnings) {
        Integer num = null;
        ICalParameters parameters = parseParameters(propertyElement);
        String propertyName = propertyElement.getLocalName();
        try {
            Result<? extends ICalProperty> result = this.index.getPropertyScribe(new QName(propertyElement.getNamespaceURI(), propertyName)).parseXml(propertyElement, parameters);
            for (Warning warning : result.getWarnings()) {
                warnings.add(null, propertyName, warning);
            }
            return result.getProperty();
        } catch (SkipMeException e) {
            warnings.add(num, propertyName, 0, e.getMessage());
            return num;
        } catch (CannotParseException e2) {
            warnings.add(num, propertyName, 16, e2.getMessage());
            return this.index.getPropertyScribe(Xml.class).parseXml(propertyElement, parameters).getProperty();
        }
    }

    private ICalParameters parseParameters(Element propertyElement) {
        ICalParameters parameters = new ICalParameters();
        for (Element parametersElement : getChildElements(propertyElement, XCalQNames.PARAMETERS)) {
            for (Element paramElement : XmlUtils.toElementList(parametersElement.getChildNodes())) {
                if (XCalNamespaceContext.XCAL_NS.equals(paramElement.getNamespaceURI())) {
                    String name = paramElement.getLocalName().toUpperCase();
                    List<Element> valueElements = XmlUtils.toElementList(paramElement.getChildNodes());
                    if (valueElements.isEmpty()) {
                        parameters.put(name, paramElement.getTextContent());
                    } else {
                        for (Element valueElement : valueElements) {
                            if (XCalNamespaceContext.XCAL_NS.equals(valueElement.getNamespaceURI())) {
                                parameters.put(name, valueElement.getTextContent());
                            }
                        }
                    }
                }
            }
        }
        return parameters;
    }

    private Element buildElement(String localName) {
        return buildElement(new QName(XCalNamespaceContext.XCAL_NS, localName));
    }

    private Element buildElement(QName qname) {
        return this.document.createElementNS(qname.getNamespaceURI(), qname.getLocalPart());
    }

    private Element buildAndAppendElement(String localName, Element parent) {
        return buildAndAppendElement(new QName(XCalNamespaceContext.XCAL_NS, localName), parent);
    }

    private Element buildAndAppendElement(QName qname, Element parent) {
        Element child = this.document.createElementNS(qname.getNamespaceURI(), qname.getLocalPart());
        parent.appendChild(child);
        return child;
    }

    private List<Element> getVCalendarElements() {
        return getChildElements(this.root, XCalQNames.VCALENDAR);
    }

    private List<Element> getChildElements(Element parent, QName qname) {
        List<Element> elements = new ArrayList();
        for (Element child : XmlUtils.toElementList(parent.getChildNodes())) {
            if (qname.equals(new QName(child.getNamespaceURI(), child.getLocalName()))) {
                elements.add(child);
            }
        }
        return elements;
    }

    public String toString() {
        return write(2);
    }
}
