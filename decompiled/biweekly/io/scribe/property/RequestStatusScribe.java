package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.RequestStatus;
import com.google.android.gms.plus.PlusShare;
import java.util.List;

public class RequestStatusScribe extends ICalPropertyScribe<RequestStatus> {
    public RequestStatusScribe() {
        super(RequestStatus.class, "REQUEST-STATUS", ICalDataType.TEXT);
    }

    protected String _writeText(RequestStatus property) {
        return ICalPropertyScribe.structured(property.getStatusCode(), property.getDescription(), property.getExceptionText());
    }

    protected RequestStatus _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        SemiStructuredIterator it = ICalPropertyScribe.semistructured(value, true);
        RequestStatus requestStatus = new RequestStatus(it.next());
        requestStatus.setDescription(it.next());
        requestStatus.setExceptionText(it.next());
        return requestStatus;
    }

    protected void _writeXml(RequestStatus property, XCalElement element) {
        element.append("code", property.getStatusCode());
        element.append(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, property.getDescription());
        String data = property.getExceptionText();
        if (data != null) {
            element.append("data", data);
        }
    }

    protected RequestStatus _parseXml(XCalElement element, ICalParameters parameters, List<Warning> list) {
        String code = element.first("code");
        if (code == null) {
            throw ICalPropertyScribe.missingXmlElements("code");
        }
        RequestStatus requestStatus = new RequestStatus(s(code));
        requestStatus.setDescription(s(element.first(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION)));
        requestStatus.setExceptionText(s(element.first("data")));
        return requestStatus;
    }

    protected JCalValue _writeJson(RequestStatus property) {
        return JCalValue.structured(property.getStatusCode(), property.getDescription(), property.getExceptionText());
    }

    protected RequestStatus _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> list) {
        StructuredIterator it = ICalPropertyScribe.structured(value);
        RequestStatus requestStatus = new RequestStatus(it.nextString());
        requestStatus.setDescription(it.nextString());
        requestStatus.setExceptionText(it.nextString());
        return requestStatus;
    }

    private String s(String str) {
        return (str == null || str.length() == 0) ? null : str;
    }
}
