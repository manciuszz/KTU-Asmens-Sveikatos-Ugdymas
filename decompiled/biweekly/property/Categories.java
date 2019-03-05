package biweekly.property;

import java.util.List;

public class Categories extends ListProperty<String> {
    public Categories(String... categories) {
        super((Object[]) categories);
    }

    public Categories(List<String> categories) {
        super((List) categories);
    }

    public String getLanguage() {
        return super.getLanguage();
    }

    public void setLanguage(String language) {
        super.setLanguage(language);
    }
}
