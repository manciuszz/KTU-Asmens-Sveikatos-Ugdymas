package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.Version;
import java.util.List;

public class VersionScribe extends ICalPropertyScribe<Version> {
    public VersionScribe() {
        super(Version.class, "VERSION", ICalDataType.TEXT);
    }

    protected String _writeText(Version property) {
        StringBuilder sb = new StringBuilder();
        if (property.getMinVersion() != null) {
            sb.append(property.getMinVersion()).append(';');
        }
        if (property.getMaxVersion() != null) {
            sb.append(property.getMaxVersion());
        }
        return sb.toString();
    }

    protected Version _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        String max;
        SemiStructuredIterator it = ICalPropertyScribe.semistructured(value, true);
        String one = it.next();
        String two = it.next();
        String min = null;
        if (two == null) {
            max = one;
        } else {
            min = one;
            max = two;
        }
        return new Version(min, max);
    }

    protected void _writeXml(Version property, XCalElement element) {
        element.append(dataType(property), property.getMaxVersion());
    }

    protected Version _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String value = element.first(this.defaultDataType);
        if (value != null) {
            return new Version(value);
        }
        throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
    }

    protected JCalValue _writeJson(Version property) {
        return JCalValue.single(property.getMaxVersion());
    }

    protected Version _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        return new Version(value.asSingle());
    }
}
