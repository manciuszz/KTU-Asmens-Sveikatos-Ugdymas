package biweekly.property;

import biweekly.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Xml extends ValuedProperty<Document> {
    public Xml(String xml) throws SAXException {
        super(XmlUtils.toDocument(xml));
    }

    public Xml(Element element) {
        super(XmlUtils.createDocument());
        ((Document) this.value).appendChild(((Document) this.value).importNode(element, true));
    }

    public Xml(Document document) {
        super(document);
    }
}
