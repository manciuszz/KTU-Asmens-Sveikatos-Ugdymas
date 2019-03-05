package app.asu.Models;

import android.content.Context;
import app.asu.StaticMethods;
import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarModel {
    private List<LectureModel> all_lectures = new ArrayList();
    private Context ctx;
    private List<Long> semester_weeks = new ArrayList();

    public CalendarModel(Context ctx) {
        this.ctx = ctx;
    }

    public void parseCalendar() {
        try {
            InputStream fin = new FileInputStream(this.ctx.getApplicationContext().getFilesDir().getAbsolutePath() + "/schedule.ics");
            try {
                ICalendar ical = Biweekly.parse(fin).first();
                for (int i = 0; i < ical.getEvents().size(); i++) {
                    VEvent event = (VEvent) ical.getEvents().get(i);
                    LectureModel lecture = new LectureModel();
                    lecture.setStartTime(event.getDateStart().getValue().getTime());
                    lecture.setEndTime(event.getDateEnd().getValue().getTime());
                    lecture.setAuditorium(((String) event.getLocation().getValue()).split("\\;", 2)[0]);
                    lecture.setPlace((String) event.getLocation().getValue());
                    String[] summary = ((String) event.getSummary().getValue()).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, 2);
                    lecture.setLecture_code(summary[0]);
                    lecture.setLecture(summary[1]);
                    String[] description = ((String) event.getDescription().getValue()).split("\n", 2);
                    lecture.setCategory(description[0]);
                    lecture.setLecturer(description[1]);
                    this.all_lectures.add(lecture);
                    long weekMillis = getWeek(lecture.getStartTime());
                    if (!this.semester_weeks.contains(Long.valueOf(weekMillis))) {
                        this.semester_weeks.add(Long.valueOf(weekMillis));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                InputStream inputStream = fin;
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public List<LectureModel> getDayLectures(long day) {
        if (Calendar.getInstance().getFirstDayOfWeek() == 2) {
            day -= StaticMethods.DAY_LONG;
        }
        List<LectureModel> day_lectures = new ArrayList();
        for (LectureModel lecture : this.all_lectures) {
            if (lecture.getStartTime() > day && lecture.getStartTime() < day + StaticMethods.DAY_LONG) {
                day_lectures.add(lecture);
            }
        }
        return day_lectures;
    }

    public List<Long> getSemester_weeks() {
        return this.semester_weeks;
    }

    public int getSemesterWeeksCount() {
        if (this.semester_weeks.size() == 0) {
            return 0;
        }
        return ((int) ((((Long) this.semester_weeks.get(this.semester_weeks.size() - 1)).longValue() - getFirstSemesterWeek()) / StaticMethods.WEEK_LONG)) + 1;
    }

    private long getWeek(long day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(day));
        cal.set(11, 0);
        cal.clear(12);
        cal.clear(13);
        cal.clear(14);
        cal.set(7, cal.getFirstDayOfWeek() + 1);
        return cal.getTimeInMillis();
    }

    public long getFirstSemesterWeek() {
        if (this.semester_weeks.size() == 0) {
            return 0;
        }
        return ((Long) this.semester_weeks.get(0)).longValue();
    }
}
