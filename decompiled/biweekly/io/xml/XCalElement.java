package biweekly.io.xml;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.util.XmlUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XCalElement {
    private final Document document;
    private final Element element;

    public XCalElement(Element element) {
        this.element = element;
        this.document = element.getOwnerDocument();
    }

    public String first(ICalDataType dataType) {
        return first(toLocalName(dataType));
    }

    public String first(String localName) {
        for (Element child : children()) {
            if (localName.equals(child.getLocalName()) && XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                return child.getTextContent();
            }
        }
        return null;
    }

    public List<String> all(ICalDataType dataType) {
        return all(toLocalName(dataType));
    }

    public List<String> all(String localName) {
        List<String> childrenText = new ArrayList();
        for (Element child : children()) {
            if (localName.equals(child.getLocalName()) && XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                childrenText.add(child.getTextContent());
            }
        }
        return childrenText;
    }

    public Element append(ICalDataType dataType, String value) {
        return append(toLocalName(dataType), value);
    }

    public Element append(String name, String value) {
        Element child = this.document.createElementNS(XCalNamespaceContext.XCAL_NS, name);
        child.setTextContent(value);
        this.element.appendChild(child);
        return child;
    }

    public XCalElement append(String name) {
        return new XCalElement(append(name, (String) null));
    }

    public XCalElement append(ICalDataType dataType) {
        return append(dataType.getName().toLowerCase());
    }

    public List<Element> append(String name, Collection<String> values) {
        List<Element> elements = new ArrayList(values.size());
        for (String value : values) {
            elements.add(append(name, value));
        }
        return elements;
    }

    public Document document() {
        return this.document;
    }

    public Element getElement() {
        return this.element;
    }

    private List<Element> children() {
        return XmlUtils.toElementList(this.element.getChildNodes());
    }

    public List<XCalElement> children(ICalDataType dataType) {
        String localName = dataType.getName().toLowerCase();
        List<XCalElement> children = new ArrayList();
        for (Element child : children()) {
            if (localName.equals(child.getLocalName()) && XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                children.add(new XCalElement(child));
            }
        }
        return children;
    }

    public XCalElement child(ICalDataType dataType) {
        String localName = dataType.getName().toLowerCase();
        for (Element child : children()) {
            if (localName.equals(child.getLocalName()) && XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                return new XCalElement(child);
            }
        }
        return null;
    }

    private String toLocalName(ICalDataType dataType) {
        return dataType == null ? EnvironmentCompat.MEDIA_UNKNOWN : dataType.getName().toLowerCase();
    }
}
