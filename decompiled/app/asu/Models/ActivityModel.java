package app.asu.Models;

public class ActivityModel {
    private int activityID;
    private long endDate;
    private int id;
    private long startDate;

    public ActivityModel(int activityID, long startDate, long endDate) {
        this.activityID = activityID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ActivityModel(int id, int activityID, long startDate, long endDate) {
        this.id = id;
        this.activityID = activityID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityID() {
        return this.activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public long getStartDate() {
        return this.startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return this.endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
