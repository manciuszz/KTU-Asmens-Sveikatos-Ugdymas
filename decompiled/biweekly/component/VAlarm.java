package biweekly.component;

import biweekly.Warning;
import biweekly.parameter.Related;
import biweekly.property.Action;
import biweekly.property.Attachment;
import biweekly.property.Attendee;
import biweekly.property.DateDue;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.Description;
import biweekly.property.DurationProperty;
import biweekly.property.Repeat;
import biweekly.property.Summary;
import biweekly.property.Trigger;
import biweekly.util.Duration;
import java.util.Arrays;
import java.util.List;

public class VAlarm extends ICalComponent {
    public VAlarm(Action action, Trigger trigger) {
        setAction(action);
        setTrigger(trigger);
    }

    public static VAlarm audio(Trigger trigger) {
        return audio(trigger, null);
    }

    public static VAlarm audio(Trigger trigger, Attachment sound) {
        VAlarm alarm = new VAlarm(Action.audio(), trigger);
        if (sound != null) {
            alarm.addAttachment(sound);
        }
        return alarm;
    }

    public static VAlarm display(Trigger trigger, String displayText) {
        VAlarm alarm = new VAlarm(Action.display(), trigger);
        alarm.setDescription(displayText);
        return alarm;
    }

    public static VAlarm email(Trigger trigger, String subject, String body, String... recipients) {
        return email(trigger, subject, body, Arrays.asList(recipients));
    }

    public static VAlarm email(Trigger trigger, String subject, String body, List<String> recipients) {
        VAlarm alarm = new VAlarm(Action.email(), trigger);
        alarm.setSummary(subject);
        alarm.setDescription(body);
        for (String recipient : recipients) {
            alarm.addAttendee(Attendee.email(recipient));
        }
        return alarm;
    }

    public List<Attachment> getAttachments() {
        return getProperties(Attachment.class);
    }

    public void addAttachment(Attachment attachment) {
        addProperty(attachment);
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

    public List<Attendee> getAttendees() {
        return getProperties(Attendee.class);
    }

    public void addAttendee(Attendee attendee) {
        addProperty(attendee);
    }

    public Action getAction() {
        return (Action) getProperty(Action.class);
    }

    public void setAction(Action action) {
        setProperty(Action.class, action);
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

    public Repeat getRepeat() {
        return (Repeat) getProperty(Repeat.class);
    }

    public void setRepeat(Repeat repeat) {
        setProperty(Repeat.class, repeat);
    }

    public Repeat setRepeat(Integer count) {
        Repeat prop = count == null ? null : new Repeat(count);
        setRepeat(prop);
        return prop;
    }

    public void setRepeat(int count, Duration pauseDuration) {
        Repeat repeat = new Repeat(Integer.valueOf(count));
        DurationProperty duration = new DurationProperty(pauseDuration);
        setRepeat(repeat);
        setDuration(duration);
    }

    public Trigger getTrigger() {
        return (Trigger) getProperty(Trigger.class);
    }

    public void setTrigger(Trigger trigger) {
        setProperty(Trigger.class, trigger);
    }

    protected void validate(List<ICalComponent> components, List<Warning> warnings) {
        checkRequiredCardinality(warnings, Action.class, Trigger.class);
        Action action = getAction();
        if (action != null) {
            if (action.isAudio() && getAttachments().size() > 1) {
                warnings.add(Warning.validate(7, new Object[0]));
            }
            if (action.isDisplay()) {
                checkRequiredCardinality(warnings, Description.class);
            }
            if (action.isEmail()) {
                checkRequiredCardinality(warnings, Summary.class, Description.class);
                if (getAttendees().isEmpty()) {
                    warnings.add(Warning.validate(8, new Object[0]));
                }
            } else if (!getAttendees().isEmpty()) {
                warnings.add(Warning.validate(9, new Object[0]));
            }
        }
        Trigger trigger = getTrigger();
        if (trigger != null) {
            Related related = trigger.getRelated();
            if (related != null) {
                ICalComponent parent = (ICalComponent) components.get(components.size() - 1);
                if (related == Related.START && parent.getProperty(DateStart.class) == null) {
                    warnings.add(Warning.validate(11, new Object[0]));
                }
                if (related == Related.END) {
                    boolean noEndDate = false;
                    if (parent instanceof VEvent) {
                        if (parent.getProperty(DateEnd.class) == null && (parent.getProperty(DateStart.class) == null || parent.getProperty(DurationProperty.class) == null)) {
                            noEndDate = true;
                        } else {
                            noEndDate = false;
                        }
                    } else if (parent instanceof VTodo) {
                        noEndDate = parent.getProperty(DateDue.class) == null && (parent.getProperty(DateStart.class) == null || parent.getProperty(DurationProperty.class) == null);
                    }
                    if (noEndDate) {
                        warnings.add(Warning.validate(12, new Object[0]));
                    }
                }
            }
        }
    }
}
