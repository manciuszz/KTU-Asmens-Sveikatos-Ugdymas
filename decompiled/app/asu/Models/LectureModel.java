package app.asu.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LectureModel {
    private String auditorium;
    private String category;
    private long endTime;
    private String lecture;
    private String lecture_code;
    private String lecturer;
    private String place;
    private long startTime;

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLecture() {
        return this.lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getAuditorium() {
        return this.auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    public String getLecture_code() {
        return this.lecture_code;
    }

    public void setLecture_code(String lecture_code) {
        this.lecture_code = lecture_code;
    }

    public String getLectureTime() {
        Date start = new Date(this.startTime);
        Date end = new Date(this.endTime);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("EET"));
        return sdf.format(start) + " - " + sdf.format(end);
    }

    public int getDuration() {
        return (int) (((this.endTime - this.startTime) / 1000) / 60);
    }
}
