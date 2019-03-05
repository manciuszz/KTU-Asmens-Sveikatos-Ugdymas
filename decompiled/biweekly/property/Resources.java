package biweekly.property;

import java.util.List;

public class Resources extends ListProperty<String> {
    public Resources(String... values) {
        super((Object[]) values);
    }

    public Resources(List<String> values) {
        super((List) values);
    }

    public String getAltRepresentation() {
        return super.getAltRepresentation();
    }

    public void setAltRepresentation(String uri) {
        super.setAltRepresentation(uri);
    }

    public String getLanguage() {
        return super.getLanguage();
    }

    public void setLanguage(String language) {
        super.setLanguage(language);
    }
}
