package biweekly.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtcOffset {
    private final int hour;
    private final int minute;

    public UtcOffset(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static UtcOffset parse(String text) {
        Matcher m = Pattern.compile("^([-\\+])?(\\d{1,2})(:?(\\d{2}))?$").matcher(text);
        if (m.find()) {
            boolean positive;
            if ("-".equals(m.group(1))) {
                positive = false;
            } else {
                positive = true;
            }
            int hourOffset = Integer.parseInt(m.group(2));
            if (!positive) {
                hourOffset *= -1;
            }
            String minuteStr = m.group(4);
            return new UtcOffset(hourOffset, minuteStr == null ? 0 : Integer.parseInt(minuteStr));
        }
        throw new IllegalArgumentException("Offset string is not in ISO8610 format: " + text);
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean extended) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.hour >= 0 ? '+' : '-');
        int hour = Math.abs(this.hour);
        if (hour < 10) {
            sb.append('0');
        }
        sb.append(hour);
        if (extended) {
            sb.append(':');
        }
        if (this.minute < 10) {
            sb.append('0');
        }
        sb.append(this.minute);
        return sb.toString();
    }

    public int hashCode() {
        return ((this.hour + 31) * 31) + this.minute;
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
        UtcOffset other = (UtcOffset) obj;
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        return true;
    }
}
