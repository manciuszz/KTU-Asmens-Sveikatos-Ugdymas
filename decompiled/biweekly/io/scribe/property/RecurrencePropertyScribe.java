package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.io.xml.XCalNamespaceContext;
import biweekly.parameter.ICalParameters;
import biweekly.property.RecurrenceProperty;
import biweekly.util.ICalDateFormat;
import biweekly.util.ListMultimap;
import biweekly.util.Recurrence;
import biweekly.util.Recurrence.Builder;
import biweekly.util.Recurrence.DayOfWeek;
import biweekly.util.Recurrence.Frequency;
import biweekly.util.XmlUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Element;

public abstract class RecurrencePropertyScribe<T extends RecurrenceProperty> extends ICalPropertyScribe<T> {
    private static final String BYDAY = "BYDAY";
    private static final String BYHOUR = "BYHOUR";
    private static final String BYMINUTE = "BYMINUTE";
    private static final String BYMONTH = "BYMONTH";
    private static final String BYMONTHDAY = "BYMONTHDAY";
    private static final String BYSECOND = "BYSECOND";
    private static final String BYSETPOS = "BYSETPOS";
    private static final String BYWEEKNO = "BYWEEKNO";
    private static final String BYYEARDAY = "BYYEARDAY";
    private static final String COUNT = "COUNT";
    private static final String FREQ = "FREQ";
    private static final String INTERVAL = "INTERVAL";
    private static final String UNTIL = "UNTIL";
    private static final String WKST = "WKST";

    private interface Handler<T> {
        void handle(T t);
    }

    protected abstract T newInstance(Recurrence recurrence);

    public RecurrencePropertyScribe(Class<T> clazz, String propertyName) {
        super(clazz, propertyName, ICalDataType.RECUR);
    }

    protected String _writeText(T property) {
        Recurrence recur = (Recurrence) property.getValue();
        if (recur == null) {
            return "";
        }
        return ICalPropertyScribe.object(buildComponents(recur, false).getMap());
    }

    protected T _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        Builder builder = new Builder((Frequency) null);
        ListMultimap<String, String> rules = ICalPropertyScribe.object(value);
        parseFreq(rules, builder, warnings);
        parseUntil(rules, builder, warnings);
        parseCount(rules, builder, warnings);
        parseInterval(rules, builder, warnings);
        parseBySecond(rules, builder, warnings);
        parseByMinute(rules, builder, warnings);
        parseByHour(rules, builder, warnings);
        parseByDay(rules, builder, warnings);
        parseByMonthDay(rules, builder, warnings);
        parseByYearDay(rules, builder, warnings);
        parseByWeekNo(rules, builder, warnings);
        parseByMonth(rules, builder, warnings);
        parseBySetPos(rules, builder, warnings);
        parseWkst(rules, builder, warnings);
        parseXRules(rules, builder, warnings);
        return newInstance(builder.build());
    }

    protected void _writeXml(T property, XCalElement element) {
        XCalElement recurElement = element.append(dataType(property));
        Recurrence recur = (Recurrence) property.getValue();
        if (recur != null) {
            Iterator it = buildComponents(recur, true).iterator();
            while (it.hasNext()) {
                Entry<String, List<Object>> component = (Entry) it.next();
                String name = ((String) component.getKey()).toLowerCase();
                for (Object value : (List) component.getValue()) {
                    recurElement.append(name, value.toString());
                }
            }
        }
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        XCalElement value = element.child(this.defaultDataType);
        if (value == null) {
            throw ICalPropertyScribe.missingXmlElements(this.defaultDataType);
        }
        ListMultimap<String, String> rules = new ListMultimap();
        for (Element child : XmlUtils.toElementList(value.getElement().getChildNodes())) {
            if (XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                rules.put(child.getLocalName().toUpperCase(), child.getTextContent());
            }
        }
        Builder builder = new Builder((Frequency) null);
        parseFreq(rules, builder, warnings);
        parseUntil(rules, builder, warnings);
        parseCount(rules, builder, warnings);
        parseInterval(rules, builder, warnings);
        parseBySecond(rules, builder, warnings);
        parseByMinute(rules, builder, warnings);
        parseByHour(rules, builder, warnings);
        parseByDay(rules, builder, warnings);
        parseByMonthDay(rules, builder, warnings);
        parseByYearDay(rules, builder, warnings);
        parseByWeekNo(rules, builder, warnings);
        parseByMonth(rules, builder, warnings);
        parseBySetPos(rules, builder, warnings);
        parseWkst(rules, builder, warnings);
        parseXRules(rules, builder, warnings);
        return newInstance(builder.build());
    }

    protected JCalValue _writeJson(T property) {
        Recurrence recur = (Recurrence) property.getValue();
        if (recur == null) {
            return JCalValue.object(new ListMultimap(0));
        }
        ListMultimap<String, Object> components = buildComponents(recur, true);
        ListMultimap<String, Object> object = new ListMultimap(components.keySet().size());
        Iterator i$ = components.iterator();
        while (i$.hasNext()) {
            Entry<String, List<Object>> entry = (Entry) i$.next();
            object.putAll(((String) entry.getKey()).toLowerCase(), (Collection) entry.getValue());
        }
        return JCalValue.object(object);
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        Builder builder = new Builder((Frequency) null);
        ListMultimap<String, String> object = value.asObject();
        ListMultimap<String, String> rules = new ListMultimap(object.keySet().size());
        Iterator i$ = object.iterator();
        while (i$.hasNext()) {
            Entry<String, List<String>> entry = (Entry) i$.next();
            rules.putAll(((String) entry.getKey()).toUpperCase(), (Collection) entry.getValue());
        }
        parseFreq(rules, builder, warnings);
        parseUntil(rules, builder, warnings);
        parseCount(rules, builder, warnings);
        parseInterval(rules, builder, warnings);
        parseBySecond(rules, builder, warnings);
        parseByMinute(rules, builder, warnings);
        parseByHour(rules, builder, warnings);
        parseByDay(rules, builder, warnings);
        parseByMonthDay(rules, builder, warnings);
        parseByYearDay(rules, builder, warnings);
        parseByWeekNo(rules, builder, warnings);
        parseByMonth(rules, builder, warnings);
        parseBySetPos(rules, builder, warnings);
        parseWkst(rules, builder, warnings);
        parseXRules(rules, builder, warnings);
        return newInstance(builder.build());
    }

    private void parseFreq(ListMultimap<String, String> rules, final Builder builder, final List<Warning> warnings) {
        parseFirst(rules, FREQ, new Handler<String>() {
            public void handle(String value) {
                try {
                    builder.frequency(Frequency.valueOf(value.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    warnings.add(Warning.parse(7, RecurrencePropertyScribe.FREQ, value));
                }
            }
        });
    }

    private void parseUntil(ListMultimap<String, String> rules, final Builder builder, final List<Warning> warnings) {
        parseFirst(rules, UNTIL, new Handler<String>() {
            public void handle(String value) {
                try {
                    builder.until(ICalPropertyScribe.date(value).parse(), ICalDateFormat.dateHasTime(value));
                } catch (IllegalArgumentException e) {
                    warnings.add(Warning.parse(7, RecurrencePropertyScribe.UNTIL, value));
                }
            }
        });
    }

    private void parseCount(ListMultimap<String, String> rules, final Builder builder, final List<Warning> warnings) {
        parseFirst(rules, COUNT, new Handler<String>() {
            public void handle(String value) {
                try {
                    builder.count(Integer.valueOf(value));
                } catch (NumberFormatException e) {
                    warnings.add(Warning.parse(7, RecurrencePropertyScribe.COUNT, value));
                }
            }
        });
    }

    private void parseInterval(ListMultimap<String, String> rules, final Builder builder, final List<Warning> warnings) {
        parseFirst(rules, INTERVAL, new Handler<String>() {
            public void handle(String value) {
                try {
                    builder.interval(Integer.valueOf(value));
                } catch (NumberFormatException e) {
                    warnings.add(Warning.parse(7, RecurrencePropertyScribe.INTERVAL, value));
                }
            }
        });
    }

    private void parseBySecond(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYSECOND, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.bySecond(value);
            }
        });
    }

    private void parseByMinute(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYMINUTE, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byMinute(value);
            }
        });
    }

    private void parseByHour(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYHOUR, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byHour(value);
            }
        });
    }

    private void parseByDay(ListMultimap<String, String> rules, Builder builder, List<Warning> warnings) {
        Pattern p = Pattern.compile("^([-+]?\\d+)?(.*)$");
        for (String value : rules.removeAll(BYDAY)) {
            Matcher m = p.matcher(value);
            if (m.find()) {
                DayOfWeek day = DayOfWeek.valueOfAbbr(m.group(2));
                if (day == null) {
                    warnings.add(Warning.parse(7, BYDAY, value));
                } else {
                    String prefixStr = m.group(1);
                    builder.byDay(prefixStr == null ? null : Integer.valueOf(prefixStr), day);
                }
            } else {
                warnings.add(Warning.parse(7, BYDAY, value));
            }
        }
    }

    private void parseByMonthDay(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYMONTHDAY, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byMonthDay(value);
            }
        });
    }

    private void parseByYearDay(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYYEARDAY, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byYearDay(value);
            }
        });
    }

    private void parseByWeekNo(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYWEEKNO, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byWeekNo(value);
            }
        });
    }

    private void parseByMonth(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYMONTH, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.byMonth(value);
            }
        });
    }

    private void parseBySetPos(ListMultimap<String, String> rules, final Builder builder, List<Warning> warnings) {
        parseIntegerList(BYSETPOS, rules, warnings, new Handler<Integer>() {
            public void handle(Integer value) {
                builder.bySetPos(value);
            }
        });
    }

    private void parseWkst(ListMultimap<String, String> rules, final Builder builder, final List<Warning> warnings) {
        parseFirst(rules, WKST, new Handler<String>() {
            public void handle(String value) {
                DayOfWeek day = DayOfWeek.valueOfAbbr(value);
                if (day == null) {
                    warnings.add(Warning.parse(7, RecurrencePropertyScribe.WKST, value));
                    return;
                }
                builder.workweekStarts(day);
            }
        });
    }

    private void parseXRules(ListMultimap<String, String> rules, Builder builder, List<Warning> list) {
        Iterator it = rules.iterator();
        while (it.hasNext()) {
            Entry<String, List<String>> rule = (Entry) it.next();
            String name = (String) rule.getKey();
            for (String value : (List) rule.getValue()) {
                builder.xrule(name, value);
            }
        }
    }

    private ListMultimap<String, Object> buildComponents(Recurrence recur, boolean extended) {
        ListMultimap<String, Object> components = new ListMultimap();
        if (recur.getFrequency() != null) {
            components.put(FREQ, recur.getFrequency().name());
        }
        if (recur.getUntil() != null) {
            components.put(UNTIL, ICalPropertyScribe.date(recur.getUntil()).time(recur.hasTimeUntilDate()).extended(extended).write());
        }
        if (recur.getCount() != null) {
            components.put(COUNT, recur.getCount());
        }
        if (recur.getInterval() != null) {
            components.put(INTERVAL, recur.getInterval());
        }
        addIntegerListComponent(components, BYSECOND, recur.getBySecond());
        addIntegerListComponent(components, BYMINUTE, recur.getByMinute());
        addIntegerListComponent(components, BYHOUR, recur.getByHour());
        Iterator<Integer> prefixIt = recur.getByDayPrefixes().iterator();
        Iterator<DayOfWeek> dayIt = recur.getByDay().iterator();
        while (prefixIt.hasNext() && dayIt.hasNext()) {
            Integer prefix = (Integer) prefixIt.next();
            String value = ((DayOfWeek) dayIt.next()).getAbbr();
            if (prefix != null) {
                value = prefix + value;
            }
            components.put(BYDAY, value);
        }
        addIntegerListComponent(components, BYMONTHDAY, recur.getByMonthDay());
        addIntegerListComponent(components, BYYEARDAY, recur.getByYearDay());
        addIntegerListComponent(components, BYWEEKNO, recur.getByWeekNo());
        addIntegerListComponent(components, BYMONTH, recur.getByMonth());
        addIntegerListComponent(components, BYSETPOS, recur.getBySetPos());
        if (recur.getWorkweekStarts() != null) {
            components.put(WKST, recur.getWorkweekStarts().getAbbr());
        }
        for (Entry<String, List<String>> entry : recur.getXRules().entrySet()) {
            String name = (String) entry.getKey();
            for (String value2 : (List) entry.getValue()) {
                components.put(name, value2);
            }
        }
        return components;
    }

    private void addIntegerListComponent(ListMultimap<String, Object> components, String name, List<Integer> values) {
        for (Integer value : values) {
            components.put(name, value);
        }
    }

    private void parseFirst(ListMultimap<String, String> rules, String name, Handler<String> handler) {
        List<String> values = rules.removeAll(name);
        if (!values.isEmpty()) {
            handler.handle((String) values.get(0));
        }
    }

    private void parseIntegerList(String name, ListMultimap<String, String> rules, List<Warning> warnings, Handler<Integer> handler) {
        for (String value : rules.removeAll(name)) {
            try {
                handler.handle(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                warnings.add(Warning.parse(8, name, value));
            }
        }
    }
}
