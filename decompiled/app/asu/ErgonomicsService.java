package app.asu;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import app.asu.Databases.ActivitiesDB;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ErgonomicsService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {
    ActivityRecognitionClient mActivityRecognitionClient;

    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
        private DownloadFilesTask() {
        }

        protected Void doInBackground(Void... empty) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://asu.milasevicius.com/data/activity");
            try {
                ActivitiesDB db = new ActivitiesDB(ErgonomicsService.this.getApplicationContext());
                db.getReadableDatabase();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ErgonomicsService.this.getApplicationContext());
                long start_date = preferences.getLong("activity_save_date", 0);
                JSONObject object = new JSONObject();
                object.put("id", StaticMethods.getUniquePsuedoID());
                object.put("objects", db.getActitiviesJSON(start_date));
                StringEntity se = new StringEntity(object.toString());
                se.setContentType(new BasicHeader("Content-Type", RequestParams.APPLICATION_JSON));
                httppost.setEntity(se);
                String rsp = EntityUtils.toString(httpclient.execute(httppost).getEntity());
                Log.i("Data", rsp);
                Log.i("DataLog", object.toString());
                if (rsp.toLowerCase().equals("success")) {
                    Editor editor = preferences.edit();
                    editor.putLong("activity_save_date", System.currentTimeMillis());
                    Log.i("time", String.valueOf(System.currentTimeMillis()));
                    editor.apply();
                }
            } catch (ClientProtocolException e) {
            } catch (IOException e2) {
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            return null;
        }
    }

    public IBinder onBind(Intent intent) {
        Log.i("Autostart", "ErgonomicsService - onBind");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Autostart", "ErgonomicsService - onStartCommand");
        final Handler handler = new Handler();
        new Timer().schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if (StaticMethods.isNetworkConnected(ErgonomicsService.this.getApplication().getApplicationContext())) {
                                new DownloadFilesTask().execute(new Void[0]);
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        }, 0, 14400000);
        this.mActivityRecognitionClient = new ActivityRecognitionClient(this, this, this);
        this.mActivityRecognitionClient.connect();
        return 0;
    }

    public void onConnected(Bundle connectionHint) {
        Log.i("Autostart", "ErgonomicsService - onConnected");
        Editor editor = getSharedPreferences("activityID", 0).edit();
        editor.remove("activityID");
        editor.remove("startDate");
        editor.apply();
        this.mActivityRecognitionClient.requestActivityUpdates(1000, PendingIntent.getService(this, 0, new Intent(this, CheckMovementService.class), 134217728));
    }

    public void onDisconnected() {
        Log.i("Autostart", "ErgonomicsService - onDisconnected");
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Autostart", "ErgonomicsService - onConnectionFailed");
    }
}
