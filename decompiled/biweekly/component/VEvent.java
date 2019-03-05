package biweekly.component;

import biweekly.Warning;
import biweekly.property.Attachment;
import biweekly.property.Attendee;
import biweekly.property.Categories;
import biweekly.property.Classification;
import biweekly.property.Comment;
import biweekly.property.Contact;
import biweekly.property.Created;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.DateTimeStamp;
import biweekly.property.Description;
import biweekly.property.DurationProperty;
import biweekly.property.ExceptionDates;
import biweekly.property.ExceptionRule;
import biweekly.property.Geo;
import biweekly.property.LastModified;
import biweekly.property.Location;
import biweekly.property.Method;
import biweekly.property.Organizer;
import biweekly.property.Priority;
import biweekly.property.RecurrenceDates;
import biweekly.property.RecurrenceId;
import biweekly.property.RecurrenceRule;
import biweekly.property.RelatedTo;
import biweekly.property.RequestStatus;
import biweekly.property.Resources;
import biweekly.property.Sequence;
import biweekly.property.Status;
import biweekly.property.Summary;
import biweekly.property.Transparency;
import biweekly.property.Uid;
import biweekly.property.Url;
import biweekly.util.Duration;
import biweekly.util.Recurrence;
import java.util.Date;
import java.util.List;

public class VEvent extends ICalComponent {
    public VEvent() {
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

    public Classification getClassification() {
        return (Classification) getProperty(Classification.class);
    }

    public void setClassification(Classification classification) {
        setProperty(Classification.class, classification);
    }

    public Classification setClassification(String classification) {
        Classification prop = classification == null ? null : new Classification(classification);
        setClassification(prop);
        return prop;
    }

    public Description getDescription() {
        return (Description) getProperty(Description.class);
    }

    public void setDescription(Description description) {
        setProperty(Description.class, description);
    }

    public Description setDescription(String description) {
        Description prop = description == null ? null : new Description(description);
        setDescription(prop);
        return prop;
    }

    public Geo getGeo() {
        return (Geo) getProperty(Geo.class);
    }

    public void setGeo(Geo geo) {
        setProperty(Geo.class, geo);
    }

    public Location getLocation() {
        return (Location) getProperty(Location.class);
    }

    public void setLocation(Location location) {
        setProperty(Location.class, location);
    }

    public Location setLocation(String location) {
        Location prop = location == null ? null : new Location(location);
        setLocation(prop);
        return prop;
    }

    public Priority getPriority() {
        return (Priority) getProperty(Priority.class);
    }

    public void setPriority(Priority priority) {
        setProperty(Priority.class, priority);
    }

    public Priority setPriority(Integer priority) {
        Priority prop = priority == null ? null : new Priority(priority);
        setPriority(prop);
        return prop;
    }

    public Status getStatus() {
        return (Status) getProperty(Status.class);
    }

    public void setStatus(Status status) {
        setProperty(Status.class, status);
    }

    public Summary getSummary() {
        return (Summary) getProperty(Summary.class);
    }

    public void setSummary(Summary summary) {
        setProperty(Summary.class, summary);
    }

    public Summary setSummary(String summary) {
        Summary prop = summary == null ? null : new Summary(summary);
        setSummary(prop);
        return prop;
    }

    public Transparency getTransparency() {
        return (Transparency) getProperty(Transparency.class);
    }

    public void setTransparency(Transparency transparency) {
        setProperty(Transparency.class, transparency);
    }

    public Transparency setTransparency(Boolean transparent) {
        Transparency prop = null;
        if (transparent != null) {
            prop = transparent.booleanValue() ? Transparency.transparent() : Transparency.opaque();
        }
        setTransparency(prop);
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

    public RecurrenceId getRecurrenceId() {
        return (RecurrenceId) getProperty(RecurrenceId.class);
    }

    public void setRecurrenceId(RecurrenceId recurrenceId) {
        setProperty(RecurrenceId.class, recurrenceId);
    }

    public RecurrenceId setRecurrenceId(Date originalStartDate) {
        RecurrenceId prop = originalStartDate == null ? null : new RecurrenceId(originalStartDate);
        setRecurrenceId(prop);
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

    public RecurrenceRule getRecurrenceRule() {
        return (RecurrenceRule) getProperty(RecurrenceRule.class);
    }

    public RecurrenceRule setRecurrenceRule(Recurrence recur) {
        RecurrenceRule prop = recur == null ? null : new RecurrenceRule(recur);
        setRecurrenceRule(prop);
        return prop;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        setProperty(RecurrenceRule.class, recurrenceRule);
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

    public DurationProperty getDuration() {
        return (DurationProperty) getProperty(DurationProperty.class);
    }

    public void setDuration(DurationProperty duration) {
        setProperty(DurationProperty.class, duration);
    }

    public DurationProperty setDuration(Duration duration) {
        DurationProperty prop = duration == null ? null : new DurationProperty(duration);
        setDuration(prop);
        return prop;
    }

    public Created getCreated() {
        return (Created) getProperty(Created.class);
    }

    public void setCreated(Created created) {
        setProperty(Created.class, created);
    }

    public Created setCreated(Date created) {
        Created prop = created == null ? null : new Created(created);
        setCreated(prop);
        return prop;
    }

    public LastModified getLastModified() {
        return (LastModified) getProperty(LastModified.class);
    }

    public void setLastModified(LastModified lastModified) {
        setProperty(LastModified.class, lastModified);
    }

    public LastModified setLastModified(Date lastModified) {
        LastModified prop = lastModified == null ? null : new LastModified(lastModified);
        setLastModified(prop);
        return prop;
    }

    public Sequence getSequence() {
        return (Sequence) getProperty(Sequence.class);
    }

    public void setSequence(Sequence sequence) {
        setProperty(Sequence.class, sequence);
    }

    public Sequence setSequence(Integer sequence) {
        Sequence prop = sequence == null ? null : new Sequence(sequence);
        setSequence(prop);
        return prop;
    }

    public void incrementSequence() {
        Sequence sequence = getSequence();
        if (sequence == null) {
            setSequence(Integer.valueOf(1));
        } else {
            sequence.increment();
        }
    }

    public List<Attachment> getAttachments() {
        return getProperties(Attachment.class);
    }

    public void addAttachment(Attachment attachment) {
        addProperty(attachment);
    }

    public List<Attendee> getAttendees() {
        return getProperties(Attendee.class);
    }

    public void addAttendee(Attendee attendee) {
        addProperty(attendee);
    }

    public Attendee addAttendee(String email) {
        Attendee prop = Attendee.email(email);
        addAttendee(prop);
        return prop;
    }

    public List<Categories> getCategories() {
        return getProperties(Categories.class);
    }

    public void addCategories(Categories categories) {
        addProperty(categories);
    }

    public Categories addCategories(String... categories) {
        Categories prop = new Categories(categories);
        addCategories(prop);
        return prop;
    }

    public Categories addCategories(List<String> categories) {
        Categories prop = new Categories((List) categories);
        addCategories(prop);
        return prop;
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

    public List<Contact> getContacts() {
        return getProperties(Contact.class);
    }

    public void addContact(Contact contact) {
        addProperty(contact);
    }

    public Contact addContact(String contact) {
        Contact prop = new Contact(contact);
        addContact(prop);
        return prop;
    }

    public List<ExceptionDates> getExceptionDates() {
        return getProperties(ExceptionDates.class);
    }

    public void addExceptionDates(ExceptionDates exceptionDates) {
        addProperty(exceptionDates);
    }

    public RequestStatus getRequestStatus() {
        return (RequestStatus) getProperty(RequestStatus.class);
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        setProperty(RequestStatus.class, requestStatus);
    }

    public List<RelatedTo> getRelatedTo() {
        return getProperties(RelatedTo.class);
    }

    public void addRelatedTo(RelatedTo relatedTo) {
        addProperty(relatedTo);
    }

    public RelatedTo addRelatedTo(String uid) {
        RelatedTo prop = new RelatedTo(uid);
        addRelatedTo(prop);
        return prop;
    }

    public List<Resources> getResources() {
        return getProperties(Resources.class);
    }

    public void addResources(Resources resources) {
        addProperty(resources);
    }

    public Resources addResources(String... resources) {
        Resources prop = new Resources(resources);
        addResources(prop);
        return prop;
    }

    public Resources addResources(List<String> resources) {
        Resources prop = new Resources((List) resources);
        addResources(prop);
        return prop;
    }

    public List<RecurrenceDates> getRecurrenceDates() {
        return getProperties(RecurrenceDates.class);
    }

    public void addRecurrenceDates(RecurrenceDates recurrenceDates) {
        addProperty(recurrenceDates);
    }

    public List<VAlarm> getAlarms() {
        return getComponents(VAlarm.class);
    }

    public void addAlarm(VAlarm alarm) {
        addComponent(alarm);
    }

    public List<ExceptionRule> getExceptionRules() {
        return getProperties(ExceptionRule.class);
    }

    public ExceptionRule addExceptionRule(Recurrence recur) {
        ExceptionRule prop = new ExceptionRule(recur);
        addExceptionRule(prop);
        return prop;
    }

    public void addExceptionRule(ExceptionRule exceptionRule) {
        addProperty(exceptionRule);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        checkRequiredCardinality(warnings, Uid.class, DateTimeStamp.class);
        checkOptionalCardinality(warnings, Classification.class, Created.class, Description.class, Geo.class, LastModified.class, Location.class, Organizer.class, Priority.class, Status.class, Summary.class, Transparency.class, Url.class, RecurrenceId.class);
        checkStatus(warnings, Status.tentative(), Status.confirmed(), Status.cancelled());
        DateStart dateStart = getDateStart();
        DateEnd dateEnd = getDateEnd();
        ICalComponent ical = (ICalComponent) components.get(0);
        if (dateStart == null && ical.getProperty(Method.class) == null) {
            warnings.add(Warning.validate(14, new Object[0]));
        }
        if (dateEnd != null && dateStart == null) {
            warnings.add(Warning.validate(15, new Object[0]));
        }
        if (!(dateStart == null || dateEnd == null)) {
            Date start = dateStart.getValue();
            Date end = dateEnd.getValue();
            if (!(start == null || end == null || start.compareTo(end) <= 0)) {
                warnings.add(Warning.validate(16, new Object[0]));
            }
            if (dateStart.hasTime() != dateEnd.hasTime()) {
                warnings.add(Warning.validate(17, new Object[0]));
            }
        }
        if (!(dateEnd == null || getDuration() == null)) {
            warnings.add(Warning.validate(18, new Object[0]));
        }
        RecurrenceId recurrenceId = getRecurrenceId();
        if (!(recurrenceId == null || dateStart == null || dateStart.hasTime() == recurrenceId.hasTime())) {
            warnings.add(Warning.validate(19, new Object[0]));
        }
        RecurrenceRule rrule = getRecurrenceRule();
        if (!(dateStart == null || rrule == null)) {
            Recurrence recur = (Recurrence) rrule.getValue();
            if (!(dateStart.getValue() == null || recur == null || dateStart.hasTime() || (recur.getByHour().isEmpty() && recur.getByMinute().isEmpty() && recur.getBySecond().isEmpty()))) {
                warnings.add(Warning.validate(5, new Object[0]));
            }
        }
        if (getProperties(RecurrenceRule.class).size() > 1) {
            warnings.add(Warning.validate(6, new Object[0]));
        }
    }
}
