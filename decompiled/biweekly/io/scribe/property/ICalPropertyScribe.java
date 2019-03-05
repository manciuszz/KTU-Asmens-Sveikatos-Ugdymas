package biweekly.io.scribe.property;

import android.support.v4.os.EnvironmentCompat;
import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.json.JsonValue;
import biweekly.io.xml.XCalElement;
import biweekly.io.xml.XCalNamespaceContext;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.util.ICalDateFormat;
import biweekly.util.ListMultimap;
import biweekly.util.StringUtils;
import biweekly.util.StringUtils.JoinCallback;
import biweekly.util.StringUtils.JoinMapCallback;
import biweekly.util.XmlUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public abstract class ICalPropertyScribe<T extends ICalProperty> {
    protected final Class<T> clazz;
    protected final ICalDataType defaultDataType;
    protected final String propertyName;
    protected final QName qname;

    protected static class DateParser {
        private TimeZone timezone;
        private String value;

        public DateParser(String value) {
            this.value = value;
        }

        public DateParser tzid(String timezoneId) {
            return tzid(timezoneId, null);
        }

        public DateParser tzid(String timezoneId, List<Warning> warnings) {
            if (timezoneId == null) {
                return tz(null);
            }
            if (!timezoneId.contains("/")) {
                return tz(TimeZone.getDefault());
            }
            TimeZone timezone = ICalDateFormat.parseTimeZoneId(timezoneId);
            if (timezone == null) {
                timezone = TimeZone.getDefault();
                if (warnings != null) {
                    warnings.add(Warning.parse(5, timezoneId, timezone.getID()));
                }
            }
            return tz(timezone);
        }

        public DateParser tz(TimeZone timezone) {
            this.timezone = timezone;
            return this;
        }

        public Date parse() {
            return ICalDateFormat.parse(this.value, this.timezone);
        }
    }

    protected static class DateWriter {
        private Date date;
        private boolean extended = false;
        private boolean hasTime = true;
        private TimeZone timezone;

        public DateWriter(Date date) {
            this.date = date;
        }

        public DateWriter time(boolean hasTime) {
            this.hasTime = hasTime;
            return this;
        }

        public DateWriter tzid(String timezoneId) {
            if (timezoneId == null) {
                return tz(null);
            }
            if (timezoneId.contains("/")) {
                return tz(ICalDateFormat.parseTimeZoneId(timezoneId));
            }
            return tz(TimeZone.getDefault());
        }

        public DateWriter localTz(boolean localTz) {
            return localTz ? tz(TimeZone.getDefault()) : this;
        }

        public DateWriter tz(boolean localTz, String timezoneId) {
            return localTz ? localTz(true) : tzid(timezoneId);
        }

        public DateWriter tz(TimeZone timezone) {
            this.timezone = timezone;
            return this;
        }

        public DateWriter extended(boolean extended) {
            this.extended = extended;
            return this;
        }

        public String write() {
            ICalDateFormat format;
            TimeZone timezone = this.timezone;
            if (this.hasTime) {
                format = timezone == null ? this.extended ? ICalDateFormat.UTC_TIME_EXTENDED : ICalDateFormat.UTC_TIME_BASIC : this.extended ? ICalDateFormat.DATE_TIME_EXTENDED_WITHOUT_TZ : ICalDateFormat.DATE_TIME_BASIC_WITHOUT_TZ;
            } else {
                format = this.extended ? ICalDateFormat.DATE_EXTENDED : ICalDateFormat.DATE_BASIC;
                timezone = null;
            }
            return format.format(this.date, timezone);
        }
    }

    protected interface ListCallback<T> {
        String asString(T t);
    }

    public static class Result<T extends ICalProperty> {
        private final T property;
        private final List<Warning> warnings;

        public Result(T property, List<Warning> warnings) {
            this.property = property;
            this.warnings = warnings;
        }

        public List<Warning> getWarnings() {
            return this.warnings;
        }

        public T getProperty() {
            return this.property;
        }
    }

    protected static class SemiStructuredIterator {
        private final Iterator<String> it;

        public SemiStructuredIterator(Iterator<String> it) {
            this.it = it;
        }

        public String next() {
            return hasNext() ? (String) this.it.next() : null;
        }

        public boolean hasNext() {
            return this.it.hasNext();
        }
    }

    protected static class Splitter {
        private char delimiter;
        private int limit = -1;
        private boolean nullEmpties = false;
        private boolean unescape = false;

        public Splitter(char delimiter) {
            this.delimiter = delimiter;
        }

        public Splitter unescape(boolean unescape) {
            this.unescape = unescape;
            return this;
        }

        public Splitter nullEmpties(boolean nullEmpties) {
            this.nullEmpties = nullEmpties;
            return this;
        }

        public Splitter limit(int limit) {
            this.limit = limit;
            return this;
        }

        public List<String> split(String string) {
            List<String> list = new ArrayList();
            boolean escaped = false;
            int start = 0;
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (escaped) {
                    escaped = false;
                } else if (ch == this.delimiter) {
                    add(string.substring(start, i), list);
                    start = i + 1;
                    if (this.limit > 0 && list.size() == this.limit - 1) {
                        break;
                    }
                } else if (ch == '\\') {
                    escaped = true;
                }
            }
            add(string.substring(start), list);
            return list;
        }

        private void add(String str, List<String> list) {
            str = str.trim();
            if (this.nullEmpties && str.length() == 0) {
                str = null;
            } else if (this.unescape) {
                str = ICalPropertyScribe.unescape(str);
            }
            list.add(str);
        }
    }

    protected static class StructuredIterator {
        private final Iterator<List<String>> it;

        public StructuredIterator(Iterator<List<String>> it) {
            this.it = it;
        }

        public String nextString() {
            if (!hasNext()) {
                return null;
            }
            List<String> list = (List) this.it.next();
            if (list.isEmpty()) {
                return null;
            }
            String value = (String) list.get(0);
            if (value.length() == 0) {
                value = null;
            }
            return value;
        }

        public List<String> nextComponent() {
            if (!hasNext()) {
                return new ArrayList(0);
            }
            List<String> list = (List) this.it.next();
            if (list.size() == 1 && ((String) list.get(0)).length() == 0) {
                return new ArrayList(0);
            }
            return list;
        }

        public boolean hasNext() {
            return this.it.hasNext();
        }
    }

    protected abstract T _parseText(String str, ICalDataType iCalDataType, ICalParameters iCalParameters, List<Warning> list);

    protected abstract String _writeText(T t);

    public ICalPropertyScribe(Class<T> clazz, String propertyName, ICalDataType defaultDataType) {
        this(clazz, propertyName, defaultDataType, new QName(XCalNamespaceContext.XCAL_NS, propertyName.toLowerCase()));
    }

    public ICalPropertyScribe(Class<T> clazz, String propertyName, ICalDataType defaultDataType, QName qname) {
        this.clazz = clazz;
        this.propertyName = propertyName;
        this.defaultDataType = defaultDataType;
        this.qname = qname;
    }

    public Class<T> getPropertyClass() {
        return this.clazz;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public ICalDataType getDefaultDataType() {
        return this.defaultDataType;
    }

    public QName getQName() {
        return this.qname;
    }

    public final ICalParameters prepareParameters(T property) {
        ICalParameters copy = new ICalParameters(property.getParameters());
        _prepareParameters(property, copy);
        return copy;
    }

    public final ICalDataType dataType(T property) {
        return _dataType(property);
    }

    public final String writeText(T property) {
        return _writeText(property);
    }

    public final void writeXml(T property, Element element) {
        _writeXml(property, new XCalElement(element));
    }

    public final JCalValue writeJson(T property) {
        return _writeJson(property);
    }

    public final Result<T> parseText(String value, ICalDataType dataType, ICalParameters parameters) {
        List<Warning> warnings = new ArrayList(0);
        T property = _parseText(value, dataType, parameters, warnings);
        property.setParameters(parameters);
        return new Result(property, warnings);
    }

    public final Result<T> parseXml(Element element, ICalParameters parameters) {
        List<Warning> warnings = new ArrayList(0);
        T property = _parseXml(new XCalElement(element), parameters, warnings);
        property.setParameters(parameters);
        return new Result(property, warnings);
    }

    public final Result<T> parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters) {
        List<Warning> warnings = new ArrayList(0);
        T property = _parseJson(value, dataType, parameters, warnings);
        property.setParameters(parameters);
        return new Result(property, warnings);
    }

    protected void _prepareParameters(T t, ICalParameters copy) {
    }

    protected ICalDataType _dataType(T t) {
        return this.defaultDataType;
    }

    protected void _writeXml(T property, XCalElement element) {
        element.append(dataType(property), writeText(property));
    }

    protected JCalValue _writeJson(T property) {
        return JCalValue.single(writeText(property));
    }

    protected T _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        String value = null;
        ICalDataType dataType = null;
        Element rawElement = element.getElement();
        for (Element child : XmlUtils.toElementList(rawElement.getChildNodes())) {
            if (XCalNamespaceContext.XCAL_NS.equals(child.getNamespaceURI())) {
                String dataTypeStr = child.getLocalName();
                dataType = EnvironmentCompat.MEDIA_UNKNOWN.equals(dataTypeStr) ? null : ICalDataType.get(dataTypeStr);
                value = child.getTextContent();
                if (dataType == null) {
                    value = rawElement.getTextContent();
                }
                return _parseText(escape(value), dataType, parameters, warnings);
            }
        }
        if (dataType == null) {
            value = rawElement.getTextContent();
        }
        return _parseText(escape(value), dataType, parameters, warnings);
    }

    protected T _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return _parseText(jcalValueToString(value), dataType, parameters, warnings);
    }

    private String jcalValueToString(JCalValue value) {
        if (value.getValues().size() > 1) {
            Collection multi = value.asMulti();
            if (!multi.isEmpty()) {
                return list(multi);
            }
        }
        if (!(value.getValues().isEmpty() || ((JsonValue) value.getValues().get(0)).getArray() == null)) {
            List<List<String>> structured = value.asStructured();
            if (!structured.isEmpty()) {
                return structured(structured.toArray());
            }
        }
        if (((JsonValue) value.getValues().get(0)).getObject() != null) {
            ListMultimap<String, String> object = value.asObject();
            if (!object.isEmpty()) {
                return object(object.getMap());
            }
        }
        return escape(value.asSingle());
    }

    protected static String unescape(String text) {
        if (text == null) {
            return text;
        }
        StringBuilder sb = null;
        boolean escaped = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (escaped) {
                if (sb == null) {
                    sb = new StringBuilder(text.length());
                    sb.append(text.substring(0, i - 1));
                }
                escaped = false;
                if (ch == 'n' || ch == 'N') {
                    sb.append(StringUtils.NEWLINE);
                } else {
                    sb.append(ch);
                }
            } else if (ch == '\\') {
                escaped = true;
            } else if (sb != null) {
                sb.append(ch);
            }
        }
        return sb != null ? sb.toString() : text;
    }

    protected static String escape(String text) {
        if (text == null) {
            return text;
        }
        String chars = "\\,;";
        StringBuilder sb = null;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (chars.indexOf(ch) >= 0) {
                if (sb == null) {
                    sb = new StringBuilder(text.length());
                    sb.append(text.substring(0, i));
                }
                sb.append('\\');
            }
            if (sb != null) {
                sb.append(ch);
            }
        }
        return sb != null ? sb.toString() : text;
    }

    protected static Splitter splitter(char delimiter) {
        return new Splitter(delimiter);
    }

    protected static List<String> list(String value) {
        if (value.length() == 0) {
            return new ArrayList(0);
        }
        return splitter(',').unescape(true).split(value);
    }

    protected static String list(Object... values) {
        return list(Arrays.asList(values));
    }

    protected static <T> String list(Collection<T> values) {
        return list(values, new ListCallback<T>() {
            public String asString(T value) {
                return value.toString();
            }
        });
    }

    protected static <T> String list(Collection<T> values, final ListCallback<T> callback) {
        return StringUtils.join((Collection) values, ",", new JoinCallback<T>() {
            public void handle(StringBuilder sb, T value) {
                if (value != null) {
                    sb.append(ICalPropertyScribe.escape(callback.asString(value)));
                }
            }
        });
    }

    protected static SemiStructuredIterator semistructured(String value) {
        return semistructured(value, false);
    }

    protected static SemiStructuredIterator semistructured(String value, boolean nullEmpties) {
        return new SemiStructuredIterator(splitter(';').unescape(true).nullEmpties(nullEmpties).split(value).iterator());
    }

    protected static StructuredIterator structured(String value) {
        List<String> split = splitter(';').split(value);
        List<List<String>> components = new ArrayList(split.size());
        for (String s : split) {
            components.add(list(s));
        }
        return new StructuredIterator(components.iterator());
    }

    protected static StructuredIterator structured(JCalValue value) {
        return new StructuredIterator(value.asStructured().iterator());
    }

    protected static String structured(Object... values) {
        return StringUtils.join(Arrays.asList(values), ";", new JoinCallback<Object>() {
            public void handle(StringBuilder sb, Object value) {
                if (value != null) {
                    if (value instanceof Collection) {
                        sb.append(ICalPropertyScribe.list((Collection) value));
                    } else {
                        sb.append(ICalPropertyScribe.escape(value.toString()));
                    }
                }
            }
        });
    }

    protected static <T> String object(Map<String, List<T>> value) {
        return StringUtils.join((Map) value, ";", new JoinMapCallback<String, List<T>>() {
            public void handle(StringBuilder sb, String key, List<T> value) {
                sb.append(key.toUpperCase()).append('=').append(ICalPropertyScribe.list((Collection) value));
            }
        });
    }

    protected static ListMultimap<String, String> object(String value) {
        ListMultimap<String, String> map = new ListMultimap();
        for (String component : splitter(';').unescape(false).split(value)) {
            if (component.length() != 0) {
                List<String> values;
                String[] split = component.split("=", 2);
                String name = unescape(split[0].toUpperCase());
                if (split.length > 1) {
                    values = list(split[1]);
                } else {
                    values = Arrays.asList(new String[]{""});
                }
                map.putAll(name, values);
            }
        }
        return map;
    }

    protected static DateParser date(String value) {
        return new DateParser(value);
    }

    protected static DateWriter date(Date date) {
        return new DateWriter(date);
    }

    protected static CannotParseException missingXmlElements(ICalDataType... dataTypes) {
        String[] elements = new String[dataTypes.length];
        for (int i = 0; i < dataTypes.length; i++) {
            ICalDataType dataType = dataTypes[i];
            elements[i] = dataType == null ? EnvironmentCompat.MEDIA_UNKNOWN : dataType.getName().toLowerCase();
        }
        return missingXmlElements(elements);
    }

    protected static CannotParseException missingXmlElements(String... elements) {
        return new CannotParseException(23, Arrays.toString(elements));
    }
}
