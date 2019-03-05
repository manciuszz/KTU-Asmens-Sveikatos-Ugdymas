package biweekly.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Recurrence {
    private final List<DayOfWeek> byDay;
    private final List<Integer> byDayPrefixes;
    private final List<Integer> byHour;
    private final List<Integer> byMinute;
    private final List<Integer> byMonth;
    private final List<Integer> byMonthDay;
    private final List<Integer> bySecond;
    private final List<Integer> bySetPos;
    private final List<Integer> byWeekNo;
    private final List<Integer> byYearDay;
    private final Integer count;
    private final Frequency frequency;
    private final Integer interval;
    private final Date until;
    private final boolean untilHasTime;
    private final DayOfWeek workweekStarts;
    private final Map<String, List<String>> xrules;

    public static class Builder {
        private List<DayOfWeek> byDay;
        private List<Integer> byDayPrefixes;
        private List<Integer> byHour;
        private List<Integer> byMinute;
        private List<Integer> byMonth;
        private List<Integer> byMonthDay;
        private List<Integer> bySecond;
        private List<Integer> bySetPos;
        private List<Integer> byWeekNo;
        private List<Integer> byYearDay;
        private Integer count;
        private Frequency frequency;
        private Integer interval;
        private Date until;
        private boolean untilHasTime;
        private DayOfWeek workweekStarts;
        private ListMultimap<String, String> xrules;

        public Builder(Frequency frequency) {
            this.frequency = frequency;
            this.bySecond = new ArrayList(0);
            this.byMinute = new ArrayList(0);
            this.byHour = new ArrayList(0);
            this.byDay = new ArrayList(0);
            this.byDayPrefixes = new ArrayList(0);
            this.byMonthDay = new ArrayList(0);
            this.byYearDay = new ArrayList(0);
            this.byWeekNo = new ArrayList(0);
            this.byMonth = new ArrayList(0);
            this.bySetPos = new ArrayList(0);
            this.xrules = new ListMultimap(0);
        }

        public Builder(Recurrence recur) {
            this.frequency = recur.frequency;
            this.interval = recur.interval;
            this.count = recur.count;
            this.until = recur.until;
            this.untilHasTime = recur.untilHasTime;
            this.bySecond = new ArrayList(recur.bySecond);
            this.byMinute = new ArrayList(recur.byMinute);
            this.byHour = new ArrayList(recur.byHour);
            this.byDay = new ArrayList(recur.byDay);
            this.byDayPrefixes = new ArrayList(recur.byDayPrefixes);
            this.byMonthDay = new ArrayList(recur.byMonthDay);
            this.byYearDay = new ArrayList(recur.byYearDay);
            this.byWeekNo = new ArrayList(recur.byWeekNo);
            this.byMonth = new ArrayList(recur.byMonth);
            this.bySetPos = new ArrayList(recur.bySetPos);
            this.workweekStarts = recur.workweekStarts;
            this.xrules = new ListMultimap(recur.xrules);
        }

        public Builder frequency(Frequency frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder until(Date until) {
            return until(until, true);
        }

        public Builder until(Date until, boolean hasTime) {
            if (until == null) {
                this.until = null;
                this.untilHasTime = false;
            } else {
                this.until = new Date(until.getTime());
                this.untilHasTime = hasTime;
            }
            return this;
        }

        public Builder count(Integer count) {
            this.count = count;
            return this;
        }

        public Builder interval(Integer interval) {
            this.interval = interval;
            return this;
        }

        public Builder bySecond(Integer bySecond) {
            this.bySecond.add(bySecond);
            return this;
        }

        public Builder byMinute(Integer byMinute) {
            this.byMinute.add(byMinute);
            return this;
        }

        public Builder byHour(Integer byHour) {
            this.byHour.add(byHour);
            return this;
        }

        public Builder byMonthDay(Integer byMonthDay) {
            this.byMonthDay.add(byMonthDay);
            return this;
        }

        public Builder byYearDay(Integer byYearDay) {
            this.byYearDay.add(byYearDay);
            return this;
        }

        public Builder byWeekNo(Integer byWeekNo) {
            this.byWeekNo.add(byWeekNo);
            return this;
        }

        public Builder byMonth(Integer byMonth) {
            this.byMonth.add(byMonth);
            return this;
        }

        public Builder bySetPos(Integer bySetPos) {
            this.bySetPos.add(bySetPos);
            return this;
        }

        public Builder byDay(DayOfWeek byDay) {
            return byDay(null, byDay);
        }

        public Builder byDay(Integer prefix, DayOfWeek byDay) {
            this.byDayPrefixes.add(prefix);
            this.byDay.add(byDay);
            return this;
        }

        public Builder workweekStarts(DayOfWeek workweekStarts) {
            this.workweekStarts = workweekStarts;
            return this;
        }

        public Builder xrule(String name, String value) {
            name = name.toUpperCase();
            if (value == null) {
                this.xrules.removeAll(name);
            } else {
                this.xrules.put(name, value);
            }
            return this;
        }

        public Recurrence build() {
            return new Recurrence();
        }
    }

    public enum DayOfWeek {
        MONDAY("MO"),
        TUESDAY("TU"),
        WEDNESDAY("WE"),
        THURSDAY("TH"),
        FRIDAY("FR"),
        SATURDAY("SA"),
        SUNDAY("SU");
        
        private final String abbr;

        private DayOfWeek(String abbr) {
            this.abbr = abbr;
        }

        public String getAbbr() {
            return this.abbr;
        }

        public static DayOfWeek valueOfAbbr(String abbr) {
            for (DayOfWeek day : values()) {
                if (day.abbr.equalsIgnoreCase(abbr)) {
                    return day;
                }
            }
            return null;
        }
    }

    public enum Frequency {
        SECONDLY,
        MINUTELY,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

    private Recurrence(Builder builder) {
        this.frequency = builder.frequency;
        this.interval = builder.interval;
        this.count = builder.count;
        this.until = builder.until;
        this.untilHasTime = builder.untilHasTime;
        this.bySecond = Collections.unmodifiableList(builder.bySecond);
        this.byMinute = Collections.unmodifiableList(builder.byMinute);
        this.byHour = Collections.unmodifiableList(builder.byHour);
        this.byMonthDay = Collections.unmodifiableList(builder.byMonthDay);
        this.byYearDay = Collections.unmodifiableList(builder.byYearDay);
        this.byWeekNo = Collections.unmodifiableList(builder.byWeekNo);
        this.byMonth = Collections.unmodifiableList(builder.byMonth);
        this.bySetPos = Collections.unmodifiableList(builder.bySetPos);
        this.byDay = Collections.unmodifiableList(builder.byDay);
        this.byDayPrefixes = Collections.unmodifiableList(builder.byDayPrefixes);
        this.workweekStarts = builder.workweekStarts;
        Map<String, List<String>> map = builder.xrules.getMap();
        for (Entry<String, List<String>> entry : map.entrySet()) {
            map.put((String) entry.getKey(), Collections.unmodifiableList((List) entry.getValue()));
        }
        this.xrules = Collections.unmodifiableMap(map);
    }

    public Frequency getFrequency() {
        return this.frequency;
    }

    public Date getUntil() {
        return this.until == null ? null : new Date(this.until.getTime());
    }

    public boolean hasTimeUntilDate() {
        return this.untilHasTime;
    }

    public Integer getCount() {
        return this.count;
    }

    public Integer getInterval() {
        return this.interval;
    }

    public List<Integer> getBySecond() {
        return this.bySecond;
    }

    public List<Integer> getByMinute() {
        return this.byMinute;
    }

    public List<Integer> getByHour() {
        return this.byHour;
    }

    public List<DayOfWeek> getByDay() {
        return this.byDay;
    }

    public List<Integer> getByDayPrefixes() {
        return this.byDayPrefixes;
    }

    public List<Integer> getByMonthDay() {
        return this.byMonthDay;
    }

    public List<Integer> getByYearDay() {
        return this.byYearDay;
    }

    public List<Integer> getByWeekNo() {
        return this.byWeekNo;
    }

    public List<Integer> getByMonth() {
        return this.byMonth;
    }

    public List<Integer> getBySetPos() {
        return this.bySetPos;
    }

    public DayOfWeek getWorkweekStarts() {
        return this.workweekStarts;
    }

    public Map<String, List<String>> getXRules() {
        return this.xrules;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((((((((((((((((this.byDay == null ? 0 : this.byDay.hashCode()) + 31) * 31) + (this.byDayPrefixes == null ? 0 : this.byDayPrefixes.hashCode())) * 31) + (this.byHour == null ? 0 : this.byHour.hashCode())) * 31) + (this.byMinute == null ? 0 : this.byMinute.hashCode())) * 31) + (this.byMonth == null ? 0 : this.byMonth.hashCode())) * 31) + (this.byMonthDay == null ? 0 : this.byMonthDay.hashCode())) * 31) + (this.bySecond == null ? 0 : this.bySecond.hashCode())) * 31) + (this.bySetPos == null ? 0 : this.bySetPos.hashCode())) * 31) + (this.byWeekNo == null ? 0 : this.byWeekNo.hashCode())) * 31) + (this.byYearDay == null ? 0 : this.byYearDay.hashCode())) * 31) + (this.count == null ? 0 : this.count.hashCode())) * 31) + (this.xrules == null ? 0 : this.xrules.hashCode())) * 31) + (this.frequency == null ? 0 : this.frequency.hashCode())) * 31) + (this.interval == null ? 0 : this.interval.hashCode())) * 31) + (this.until == null ? 0 : this.until.hashCode())) * 31) + (this.untilHasTime ? 1231 : 1237)) * 31;
        if (this.workweekStarts != null) {
            i = this.workweekStarts.hashCode();
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
        Recurrence other = (Recurrence) obj;
        if (this.byDay == null) {
            if (other.byDay != null) {
                return false;
            }
        } else if (!this.byDay.equals(other.byDay)) {
            return false;
        }
        if (this.byDayPrefixes == null) {
            if (other.byDayPrefixes != null) {
                return false;
            }
        } else if (!this.byDayPrefixes.equals(other.byDayPrefixes)) {
            return false;
        }
        if (this.byHour == null) {
            if (other.byHour != null) {
                return false;
            }
        } else if (!this.byHour.equals(other.byHour)) {
            return false;
        }
        if (this.byMinute == null) {
            if (other.byMinute != null) {
                return false;
            }
        } else if (!this.byMinute.equals(other.byMinute)) {
            return false;
        }
        if (this.byMonth == null) {
            if (other.byMonth != null) {
                return false;
            }
        } else if (!this.byMonth.equals(other.byMonth)) {
            return false;
        }
        if (this.byMonthDay == null) {
            if (other.byMonthDay != null) {
                return false;
            }
        } else if (!this.byMonthDay.equals(other.byMonthDay)) {
            return false;
        }
        if (this.bySecond == null) {
            if (other.bySecond != null) {
                return false;
            }
        } else if (!this.bySecond.equals(other.bySecond)) {
            return false;
        }
        if (this.bySetPos == null) {
            if (other.bySetPos != null) {
                return false;
            }
        } else if (!this.bySetPos.equals(other.bySetPos)) {
            return false;
        }
        if (this.byWeekNo == null) {
            if (other.byWeekNo != null) {
                return false;
            }
        } else if (!this.byWeekNo.equals(other.byWeekNo)) {
            return false;
        }
        if (this.byYearDay == null) {
            if (other.byYearDay != null) {
                return false;
            }
        } else if (!this.byYearDay.equals(other.byYearDay)) {
            return false;
        }
        if (this.count == null) {
            if (other.count != null) {
                return false;
            }
        } else if (!this.count.equals(other.count)) {
            return false;
        }
        if (this.xrules == null) {
            if (other.xrules != null) {
                return false;
            }
        } else if (!this.xrules.equals(other.xrules)) {
            return false;
        }
        if (this.frequency != other.frequency) {
            return false;
        }
        if (this.interval == null) {
            if (other.interval != null) {
                return false;
            }
        } else if (!this.interval.equals(other.interval)) {
            return false;
        }
        if (this.until == null) {
            if (other.until != null) {
                return false;
            }
        } else if (!this.until.equals(other.until)) {
            return false;
        }
        if (this.untilHasTime != other.untilHasTime) {
            return false;
        }
        if (this.workweekStarts != other.workweekStarts) {
            return false;
        }
        return true;
    }
}
