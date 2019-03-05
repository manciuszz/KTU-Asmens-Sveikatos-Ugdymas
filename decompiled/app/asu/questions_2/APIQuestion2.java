package app.asu.questions_2;

import android.app.Activity;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

public class APIQuestion2 {
    private static final String URL = "http://asu.milasevicius.com/questionnaire3";
    private Activity activity;
    private OnResultListener onResultListener;

    interface OnResultListener {
        void onResult(boolean z, int i, String str);
    }

    public APIQuestion2(Activity activity) {
        this.activity = activity;
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void postParams(RequestParams params) {
        new AsyncHttpClient().post(URL, params, new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                APIQuestion2.this.onResultListener.onResult(true, statusCode, APIQuestion2.this.getString(responseBody));
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                APIQuestion2.this.onResultListener.onResult(false, statusCode, APIQuestion2.this.getString(responseBody));
            }
        });
    }

    public void postJSON(JSONObject json) {
        StringEntity entity = new StringEntity(json.toString(), "UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("APIQuestion2", "JSON: " + entity.toString());
        client.post(this.activity, URL, entity, "application/json;UTF-8", new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                APIQuestion2.this.onResultListener.onResult(true, statusCode, APIQuestion2.this.getString(responseBody));
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                APIQuestion2.this.onResultListener.onResult(false, statusCode, APIQuestion2.this.getString(responseBody));
            }
        });
    }

    public String getString(byte[] responseBody) {
        String output = "No response from ASU API";
        if (responseBody == null) {
            return output;
        }
        try {
            return new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return output;
        }
    }
}
