package biweekly.property;

public class Organizer extends TextProperty {
    public Organizer(String uri) {
        super(uri);
    }

    public static Organizer email(String email) {
        return new Organizer("mailto:" + email);
    }

    public String getSentBy() {
        return super.getSentBy();
    }

    public void setSentBy(String sentBy) {
        super.setSentBy(sentBy);
    }

    public String getCommonName() {
        return super.getCommonName();
    }

    public void setCommonName(String commonName) {
        super.setCommonName(commonName);
    }

    public String getDirectoryEntry() {
        return super.getDirectoryEntry();
    }

    public void setDirectoryEntry(String directoryEntry) {
        super.setDirectoryEntry(directoryEntry);
    }

    public String getLanguage() {
        return super.getLanguage();
    }

    public void setLanguage(String language) {
        super.setLanguage(language);
    }
}
