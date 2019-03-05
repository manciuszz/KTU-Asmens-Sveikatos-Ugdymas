package biweekly.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Duration {
    private final Integer days;
    private final Integer hours;
    private final Integer minutes;
    private final boolean prior;
    private final Integer seconds;
    private final Integer weeks;

    public static class Builder {
        private Integer days;
        private Integer hours;
        private Integer minutes;
        private boolean prior = false;
        private Integer seconds;
        private Integer weeks;

        public Builder(Duration source) {
            this.weeks = source.weeks;
            this.days = source.days;
            this.hours = source.hours;
            this.minutes = source.minutes;
            this.seconds = source.seconds;
            this.prior = source.prior;
        }

        public Builder weeks(Integer weeks) {
            this.weeks = weeks;
            return this;
        }

        public Builder days(Integer days) {
            this.days = days;
            return this;
        }

        public Builder hours(Integer hours) {
            this.hours = hours;
            return this;
        }

        public Builder minutes(Integer minutes) {
            this.minutes = minutes;
            return this;
        }

        public Builder seconds(Integer seconds) {
            this.seconds = seconds;
            return this;
        }

        public Builder prior(boolean prior) {
            this.prior = prior;
            return this;
        }

        public Duration build() {
            return new Duration();
        }
    }

    private Duration(Builder b) {
        this.weeks = b.weeks;
        this.days = b.days;
        this.hours = b.hours;
        this.minutes = b.minutes;
        this.seconds = b.seconds;
        this.prior = b.prior;
    }

    public static Duration parse(String value) {
        if (value.matches("-?P.*")) {
            return builder().prior(value.startsWith("-")).weeks(parseComponent(value, 'W')).days(parseComponent(value, 'D')).hours(parseComponent(value, 'H')).minutes(parseComponent(value, 'M')).seconds(parseComponent(value, 'S')).build();
        }
        throw new IllegalArgumentException("Invalid duration string: " + value);
    }

    public static Duration diff(Date start, Date end) {
        return fromMillis(end.getTime() - start.getTime());
    }

    public static Duration fromMillis(long milliseconds) {
        Builder builder = builder();
        if (milliseconds < 0) {
            builder.prior(true);
            milliseconds *= -1;
        }
        int seconds = (int) (milliseconds / 1000);
        Integer weeks = Integer.valueOf(seconds / 604800);
        if (weeks.intValue() > 0) {
            builder.weeks(weeks);
        }
        seconds %= 604800;
        Integer days = Integer.valueOf(seconds / 86400);
        if (days.intValue() > 0) {
            builder.days(days);
        }
        seconds %= 86400;
        Integer hours = Integer.valueOf(seconds / 3600);
        if (hours.intValue() > 0) {
            builder.hours(hours);
        }
        seconds %= 3600;
        Integer minutes = Integer.valueOf(seconds / 60);
        if (minutes.intValue() > 0) {
            builder.minutes(minutes);
        }
        seconds %= 60;
        if (seconds > 0) {
            builder.seconds(Integer.valueOf(seconds));
        }
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    private static Integer parseComponent(String value, char ch) {
        Matcher m = Pattern.compile("(\\d+)" + ch).matcher(value);
        return m.find() ? Integer.valueOf(m.group(1)) : null;
    }

    public boolean isPrior() {
        return this.prior;
    }

    public Integer getWeeks() {
        return this.weeks;
    }

    public Integer getDays() {
        return this.days;
    }

    public Integer getHours() {
        return this.hours;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public Date add(Date date) {
        int i;
        int i2 = -1;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (this.weeks != null) {
            c.add(5, (this.weeks.intValue() * (this.prior ? -1 : 1)) * 7);
        }
        if (this.days != null) {
            int intValue = this.days.intValue();
            if (this.prior) {
                i = -1;
            } else {
                i = 1;
            }
            c.add(5, intValue * i);
        }
        if (this.hours != null) {
            intValue = this.hours.intValue();
            if (this.prior) {
                i = -1;
            } else {
                i = 1;
            }
            c.add(11, intValue * i);
        }
        if (this.minutes != null) {
            intValue = this.minutes.intValue();
            if (this.prior) {
                i = -1;
            } else {
                i = 1;
            }
            c.add(12, intValue * i);
        }
        if (this.seconds != null) {
            i = this.seconds.intValue();
            if (!this.prior) {
                i2 = 1;
            }
            c.add(13, i * i2);
        }
        return c.getTime();
    }

    public long toMillis() {
        long totalSeconds = 0;
        if (this.weeks != null) {
            totalSeconds = 0 + (604800 * ((long) this.weeks.intValue()));
        }
        if (this.days != null) {
            totalSeconds += 86400 * ((long) this.days.intValue());
        }
        if (this.hours != null) {
            totalSeconds += 3600 * ((long) this.hours.intValue());
        }
        if (this.minutes != null) {
            totalSeconds += 60 * ((long) this.minutes.intValue());
        }
        if (this.seconds != null) {
            totalSeconds += (long) this.seconds.intValue();
        }
        if (this.prior) {
            totalSeconds *= -1;
        }
        return 1000 * totalSeconds;
    }

    public boolean hasTime() {
        return (this.hours == null && this.minutes == null && this.seconds == null) ? false : true;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((this.days == null ? 0 : this.days.hashCode()) + 31) * 31) + (this.hours == null ? 0 : this.hours.hashCode())) * 31) + (this.minutes == null ? 0 : this.minutes.hashCode())) * 31) + (this.prior ? 1231 : 1237)) * 31) + (this.seconds == null ? 0 : this.seconds.hashCode())) * 31;
        if (this.weeks != null) {
            i = this.weeks.hashCode();
        }
        return hashCode + i;
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
        Duration other = (Duration) obj;
        if (this.days == null) {
            if (other.days != null) {
                return false;
            }
        } else if (!this.days.equals(other.days)) {
            return false;
        }
        if (this.hours == null) {
            if (other.hours != null) {
                return false;
            }
        } else if (!this.hours.equals(other.hours)) {
            return false;
        }
        if (this.minutes == null) {
            if (other.minutes != null) {
                return false;
            }
        } else if (!this.minutes.equals(other.minutes)) {
            return false;
        }
        if (this.prior != other.prior) {
            return false;
        }
        if (this.seconds == null) {
            if (other.seconds != null) {
                return false;
            }
        } else if (!this.seconds.equals(other.seconds)) {
            return false;
        }
        if (this.weeks == null) {
            if (other.weeks != null) {
                return false;
            }
            return true;
        } else if (this.weeks.equals(other.weeks)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.prior) {
            sb.append('-');
        }
        sb.append('P');
        if (this.weeks != null) {
            sb.append(this.weeks).append('W');
        }
        if (this.days != null) {
            sb.append(this.days).append('D');
        }
        if (hasTime()) {
            sb.append('T');
            if (this.hours != null) {
                sb.append(this.hours).append('H');
            }
            if (this.minutes != null) {
                sb.append(this.minutes).append('M');
            }
            if (this.seconds != null) {
                sb.append(this.seconds).append('S');
            }
        }
        return sb.toString();
    }
}
