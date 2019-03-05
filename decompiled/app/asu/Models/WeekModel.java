package app.asu.Models;

import app.asu.StaticMethods;
import java.util.List;

public class WeekModel {
    private CalendarModel calendar;
    private List<LectureModel> friday_lectures;
    private List<LectureModel> monday_lectures;
    private List<LectureModel> thursday_lectures;
    private List<LectureModel> tuesday_lectures;
    private List<LectureModel> wednesday_lectures;
    private long weekStart;

    public WeekModel(long weekStart, CalendarModel calendar) {
        this.weekStart = weekStart;
        this.calendar = calendar;
    }

    public List<LectureModel> getMonday_lectures() {
        this.monday_lectures = this.calendar.getDayLectures(this.weekStart);
        return this.monday_lectures;
    }

    public List<LectureModel> getTuesday_lectures() {
        this.tuesday_lectures = this.calendar.getDayLectures(this.weekStart + StaticMethods.DAY_LONG);
        return this.tuesday_lectures;
    }

    public List<LectureModel> getWednesday_lectures() {
        this.wednesday_lectures = this.calendar.getDayLectures(this.weekStart + 172800000);
        return this.wednesday_lectures;
    }

    public List<LectureModel> getThursday_lectures() {
        this.thursday_lectures = this.calendar.getDayLectures(this.weekStart + 259200000);
        return this.thursday_lectures;
    }

    public List<LectureModel> getFriday_lectures() {
        this.friday_lectures = this.calendar.getDayLectures(this.weekStart + 345600000);
        return this.friday_lectures;
    }
}
