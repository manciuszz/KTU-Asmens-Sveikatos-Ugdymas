package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.Encoding;
import biweekly.parameter.ICalParameters;
import biweekly.property.Attachment;
import biweekly.util.Base64;
import java.util.List;

public class AttachmentScribe extends ICalPropertyScribe<Attachment> {
    public AttachmentScribe() {
        super(Attachment.class, "ATTACH", ICalDataType.URI);
    }

    protected void _prepareParameters(Attachment property, ICalParameters copy) {
        if (property.getUri() != null) {
            copy.setEncoding(null);
        } else if (property.getData() != null) {
            copy.setEncoding(Encoding.BASE64);
        }
    }

    protected ICalDataType _dataType(Attachment property) {
        if (property.getUri() != null) {
            return ICalDataType.URI;
        }
        if (property.getData() != null) {
            return ICalDataType.BINARY;
        }
        return this.defaultDataType;
    }

    protected String _writeText(Attachment property) {
        String uri = property.getUri();
        if (uri != null) {
            return uri;
        }
        byte[] data = property.getData();
        if (data != null) {
            return Base64.encode(data);
        }
        return "";
    }

    protected Attachment _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        value = ICalPropertyScribe.unescape(value);
        if (dataType == ICalDataType.BINARY || parameters.getEncoding() == Encoding.BASE64) {
            return new Attachment(null, Base64.decode(value));
        }
        return new Attachment(null, value);
    }

    protected void _writeXml(Attachment property, XCalElement element) {
        String uri = property.getUri();
        if (uri != null) {
            element.append(ICalDataType.URI, uri);
            return;
        }
        byte[] data = property.getData();
        if (data != null) {
            element.append(ICalDataType.BINARY, Base64.encode(data));
        } else {
            element.append(this.defaultDataType, "");
        }
    }

    protected Attachment _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String uri = element.first(ICalDataType.URI);
        if (uri != null) {
            return new Attachment(null, uri);
        }
        String base64Data = element.first(ICalDataType.BINARY);
        if (base64Data != null) {
            return new Attachment(null, Base64.decode(base64Data));
        }
        throw ICalPropertyScribe.missingXmlElements(ICalDataType.URI, ICalDataType.BINARY);
    }

    protected JCalValue _writeJson(Attachment property) {
        String uri = property.getUri();
        if (uri != null) {
            return JCalValue.single(uri);
        }
        byte[] data = property.getData();
        if (data != null) {
            return JCalValue.single(Base64.encode(data));
        }
        return JCalValue.single("");
    }

    protected Attachment _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        String valueStr = value.asSingle();
        if (dataType == ICalDataType.BINARY) {
            return new Attachment(null, Base64.decode(valueStr));
        }
        return new Attachment(null, valueStr);
    }
}
