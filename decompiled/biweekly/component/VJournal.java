package biweekly.component;

import biweekly.Warning;
import biweekly.property.Attachment;
import biweekly.property.Attendee;
import biweekly.property.Categories;
import biweekly.property.Classification;
import biweekly.property.Comment;
import biweekly.property.Contact;
import biweekly.property.Created;
import biweekly.property.DateStart;
import biweekly.property.DateTimeStamp;
import biweekly.property.Description;
import biweekly.property.ExceptionDates;
import biweekly.property.ExceptionRule;
import biweekly.property.LastModified;
import biweekly.property.Organizer;
import biweekly.property.RecurrenceDates;
import biweekly.property.RecurrenceId;
import biweekly.property.RecurrenceRule;
import biweekly.property.RelatedTo;
import biweekly.property.RequestStatus;
import biweekly.property.Sequence;
import biweekly.property.Status;
import biweekly.property.Summary;
import biweekly.property.Uid;
import biweekly.property.Url;
import biweekly.util.Recurrence;
import java.util.Date;
import java.util.List;

public class VJournal extends ICalComponent {
    public VJournal() {
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

    public List<Description> getDescriptions() {
        return getProperties(Description.class);
    }

    public void addDescription(Description description) {
        addProperty(description);
    }

    public Description addDescription(String description) {
        Description prop = new Description(description);
        addDescription(prop);
        return prop;
    }

    public List<ExceptionDates> getExceptionDates() {
        return getProperties(ExceptionDates.class);
    }

    public void addExceptionDates(ExceptionDates exceptionDates) {
        addProperty(exceptionDates);
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

    public List<RecurrenceDates> getRecurrenceDates() {
        return getProperties(RecurrenceDates.class);
    }

    public void addRecurrenceDates(RecurrenceDates recurrenceDates) {
        addProperty(recurrenceDates);
    }

    public RequestStatus getRequestStatus() {
        return (RequestStatus) getProperty(RequestStatus.class);
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        setProperty(RequestStatus.class, requestStatus);
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

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        checkRequiredCardinality(warnings, Uid.class, DateTimeStamp.class);
        checkOptionalCardinality(warnings, Classification.class, Created.class, DateStart.class, LastModified.class, Organizer.class, RecurrenceId.class, Sequence.class, Status.class, Summary.class, Url.class);
        checkStatus(warnings, Status.draft(), Status.final_(), Status.cancelled());
        RecurrenceId recurrenceId = getRecurrenceId();
        DateStart dateStart = getDateStart();
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
