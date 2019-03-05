package biweekly.property;

import biweekly.parameter.CalendarUserType;
import biweekly.parameter.ParticipationStatus;
import biweekly.parameter.Role;
import java.util.List;

public class Attendee extends TextProperty {
    public Attendee(String uri) {
        super(uri);
    }

    public static Attendee email(String email) {
        return new Attendee("mailto:" + email);
    }

    public CalendarUserType getCalendarUserType() {
        return this.parameters.getCalendarUserType();
    }

    public void setCalendarUserType(CalendarUserType cutype) {
        this.parameters.setCalendarUserType(cutype);
    }

    public List<String> getMembers() {
        return this.parameters.getMembers();
    }

    public void addMember(String uri) {
        this.parameters.addMember(uri);
    }

    public Role getRole() {
        return this.parameters.getRole();
    }

    public void setRole(Role role) {
        this.parameters.setRole(role);
    }

    public ParticipationStatus getParticipationStatus() {
        return this.parameters.getParticipationStatus();
    }

    public void setParticipationStatus(ParticipationStatus status) {
        this.parameters.setParticipationStatus(status);
    }

    public Boolean getRsvp() {
        return this.parameters.getRsvp();
    }

    public void setRsvp(Boolean rsvp) {
        this.parameters.setRsvp(rsvp);
    }

    public List<String> getDelegatedFrom() {
        return this.parameters.getDelegatedFrom();
    }

    public void addDelegatedFrom(String uri) {
        this.parameters.addDelegatedFrom(uri);
    }

    public List<String> getDelegatedTo() {
        return this.parameters.getDelegatedTo();
    }

    public void addDelegatedTo(String uri) {
        this.parameters.addDelegatedTo(uri);
    }

    public String getSentBy() {
        return super.getSentBy();
    }

    public void setSentBy(String uri) {
        super.setSentBy(uri);
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

    public void setDirectoryEntry(String uri) {
        super.setDirectoryEntry(uri);
    }

    public String getLanguage() {
        return super.getLanguage();
    }

    public void setLanguage(String language) {
        super.setLanguage(language);
    }
}
