package biweekly.io.scribe;

import biweekly.ICalendar;
import biweekly.component.ICalComponent;
import biweekly.component.RawComponent;
import biweekly.io.scribe.component.DaylightSavingsTimeScribe;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.component.ICalendarScribe;
import biweekly.io.scribe.component.RawComponentScribe;
import biweekly.io.scribe.component.StandardTimeScribe;
import biweekly.io.scribe.component.VAlarmScribe;
import biweekly.io.scribe.component.VEventScribe;
import biweekly.io.scribe.component.VFreeBusyScribe;
import biweekly.io.scribe.component.VJournalScribe;
import biweekly.io.scribe.component.VTimezoneScribe;
import biweekly.io.scribe.component.VTodoScribe;
import biweekly.io.scribe.property.ActionScribe;
import biweekly.io.scribe.property.AttachmentScribe;
import biweekly.io.scribe.property.AttendeeScribe;
import biweekly.io.scribe.property.CalendarScaleScribe;
import biweekly.io.scribe.property.CategoriesScribe;
import biweekly.io.scribe.property.ClassificationScribe;
import biweekly.io.scribe.property.CommentScribe;
import biweekly.io.scribe.property.CompletedScribe;
import biweekly.io.scribe.property.ContactScribe;
import biweekly.io.scribe.property.CreatedScribe;
import biweekly.io.scribe.property.DateDueScribe;
import biweekly.io.scribe.property.DateEndScribe;
import biweekly.io.scribe.property.DateStartScribe;
import biweekly.io.scribe.property.DateTimeStampScribe;
import biweekly.io.scribe.property.DescriptionScribe;
import biweekly.io.scribe.property.DurationPropertyScribe;
import biweekly.io.scribe.property.ExceptionDatesScribe;
import biweekly.io.scribe.property.ExceptionRuleScribe;
import biweekly.io.scribe.property.FreeBusyScribe;
import biweekly.io.scribe.property.GeoScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.scribe.property.LastModifiedScribe;
import biweekly.io.scribe.property.LocationScribe;
import biweekly.io.scribe.property.MethodScribe;
import biweekly.io.scribe.property.OrganizerScribe;
import biweekly.io.scribe.property.PercentCompleteScribe;
import biweekly.io.scribe.property.PriorityScribe;
import biweekly.io.scribe.property.ProductIdScribe;
import biweekly.io.scribe.property.RawPropertyScribe;
import biweekly.io.scribe.property.RecurrenceDatesScribe;
import biweekly.io.scribe.property.RecurrenceIdScribe;
import biweekly.io.scribe.property.RecurrenceRuleScribe;
import biweekly.io.scribe.property.RelatedToScribe;
import biweekly.io.scribe.property.RepeatScribe;
import biweekly.io.scribe.property.RequestStatusScribe;
import biweekly.io.scribe.property.ResourcesScribe;
import biweekly.io.scribe.property.SequenceScribe;
import biweekly.io.scribe.property.StatusScribe;
import biweekly.io.scribe.property.SummaryScribe;
import biweekly.io.scribe.property.TimezoneIdScribe;
import biweekly.io.scribe.property.TimezoneNameScribe;
import biweekly.io.scribe.property.TimezoneOffsetFromScribe;
import biweekly.io.scribe.property.TimezoneOffsetToScribe;
import biweekly.io.scribe.property.TimezoneUrlScribe;
import biweekly.io.scribe.property.TransparencyScribe;
import biweekly.io.scribe.property.TriggerScribe;
import biweekly.io.scribe.property.UidScribe;
import biweekly.io.scribe.property.UrlScribe;
import biweekly.io.scribe.property.VersionScribe;
import biweekly.io.scribe.property.XmlScribe;
import biweekly.io.xml.XCalNamespaceContext;
import biweekly.property.ICalProperty;
import biweekly.property.RawProperty;
import biweekly.property.Xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.namespace.QName;

public class ScribeIndex {
    private static final Map<Class<? extends ICalComponent>, ICalComponentScribe<? extends ICalComponent>> standardCompByClass = new HashMap();
    private static final Map<String, ICalComponentScribe<? extends ICalComponent>> standardCompByName = new HashMap();
    private static final Map<Class<? extends ICalProperty>, ICalPropertyScribe<? extends ICalProperty>> standardPropByClass = new HashMap();
    private static final Map<String, ICalPropertyScribe<? extends ICalProperty>> standardPropByName = new HashMap();
    private static final Map<QName, ICalPropertyScribe<? extends ICalProperty>> standardPropByQName = new HashMap();
    private final Map<Class<? extends ICalComponent>, ICalComponentScribe<? extends ICalComponent>> experimentalCompByClass = new HashMap(0);
    private final Map<String, ICalComponentScribe<? extends ICalComponent>> experimentalCompByName = new HashMap(0);
    private final Map<Class<? extends ICalProperty>, ICalPropertyScribe<? extends ICalProperty>> experimentalPropByClass = new HashMap(0);
    private final Map<String, ICalPropertyScribe<? extends ICalProperty>> experimentalPropByName = new HashMap(0);
    private final Map<QName, ICalPropertyScribe<? extends ICalProperty>> experimentalPropByQName = new HashMap(0);

    static {
        registerStandard(new ICalendarScribe());
        registerStandard(new VAlarmScribe());
        registerStandard(new VEventScribe());
        registerStandard(new VFreeBusyScribe());
        registerStandard(new VJournalScribe());
        registerStandard(new VTodoScribe());
        registerStandard(new VTimezoneScribe());
        registerStandard(new StandardTimeScribe());
        registerStandard(new DaylightSavingsTimeScribe());
        registerStandard(new ActionScribe());
        registerStandard(new AttachmentScribe());
        registerStandard(new AttendeeScribe());
        registerStandard(new CalendarScaleScribe());
        registerStandard(new CategoriesScribe());
        registerStandard(new ClassificationScribe());
        registerStandard(new CommentScribe());
        registerStandard(new CompletedScribe());
        registerStandard(new ContactScribe());
        registerStandard(new CreatedScribe());
        registerStandard(new DateDueScribe());
        registerStandard(new DateEndScribe());
        registerStandard(new DateStartScribe());
        registerStandard(new DateTimeStampScribe());
        registerStandard(new DescriptionScribe());
        registerStandard(new DurationPropertyScribe());
        registerStandard(new ExceptionDatesScribe());
        registerStandard(new FreeBusyScribe());
        registerStandard(new GeoScribe());
        registerStandard(new LastModifiedScribe());
        registerStandard(new LocationScribe());
        registerStandard(new MethodScribe());
        registerStandard(new OrganizerScribe());
        registerStandard(new PercentCompleteScribe());
        registerStandard(new PriorityScribe());
        registerStandard(new ProductIdScribe());
        registerStandard(new RecurrenceDatesScribe());
        registerStandard(new RecurrenceIdScribe());
        registerStandard(new RecurrenceRuleScribe());
        registerStandard(new RelatedToScribe());
        registerStandard(new RepeatScribe());
        registerStandard(new RequestStatusScribe());
        registerStandard(new ResourcesScribe());
        registerStandard(new SequenceScribe());
        registerStandard(new StatusScribe());
        registerStandard(new SummaryScribe());
        registerStandard(new TimezoneIdScribe());
        registerStandard(new TimezoneNameScribe());
        registerStandard(new TimezoneOffsetFromScribe());
        registerStandard(new TimezoneOffsetToScribe());
        registerStandard(new TimezoneUrlScribe());
        registerStandard(new TransparencyScribe());
        registerStandard(new TriggerScribe());
        registerStandard(new UidScribe());
        registerStandard(new UrlScribe());
        registerStandard(new VersionScribe());
        registerStandard(new XmlScribe());
        registerStandard(new ExceptionRuleScribe());
    }

    public ICalComponentScribe<? extends ICalComponent> getComponentScribe(String componentName) {
        componentName = componentName.toUpperCase();
        ICalComponentScribe<? extends ICalComponent> marshaller = (ICalComponentScribe) this.experimentalCompByName.get(componentName);
        if (marshaller != null) {
            return marshaller;
        }
        marshaller = (ICalComponentScribe) standardCompByName.get(componentName);
        if (marshaller != null) {
            return marshaller;
        }
        return new RawComponentScribe(componentName);
    }

    public ICalPropertyScribe<? extends ICalProperty> getPropertyScribe(String propertyName) {
        propertyName = propertyName.toUpperCase();
        ICalPropertyScribe<? extends ICalProperty> marshaller = (ICalPropertyScribe) this.experimentalPropByName.get(propertyName);
        if (marshaller != null) {
            return marshaller;
        }
        marshaller = (ICalPropertyScribe) standardPropByName.get(propertyName);
        if (marshaller != null) {
            return marshaller;
        }
        return new RawPropertyScribe(propertyName);
    }

    public ICalComponentScribe<? extends ICalComponent> getComponentScribe(Class<? extends ICalComponent> clazz) {
        ICalComponentScribe<? extends ICalComponent> marshaller = (ICalComponentScribe) this.experimentalCompByClass.get(clazz);
        return marshaller != null ? marshaller : (ICalComponentScribe) standardCompByClass.get(clazz);
    }

    public ICalPropertyScribe<? extends ICalProperty> getPropertyScribe(Class<? extends ICalProperty> clazz) {
        ICalPropertyScribe<? extends ICalProperty> marshaller = (ICalPropertyScribe) this.experimentalPropByClass.get(clazz);
        return marshaller != null ? marshaller : (ICalPropertyScribe) standardPropByClass.get(clazz);
    }

    public ICalComponentScribe<? extends ICalComponent> getComponentScribe(ICalComponent component) {
        if (component instanceof RawComponent) {
            return new RawComponentScribe(((RawComponent) component).getName());
        }
        return getComponentScribe(component.getClass());
    }

    public ICalPropertyScribe<? extends ICalProperty> getPropertyScribe(ICalProperty property) {
        if (property instanceof RawProperty) {
            return new RawPropertyScribe(((RawProperty) property).getName());
        }
        return getPropertyScribe(property.getClass());
    }

    public ICalPropertyScribe<? extends ICalProperty> getPropertyScribe(QName qname) {
        ICalPropertyScribe<? extends ICalProperty> marshaller = (ICalPropertyScribe) this.experimentalPropByQName.get(qname);
        if (marshaller != null) {
            return marshaller;
        }
        marshaller = (ICalPropertyScribe) standardPropByQName.get(qname);
        if (marshaller != null) {
            return marshaller;
        }
        if (XCalNamespaceContext.XCAL_NS.equals(qname.getNamespaceURI())) {
            return new RawPropertyScribe(qname.getLocalPart().toUpperCase());
        }
        return getPropertyScribe(Xml.class);
    }

    public void register(ICalComponentScribe<? extends ICalComponent> scribe) {
        this.experimentalCompByName.put(scribe.getComponentName().toUpperCase(), scribe);
        this.experimentalCompByClass.put(scribe.getComponentClass(), scribe);
    }

    public void register(ICalPropertyScribe<? extends ICalProperty> scribe) {
        this.experimentalPropByName.put(scribe.getPropertyName().toUpperCase(), scribe);
        this.experimentalPropByClass.put(scribe.getPropertyClass(), scribe);
        this.experimentalPropByQName.put(scribe.getQName(), scribe);
    }

    public void unregister(ICalComponentScribe<? extends ICalComponent> scribe) {
        this.experimentalCompByName.remove(scribe.getComponentName().toUpperCase());
        this.experimentalCompByClass.remove(scribe.getComponentClass());
    }

    public void unregister(ICalPropertyScribe<? extends ICalProperty> scribe) {
        this.experimentalPropByName.remove(scribe.getPropertyName().toUpperCase());
        this.experimentalPropByClass.remove(scribe.getPropertyClass());
        this.experimentalPropByQName.remove(scribe.getQName());
    }

    public void hasScribesFor(ICalendar ical) {
        Set<Class<? extends Object>> unregistered = new HashSet();
        List<ICalComponent> components = new ArrayList();
        components.add(ical);
        while (!components.isEmpty()) {
            ICalComponent component = (ICalComponent) components.remove(components.size() - 1);
            Class componentClass = component.getClass();
            if (componentClass != RawComponent.class && getComponentScribe(componentClass) == null) {
                unregistered.add(componentClass);
            }
            Iterator i$ = component.getProperties().iterator();
            while (i$.hasNext()) {
                Entry<Class<? extends ICalProperty>, List<ICalProperty>> entry = (Entry) i$.next();
                if (!((List) entry.getValue()).isEmpty()) {
                    Class clazz = (Class) entry.getKey();
                    if (clazz != RawProperty.class && getPropertyScribe(clazz) == null) {
                        unregistered.add(clazz);
                    }
                }
            }
            components.addAll(component.getComponents().values());
        }
        if (!unregistered.isEmpty()) {
            throw new IllegalArgumentException("No scribes were found the following component/property classes: " + unregistered);
        }
    }

    public static ICalendarScribe getICalendarScribe() {
        return (ICalendarScribe) standardCompByClass.get(ICalendar.class);
    }

    private static void registerStandard(ICalComponentScribe<? extends ICalComponent> marshaller) {
        standardCompByName.put(marshaller.getComponentName().toUpperCase(), marshaller);
        standardCompByClass.put(marshaller.getComponentClass(), marshaller);
    }

    private static void registerStandard(ICalPropertyScribe<? extends ICalProperty> marshaller) {
        standardPropByName.put(marshaller.getPropertyName().toUpperCase(), marshaller);
        standardPropByClass.put(marshaller.getPropertyClass(), marshaller);
        standardPropByQName.put(marshaller.getQName(), marshaller);
    }
}
