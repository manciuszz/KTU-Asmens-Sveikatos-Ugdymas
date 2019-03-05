package biweekly.io.scribe.property;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.xml.XCalElement;
import biweekly.io.xml.XCalNamespaceContext;
import biweekly.parameter.ICalParameters;
import biweekly.property.RawProperty;
import biweekly.util.XmlUtils;
import java.util.List;
import org.w3c.dom.Element;

public class RawPropertyScribe extends ICalPropertyScribe<RawProperty> {
    public RawPropertyScribe(String propertyName) {
        super(RawProperty.class, propertyName, null);
    }

    protected ICalDataType _dataType(RawProperty property) {
        return property.getDataType();
    }

    protected String _writeText(RawProperty property) {
        String value = property.getValue();
        return value != null ? value : "";
    }

    protected RawProperty _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return new RawProperty(this.propertyName, dataType, value);
    }

    protected RawProperty _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        ICalDataType dataType = null;
        Element rawElement = element.getElement();
        String name = rawElement.getLocalName();
        for (Element child : XmlUtils.toElementList(rawElement.getChildNodes())) {
            if (XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                String dataTypeStr = child.getLocalName();
                if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(dataTypeStr)) {
                    dataType = ICalDataType.get(dataTypeStr);
                }
                return new RawProperty(name, dataType, child.getTextContent());
            }
        }
        return new RawProperty(name, null, rawElement.getTextContent());
    }
}
