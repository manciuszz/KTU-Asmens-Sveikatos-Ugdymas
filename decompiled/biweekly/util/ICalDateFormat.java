package biweekly.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public enum ICalDateFormat {
    DATE_BASIC("\\d{8}", "yyyyMMdd"),
    DATE_EXTENDED("\\d{4}-\\d{2}-\\d{2}", "yyyy-MM-dd"),
    DATE_TIME_BASIC("\\d{8}T\\d{6}[-\\+]\\d{4}", "yyyyMMdd'T'HHmmssZ"),
    DATE_TIME_BASIC_WITHOUT_TZ("\\d{8}T\\d{6}", "yyyyMMdd'T'HHmmss"),
    DATE_TIME_EXTENDED("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[-\\+]\\d{2}:\\d{2}", "yyyy-MM-dd'T'HH:mm:ssZ") {
        public DateFormat getDateFormat(TimeZone timezone) {
            DateFormat df = new SimpleDateFormat(this.formatStr) {
                public Date parse(String str) throws ParseException {
                    int index = str.lastIndexOf(58);
                    return super.parse(str.substring(0, index) + str.substring(index + 1));
                }

                public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                    StringBuffer sb = super.format(date, toAppendTo, fieldPosition);
                    sb.insert(sb.length() - 2, ':');
                    return sb;
                }
            };
            if (timezone != null) {
                df.setTimeZone(timezone);
            }
            return df;
        }
    },
    DATE_TIME_EXTENDED_WITHOUT_TZ("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}", "yyyy-MM-dd'T'HH:mm:ss"),
    UTC_TIME_BASIC("\\d{8}T\\d{6}Z", "yyyyMMdd'T'HHmmss'Z'") {
        public DateFormat getDateFormat(TimeZone timezone) {
            return super.getDateFormat(TimeZone.getTimeZone("UTC"));
        }
    },
    UTC_TIME_EXTENDED("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z", "yyyy-MM-dd'T'HH:mm:ss'Z'") {
        public DateFormat getDateFormat(TimeZone timezone) {
            return super.getDateFormat(TimeZone.getTimeZone("UTC"));
        }
    };
    
    protected final String formatStr;
    private final Pattern pattern;

    private ICalDateFormat(String regex, String formatStr) {
        this.pattern = Pattern.compile(regex);
        this.formatStr = formatStr;
    }

    public boolean matches(String dateStr) {
        return this.pattern.matcher(dateStr).matches();
    }

    public DateFormat getDateFormat() {
        return getDateFormat(null);
    }

    public DateFormat getDateFormat(TimeZone timezone) {
        DateFormat df = new SimpleDateFormat(this.formatStr);
        if (timezone != null) {
            df.setTimeZone(timezone);
        }
        return df;
    }

    public String format(Date date) {
        return format(date, null);
    }

    public String format(Date date, TimeZone timezone) {
        return getDateFormat(timezone).format(date);
    }

    public static ICalDateFormat find(String dateStr) {
        for (ICalDateFormat format : values()) {
            if (format.matches(dateStr)) {
                return format;
            }
        }
        return null;
    }

    public static Date parse(String dateStr) {
        return parse(dateStr, null);
    }

    public static Date parse(String dateStr, TimeZone timezone) {
        ICalDateFormat format = find(dateStr);
        if (format == null) {
            throw parseException(dateStr);
        }
        try {
            return format.getDateFormat(timezone).parse(dateStr);
        } catch (ParseException e) {
            throw parseException(dateStr);
        }
    }

    public static boolean dateHasTime(String dateStr) {
        return dateStr.contains("T");
    }

    public static boolean dateHasTimezone(String dateStr) {
        return dateStr.endsWith("Z") || dateStr.matches(".*?[-+]\\d\\d:?\\d\\d");
    }

    public static TimeZone parseTimeZoneId(String timezoneId) {
        TimeZone timezone = TimeZone.getTimeZone(timezoneId);
        return "GMT".equals(timezone.getID()) ? null : timezone;
    }

    private static IllegalArgumentException parseException(String dateStr) {
        return new IllegalArgumentException("Date string \"" + dateStr + "\" is not in a valid ISO-8601 format.");
    }
}
