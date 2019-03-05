package app.asu.questions_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import app.asu.R;
import app.asu.StartActivity;
import app.asu.questions_2.Questions2Data.Data;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Questions2Activity extends Activity implements OnResultListener {
    private static final String TAG = Questions2Activity.class.getSimpleName();

    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_2);
        final APIQuestion2 apiClient = new APIQuestion2(this);
        apiClient.setOnResultListener(this);
        ListView competitiveListView = (ListView) findViewById(R.id.competitiveListView);
        CheckBoxListAdapter competitiveAdapter = new CheckBoxListAdapter(this);
        competitiveAdapter.setItems(getList(getResources().getStringArray(R.array.competitive_sports)));
        competitiveListView.setAdapter(competitiveAdapter);
        ListView casualListView = (ListView) findViewById(R.id.casualListView);
        CheckBoxListAdapter casualAdapter = new CheckBoxListAdapter(this);
        casualAdapter.setItems(getList(getResources().getStringArray(R.array.casual_sports)));
        casualListView.setAdapter(casualAdapter);
        final Questions2Data questions2Data = new Questions2Data(findViewById(16908290), casualAdapter, competitiveAdapter);
        ((Button) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Data data = questions2Data.getData();
                if (data.isFullyFilled()) {
                    JSONObject params = data.toJSON();
                    Log.d(Questions2Activity.TAG, "Request params: " + params.toString());
                    apiClient.postJSON(params);
                    return;
                }
                Toast.makeText(Questions2Activity.this.getApplicationContext(), "Prašome užpildyti privalomus laukus!", 0).show();
            }
        });
    }

    private List<String> getList(String[] array) {
        List<String> list = new ArrayList();
        for (String item : array) {
            list.add(item);
        }
        return list;
    }

    public void onResult(boolean success, int statusCode, String response) {
        if (success) {
            Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putBoolean("completed2", true);
            editor.apply();
            Toast.makeText(getApplicationContext(), "Dėkui, kad užpildėte apklausą!", 0).show();
            startActivity(new Intent(this, StartActivity.class));
            return;
        }
        Toast.makeText(getApplicationContext(), "Klaida: " + statusCode, 0).show();
    }
}
