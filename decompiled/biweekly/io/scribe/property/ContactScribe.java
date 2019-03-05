package biweekly.io.scribe.property;

import biweekly.property.Contact;

public class ContactScribe extends TextPropertyScribe<Contact> {
    public ContactScribe() {
        super(Contact.class, "CONTACT");
    }

    protected Contact newInstance(String value) {
        return new Contact(value);
    }
}
