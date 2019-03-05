package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.io.xml.XCalNamespaceContext;
import biweekly.parameter.ICalParameters;
import biweekly.property.Xml;
import biweekly.util.XmlUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlScribe extends ICalPropertyScribe<Xml> {
    public XmlScribe() {
        super(Xml.class, "XML", ICalDataType.TEXT);
    }

    protected String _writeText(Xml property) {
        Document value = (Document) property.getValue();
        if (value != null) {
            return ICalPropertyScribe.escape(valueToString(value));
        }
        return "";
    }

    protected Xml _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        try {
            return new Xml(ICalPropertyScribe.unescape(value));
        } catch (SAXException e) {
            throw new CannotParseException(29, new Object[0]);
        }
    }

    protected void _writeXml(Xml property, XCalElement element) {
        super._writeXml(property, element);
    }

    protected Xml _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        Xml xml = new Xml(element.getElement());
        Element root = XmlUtils.getRootElement((Document) xml.getValue());
        for (Element child : XmlUtils.toElementList(root.getChildNodes())) {
            if ("parameters".equals(child.getLocalName()) && XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                root.removeChild(child);
            }
        }
        return xml;
    }

    protected JCalValue _writeJson(Xml property) {
        Document value = (Document) property.getValue();
        if (value != null) {
            return JCalValue.single(valueToString(value));
        }
        return JCalValue.single("");
    }

    protected Xml _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        try {
            return new Xml(value.asSingle());
        } catch (SAXException e) {
            throw new CannotParseException(29, new Object[0]);
        }
    }

    private String valueToString(Document document) {
        Map<String, String> props = new HashMap();
        props.put("omit-xml-declaration", "yes");
        return XmlUtils.toString(document, props);
    }
}
