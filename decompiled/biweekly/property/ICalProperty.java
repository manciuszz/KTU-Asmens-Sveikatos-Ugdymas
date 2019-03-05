package biweekly.property;

import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.component.VTimezone;
import biweekly.parameter.ICalParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ICalProperty {
    protected ICalParameters parameters = new ICalParameters();

    public ICalParameters getParameters() {
        return this.parameters;
    }

    public void setParameters(ICalParameters parameters) {
        this.parameters = parameters;
    }

    public String getParameter(String name) {
        return (String) this.parameters.first(name);
    }

    public List<String> getParameters(String name) {
        return this.parameters.get(name);
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public void setParameter(String name, String value) {
        this.parameters.replace((Object) name, (Object) value);
    }

    public void setParameter(String name, Collection<String> values) {
        this.parameters.replace((Object) name, (Collection) values);
    }

    public void removeParameter(String name) {
        this.parameters.removeAll(name);
    }

    String getAltRepresentation() {
        return this.parameters.getAltRepresentation();
    }

    void setAltRepresentation(String uri) {
        this.parameters.setAltRepresentation(uri);
    }

    String getFormatType() {
        return this.parameters.getFormatType();
    }

    void setFormatType(String formatType) {
        this.parameters.setFormatType(formatType);
    }

    String getLanguage() {
        return this.parameters.getLanguage();
    }

    void setLanguage(String language) {
        this.parameters.setLanguage(language);
    }

    String getTimezoneId() {
        return this.parameters.getTimezoneId();
    }

    void setTimezoneId(String timezoneId) {
        this.parameters.setTimezoneId(timezoneId);
    }

    void setTimezone(VTimezone timezone) {
        if (timezone == null) {
            setTimezoneId(null);
            return;
        }
        TimezoneId tzid = timezone.getTimezoneId();
        if (tzid != null) {
            setTimezoneId((String) tzid.getValue());
        }
    }

    String getSentBy() {
        return this.parameters.getSentBy();
    }

    void setSentBy(String uri) {
        this.parameters.setSentBy(uri);
    }

    String getCommonName() {
        return this.parameters.getCommonName();
    }

    void setCommonName(String commonName) {
        this.parameters.setCommonName(commonName);
    }

    String getDirectoryEntry() {
        return this.parameters.getDirectoryEntry();
    }

    void setDirectoryEntry(String uri) {
        this.parameters.setDirectoryEntry(uri);
    }

    public final List<Warning> validate(List<ICalComponent> components) {
        List<Warning> warnings = new ArrayList(0);
        validate(components, warnings);
        warnings.addAll(this.parameters.validate());
        return warnings;
    }

    protected void validate(List<ICalComponent> list, List<Warning> list2) {
    }
}
