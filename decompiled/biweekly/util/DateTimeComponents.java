package biweekly.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateTimeComponents {
    private static final Pattern regex = Pattern.compile("^(\\d{4})-?(\\d{2})-?(\\d{2})(T(\\d{2}):?(\\d{2}):?(\\d{2})(Z?))?.*");
    private final int date;
    private final int hour;
    private final int minute;
    private final int month;
    private final int second;
    private final boolean utc;
    private final int year;

    public static DateTimeComponents parse(String dateString) {
        Matcher m = regex.matcher(dateString);
        if (m.find()) {
            int i = 1 + 1;
            int year = Integer.parseInt(m.group(1));
            int i2 = i + 1;
            int month = Integer.parseInt(m.group(i));
            i = i2 + 1;
            int date = Integer.parseInt(m.group(i2));
            i2 = i + 1;
            i = i2 + 1;
            String hourStr = m.group(i2);
            int hour = hourStr == null ? 0 : Integer.parseInt(hourStr);
            i2 = i + 1;
            String minuteStr = m.group(i);
            int minute = minuteStr == null ? 0 : Integer.parseInt(minuteStr);
            i = i2 + 1;
            String secondStr = m.group(i2);
            i2 = i + 1;
            return new DateTimeComponents(year, month, date, hour, minute, secondStr == null ? 0 : Integer.parseInt(secondStr), "Z".equals(m.group(i)));
        }
        throw new IllegalArgumentException("Cannot parse date: " + dateString);
    }

    public DateTimeComponents(DateTimeComponents original, Integer year, Integer month, Integer date, Integer hour, Integer minute, Integer second, Boolean utc) {
        this(year == null ? original.year : year.intValue(), month == null ? original.month : month.intValue(), date == null ? original.date : date.intValue(), hour == null ? original.hour : hour.intValue(), minute == null ? original.minute : minute.intValue(), second == null ? original.second : second.intValue(), utc == null ? original.utc : utc.booleanValue());
    }

    public DateTimeComponents(int year, int month, int date, int hour, int minute, int second, boolean utc) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.utc = utc;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDate() {
        return this.date;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public boolean isUtc() {
        return this.utc;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean extended) {
        NumberFormat nf = new DecimalFormat("00");
        String dash = extended ? "-" : "";
        String colon = extended ? ":" : "";
        return this.year + dash + nf.format((long) this.month) + dash + nf.format((long) this.date) + "T" + nf.format((long) this.hour) + colon + nf.format((long) this.minute) + colon + nf.format((long) this.second) + (this.utc ? "Z" : "");
    }

    public Date toDate() {
        Calendar c = Calendar.getInstance(this.utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault());
        c.clear();
        c.set(1, this.year);
        c.set(2, this.month - 1);
        c.set(5, this.date);
        c.set(11, this.hour);
        c.set(12, this.minute);
        c.set(13, this.second);
        return c.getTime();
    }

    public int hashCode() {
        return ((((((((((((this.date + 31) * 31) + this.hour) * 31) + this.minute) * 31) + this.month) * 31) + this.second) * 31) + (this.utc ? 1231 : 1237)) * 31) + this.year;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DateTimeComponents other = (DateTimeComponents) obj;
        if (this.date != other.date) {
            return false;
        }
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.second != other.second) {
            return false;
        }
        if (this.utc != other.utc) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
}
