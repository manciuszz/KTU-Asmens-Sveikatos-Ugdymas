package app.asu;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import app.asu.Databases.ActivitiesDB;
import app.asu.Models.ActivityModel;
import com.google.android.gms.location.ActivityRecognitionResult;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckMovementService extends IntentService {
    int activityID = -1;
    long endTime;

    public CheckMovementService() {
        super("CheckMovementService");
    }

    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            int currentActivityID = result.getMostProbableActivity().getType();
            if (currentActivityID == 2) {
                currentActivityID = result.getActivityConfidence(8) > result.getActivityConfidence(7) ? 8 : 7;
            }
            SharedPreferences prefs = getSharedPreferences("activityID", 0);
            this.activityID = prefs.getInt("activityID", -1);
            if (this.activityID != currentActivityID) {
                if (this.activityID != -1) {
                    this.endTime = new Date().getTime();
                    String activity = "";
                    switch (this.activityID) {
                        case 1:
                            activity = "Bicycle ";
                            break;
                        case 3:
                            activity = "Still   ";
                            break;
                        case 7:
                            activity = "Walking ";
                            break;
                        case 8:
                            activity = "Running ";
                            break;
                    }
                    if (!activity.equals("")) {
                        ActivitiesDB activitiesDB = new ActivitiesDB(getApplicationContext());
                        activitiesDB.addActivity(new ActivityModel(this.activityID, prefs.getLong("startTime", 0), this.endTime));
                        activitiesDB.close();
                        Log.i("Tracking", activity + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(prefs.getLong("startTime", 0))) + " - " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(this.endTime)));
                    }
                }
                Editor editor = getSharedPreferences("activityID", 0).edit();
                editor.remove("activityID");
                editor.remove("startTime");
                editor.putInt("activityID", currentActivityID);
                editor.putLong("startTime", new Date().getTime());
                editor.apply();
            }
        }
    }
}
