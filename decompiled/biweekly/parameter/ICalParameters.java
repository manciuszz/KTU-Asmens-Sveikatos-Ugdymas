package biweekly.parameter;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.util.ListMultimap;
import java.util.ArrayList;
import java.util.List;

public class ICalParameters extends ListMultimap<String, String> {
    public static final String ALTREP = "ALTREP";
    public static final String CN = "CN";
    public static final String CUTYPE = "CUTYPE";
    public static final String DELEGATED_FROM = "DELEGATED-FROM";
    public static final String DELEGATED_TO = "DELEGATED-TO";
    public static final String DIR = "DIR";
    public static final String ENCODING = "ENCODING";
    public static final String FBTYPE = "FBTYPE";
    public static final String FMTTYPE = "FMTTYPE";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String MEMBER = "MEMBER";
    public static final String PARTSTAT = "PARTSTAT";
    public static final String RANGE = "RANGE";
    public static final String RELATED = "RELATED";
    public static final String RELTYPE = "RELTYPE";
    public static final String ROLE = "ROLE";
    public static final String RSVP = "RSVP";
    public static final String SENT_BY = "SENT-BY";
    public static final String TZID = "TZID";
    public static final String VALUE = "VALUE";

    public ICalParameters() {
        super(0);
    }

    public ICalParameters(ICalParameters parameters) {
        super((ListMultimap) parameters);
    }

    public String getAltRepresentation() {
        return (String) first(ALTREP);
    }

    public void setAltRepresentation(String uri) {
        replace((Object) ALTREP, (Object) uri);
    }

    public String getCommonName() {
        return (String) first(CN);
    }

    public void setCommonName(String cn) {
        replace((Object) CN, (Object) cn);
    }

    public CalendarUserType getCalendarUserType() {
        String value = (String) first(CUTYPE);
        return value == null ? null : CalendarUserType.get(value);
    }

    public void setCalendarUserType(CalendarUserType cutype) {
        replace((Object) CUTYPE, cutype == null ? null : cutype.getValue());
    }

    public List<String> getDelegatedFrom() {
        return get(DELEGATED_FROM);
    }

    public void addDelegatedFrom(String uri) {
        put(DELEGATED_FROM, uri);
    }

    public void removeDelegatedFrom(String uri) {
        remove(DELEGATED_FROM, uri);
    }

    public void removeDelegatedFrom() {
        removeAll(DELEGATED_FROM);
    }

    public List<String> getDelegatedTo() {
        return get(DELEGATED_TO);
    }

    public void addDelegatedTo(String uri) {
        put(DELEGATED_TO, uri);
    }

    public void removeDelegatedTo(String uri) {
        remove(DELEGATED_TO, uri);
    }

    public void removeDelegatedTo() {
        removeAll(DELEGATED_TO);
    }

    public String getDirectoryEntry() {
        return (String) first(DIR);
    }

    public void setDirectoryEntry(String uri) {
        replace((Object) DIR, (Object) uri);
    }

    public Encoding getEncoding() {
        String value = (String) first(ENCODING);
        return value == null ? null : Encoding.get(value);
    }

    public void setEncoding(Encoding encoding) {
        replace((Object) ENCODING, encoding == null ? null : encoding.getValue());
    }

    public String getFormatType() {
        return (String) first(FMTTYPE);
    }

    public void setFormatType(String formatType) {
        replace((Object) FMTTYPE, (Object) formatType);
    }

    public FreeBusyType getFreeBusyType() {
        String value = (String) first(FBTYPE);
        return value == null ? null : FreeBusyType.get(value);
    }

    public void setFreeBusyType(FreeBusyType fbType) {
        replace((Object) FBTYPE, fbType == null ? null : fbType.getValue());
    }

    public String getLanguage() {
        return (String) first(LANGUAGE);
    }

    public void setLanguage(String language) {
        replace((Object) LANGUAGE, (Object) language);
    }

    public void addMember(String uri) {
        put(MEMBER, uri);
    }

    public List<String> getMembers() {
        return get(MEMBER);
    }

    public void removeMember(String uri) {
        remove(MEMBER, uri);
    }

    public void removeMembers() {
        removeAll(MEMBER);
    }

    public ParticipationStatus getParticipationStatus() {
        String value = (String) first(PARTSTAT);
        return value == null ? null : ParticipationStatus.get(value);
    }

    public void setParticipationStatus(ParticipationStatus status) {
        replace((Object) PARTSTAT, status == null ? null : status.getValue());
    }

    public Range getRange() {
        String value = (String) first(RANGE);
        return value == null ? null : Range.get(value);
    }

    public void setRange(Range range) {
        replace((Object) RANGE, range == null ? null : range.getValue());
    }

    public Related getRelated() {
        String value = (String) first(RELATED);
        return value == null ? null : Related.get(value);
    }

    public void setRelated(Related related) {
        replace((Object) RELATED, related == null ? null : related.getValue());
    }

    public RelationshipType getRelationshipType() {
        String value = (String) first(RELTYPE);
        return value == null ? null : RelationshipType.get(value);
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        replace((Object) RELTYPE, relationshipType == null ? null : relationshipType.getValue());
    }

    public Role getRole() {
        String value = (String) first(ROLE);
        return value == null ? null : Role.get(value);
    }

    public void setRole(Role role) {
        replace((Object) ROLE, role == null ? null : role.getValue());
    }

    public Boolean getRsvp() {
        String value = (String) first(RSVP);
        if (value == null) {
            return null;
        }
        if ("true".equalsIgnoreCase(value)) {
            return Boolean.valueOf(true);
        }
        if ("false".equalsIgnoreCase(value)) {
            return Boolean.valueOf(false);
        }
        throw new IllegalStateException("RSVP parameter value is malformed and could not be parsed. Retrieve its raw text value instead.");
    }

    public void setRsvp(Boolean rsvp) {
        replace((Object) RSVP, rsvp == null ? null : rsvp.toString().toUpperCase());
    }

    public String getSentBy() {
        return (String) first(SENT_BY);
    }

    public void setSentBy(String uri) {
        replace((Object) SENT_BY, (Object) uri);
    }

    public String getTimezoneId() {
        return (String) first(TZID);
    }

    public void setTimezoneId(String timezoneId) {
        replace((Object) TZID, (Object) timezoneId);
    }

    public ICalDataType getValue() {
        String value = (String) first(VALUE);
        return value == null ? null : ICalDataType.get(value);
    }

    public void setValue(ICalDataType value) {
        replace((Object) VALUE, value == null ? null : value.getName());
    }

    public List<Warning> validate() {
        List<Warning> warnings = new ArrayList(0);
        String value = (String) first(RSVP);
        if (!(value == null || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
            warnings.add(Warning.validate(1, RSVP, value, "[TRUE, FALSE]"));
        }
        value = (String) first(CUTYPE);
        if (value != null && CalendarUserType.find(value) == null) {
            warnings.add(Warning.validate(1, CUTYPE, value, CalendarUserType.all()));
        }
        value = (String) first(ENCODING);
        if (value != null && Encoding.find(value) == null) {
            warnings.add(Warning.validate(1, ENCODING, value, Encoding.all()));
        }
        value = (String) first(FBTYPE);
        if (value != null && FreeBusyType.find(value) == null) {
            warnings.add(Warning.validate(1, FBTYPE, value, FreeBusyType.all()));
        }
        value = (String) first(PARTSTAT);
        if (value != null && ParticipationStatus.find(value) == null) {
            warnings.add(Warning.validate(1, PARTSTAT, value, ParticipationStatus.all()));
        }
        value = (String) first(RANGE);
        if (value != null && Range.find(value) == null) {
            warnings.add(Warning.validate(1, RANGE, value, Range.all()));
        }
        value = (String) first(RELATED);
        if (value != null && Related.find(value) == null) {
            warnings.add(Warning.validate(1, RELATED, value, Related.all()));
        }
        value = (String) first(RELTYPE);
        if (value != null && RelationshipType.find(value) == null) {
            warnings.add(Warning.validate(1, RELTYPE, value, RelationshipType.all()));
        }
        value = (String) first(ROLE);
        if (value != null && Role.find(value) == null) {
            warnings.add(Warning.validate(1, ROLE, value, Role.all()));
        }
        value = (String) first(VALUE);
        if (value != null && ICalDataType.find(value) == null) {
            warnings.add(Warning.validate(1, VALUE, value, ICalDataType.all()));
        }
        return warnings;
    }

    protected String sanitizeKey(String key) {
        return key == null ? null : key.toUpperCase();
    }
}
