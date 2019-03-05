package app.asu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class IcsDownloader extends AsyncTask<Void, Void, Boolean> {
    private final String SCHEDULE_URL = "https://uais.cr.ktu.lt/ktuis/tv_rprt2.ical1?p2=";
    private OnScheduleDownloaded callback;
    private Context ctx;
    private ProgressDialog pDialog;

    public interface OnScheduleDownloaded {
        void onScheduleDownloadError();

        void onScheduleDownloaded();
    }

    public IcsDownloader(Context ctx, OnScheduleDownloaded callback) {
        this.ctx = ctx;
        this.callback = callback;
    }

    protected void onPreExecute() {
        this.pDialog = new ProgressDialog(this.ctx);
        this.pDialog.setCancelable(false);
        this.pDialog.setMessage(this.ctx.getString(R.string.please_wait));
        this.pDialog.show();
    }

    protected Boolean doInBackground(Void... params) {
        try {
            File dest_file = new File(this.ctx.getApplicationContext().getFilesDir().getAbsolutePath() + "/schedule.ics");
            URL u = new URL("https://uais.cr.ktu.lt/ktuis/tv_rprt2.ical1?p2=" + PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(SettingsActivity.NAME, ""));
            URLConnection conn = u.openConnection();
            conn.setConnectTimeout(LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            conn.setReadTimeout(LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            int contentLength = conn.getContentLength();
            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(dest_file));
            fos.write(buffer);
            fos.flush();
            fos.close();
            return Boolean.valueOf(true);
        } catch (FileNotFoundException e) {
            this.callback.onScheduleDownloadError();
            return Boolean.valueOf(false);
        } catch (Exception e2) {
            this.callback.onScheduleDownloadError();
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean result) {
        if (this.pDialog != null) {
            this.pDialog.cancel();
            Toast.makeText(this.ctx, this.ctx.getString(R.string.schedule_downloaded), 1).show();
            this.callback.onScheduleDownloaded();
        }
    }

    protected void onProgressUpdate(Void... values) {
    }
}
