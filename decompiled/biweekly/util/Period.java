package biweekly.util;

import java.util.Date;

public final class Period {
    private final Duration duration;
    private final Date endDate;
    private final Date startDate;

    public Period(Date startDate, Date endDate) {
        this.startDate = copy(startDate);
        this.endDate = copy(endDate);
        this.duration = null;
    }

    public Period(Date startDate, Duration duration) {
        this.startDate = copy(startDate);
        this.duration = duration;
        this.endDate = null;
    }

    public Period(Period period) {
        this.startDate = period.startDate;
        this.endDate = period.endDate;
        this.duration = period.duration;
    }

    public Date getStartDate() {
        return copy(this.startDate);
    }

    public Date getEndDate() {
        return copy(this.endDate);
    }

    public Duration getDuration() {
        return this.duration;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((this.duration == null ? 0 : this.duration.hashCode()) + 31) * 31) + (this.endDate == null ? 0 : this.endDate.hashCode())) * 31;
        if (this.startDate != null) {
            i = this.startDate.hashCode();
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
        Period other = (Period) obj;
        if (this.duration == null) {
            if (other.duration != null) {
                return false;
            }
        } else if (!this.duration.equals(other.duration)) {
            return false;
        }
        if (this.endDate == null) {
            if (other.endDate != null) {
                return false;
            }
        } else if (!this.endDate.equals(other.endDate)) {
            return false;
        }
        if (this.startDate == null) {
            if (other.startDate != null) {
                return false;
            }
            return true;
        } else if (this.startDate.equals(other.startDate)) {
            return true;
        } else {
            return false;
        }
    }

    private Date copy(Date date) {
        return date == null ? null : new Date(date.getTime());
    }
}
