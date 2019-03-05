package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import java.util.List;

public class RequestStatus extends ICalProperty {
    private String description;
    private String exceptionText;
    private String statusCode;

    public RequestStatus(String statusCode) {
        setStatusCode(statusCode);
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptionText() {
        return this.exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    public String getLanguage() {
        return super.getLanguage();
    }

    public void setLanguage(String language) {
        super.setLanguage(language);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        if (this.statusCode == null) {
            warnings.add(Warning.validate(36, new Object[0]));
        }
    }
}
