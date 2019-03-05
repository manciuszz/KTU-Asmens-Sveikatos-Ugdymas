package biweekly.component;

import biweekly.Warning;
import biweekly.parameter.FreeBusyType;
import biweekly.property.Attendee;
import biweekly.property.Comment;
import biweekly.property.Contact;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.DateTimeStamp;
import biweekly.property.FreeBusy;
import biweekly.property.Organizer;
import biweekly.property.RequestStatus;
import biweekly.property.Uid;
import biweekly.property.Url;
import biweekly.util.Duration;
import java.util.Date;
import java.util.List;

public class VFreeBusy extends ICalComponent {
    public VFreeBusy() {
        setUid(Uid.random());
        setDateTimeStamp(new Date());
    }

    public Uid getUid() {
        return (Uid) getProperty(Uid.class);
    }

    public void setUid(Uid uid) {
        setProperty(Uid.class, uid);
    }

    public Uid setUid(String uid) {
        Uid prop = uid == null ? null : new Uid(uid);
        setUid(prop);
        return prop;
    }

    public DateTimeStamp getDateTimeStamp() {
        return (DateTimeStamp) getProperty(DateTimeStamp.class);
    }

    public void setDateTimeStamp(DateTimeStamp dateTimeStamp) {
        setProperty(DateTimeStamp.class, dateTimeStamp);
    }

    public DateTimeStamp setDateTimeStamp(Date dateTimeStamp) {
        DateTimeStamp prop = dateTimeStamp == null ? null : new DateTimeStamp(dateTimeStamp);
        setDateTimeStamp(prop);
        return prop;
    }

    public Contact getContact() {
        return (Contact) getProperty(Contact.class);
    }

    public void setContact(Contact contact) {
        setProperty(Contact.class, contact);
    }

    public Contact addContact(String contact) {
        Contact prop = new Contact(contact);
        setContact(prop);
        return prop;
    }

    public DateStart getDateStart() {
        return (DateStart) getProperty(DateStart.class);
    }

    public void setDateStart(DateStart dateStart) {
        setProperty(DateStart.class, dateStart);
    }

    public DateStart setDateStart(Date dateStart) {
        DateStart prop = dateStart == null ? null : new DateStart(dateStart);
        setDateStart(prop);
        return prop;
    }

    public DateEnd getDateEnd() {
        return (DateEnd) getProperty(DateEnd.class);
    }

    public void setDateEnd(DateEnd dateEnd) {
        setProperty(DateEnd.class, dateEnd);
    }

    public DateEnd setDateEnd(Date dateEnd) {
        DateEnd prop = dateEnd == null ? null : new DateEnd(dateEnd);
        setDateEnd(prop);
        return prop;
    }

    public Organizer getOrganizer() {
        return (Organizer) getProperty(Organizer.class);
    }

    public void setOrganizer(Organizer organizer) {
        setProperty(Organizer.class, organizer);
    }

    public Organizer setOrganizer(String email) {
        Organizer prop = email == null ? null : Organizer.email(email);
        setOrganizer(prop);
        return prop;
    }

    public Url getUrl() {
        return (Url) getProperty(Url.class);
    }

    public void setUrl(Url url) {
        setProperty(Url.class, url);
    }

    public Url setUrl(String url) {
        Url prop = url == null ? null : new Url(url);
        setUrl(prop);
        return prop;
    }

    public List<Attendee> getAttendees() {
        return getProperties(Attendee.class);
    }

    public void addAttendee(Attendee attendee) {
        addProperty(attendee);
    }

    public List<Comment> getComments() {
        return getProperties(Comment.class);
    }

    public void addComment(Comment comment) {
        addProperty(comment);
    }

    public Comment addComment(String comment) {
        Comment prop = new Comment(comment);
        addComment(prop);
        return prop;
    }

    public List<FreeBusy> getFreeBusy() {
        return getProperties(FreeBusy.class);
    }

    public void addFreeBusy(FreeBusy freeBusy) {
        addProperty(freeBusy);
    }

    public FreeBusy addFreeBusy(FreeBusyType type, Date start, Date end) {
        FreeBusy found = findByFbType(type);
        found.addValue(start, end);
        return found;
    }

    public FreeBusy addFreeBusy(FreeBusyType type, Date start, Duration duration) {
        FreeBusy found = findByFbType(type);
        found.addValue(start, duration);
        return found;
    }

    private FreeBusy findByFbType(FreeBusyType type) {
        FreeBusy found = null;
        for (FreeBusy fb : getFreeBusy()) {
            if (fb.getType() == type) {
                found = fb;
                break;
            }
        }
        if (found != null) {
            return found;
        }
        found = new FreeBusy();
        found.setType(type);
        addFreeBusy(found);
        return found;
    }

    public RequestStatus getRequestStatus() {
        return (RequestStatus) getProperty(RequestStatus.class);
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        setProperty(RequestStatus.class, requestStatus);
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        checkRequiredCardinality(warnings, Uid.class, DateTimeStamp.class);
        checkOptionalCardinality(warnings, Contact.class, DateStart.class, DateEnd.class, Organizer.class, Url.class);
        DateStart dateStart = getDateStart();
        DateEnd dateEnd = getDateEnd();
        if (dateEnd != null && dateStart == null) {
            warnings.add(Warning.validate(15, new Object[0]));
        }
        if (!(dateStart == null || dateStart.getValue() == null || dateStart.hasTime())) {
            warnings.add(Warning.validate(20, DateStart.class.getSimpleName()));
        }
        if (!(dateEnd == null || dateEnd.getValue() == null || dateEnd.hasTime())) {
            warnings.add(Warning.validate(20, DateEnd.class.getSimpleName()));
        }
        if (dateStart != null && dateEnd != null) {
            Date start = dateStart.getValue();
            Date end = dateEnd.getValue();
            if (start != null && end != null && start.compareTo(end) >= 0) {
                warnings.add(Warning.validate(16, new Object[0]));
            }
        }
    }
}
