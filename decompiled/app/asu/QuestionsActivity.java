package app.asu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import app.asu.CustomMultiSelectionSpinner.OnItemSelected;
import com.fasterxml.jackson.core.JsonFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionsActivity extends Activity implements OnItemSelected {
    EditText et1;
    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    List<Spinner> sp4;
    CustomMultiSelectionSpinner sp5;
    CustomMultiSelectionSpinner sp6;
    Spinner sp7;
    List<CustomMultiSelectionSpinner> sp8;

    private class sendData extends AsyncTask<String, Void, String> {
        Activity activity;
        private final HttpClient httpClient = new DefaultHttpClient();
        private ProgressDialog pDialog;

        public sendData(Activity activity) {
            this.activity = activity;
        }

        protected void onPreExecute() {
            this.pDialog = new ProgressDialog(this.activity);
            this.pDialog.setCancelable(false);
            this.pDialog.setMessage("Siunčiama...");
            this.pDialog.show();
        }

        protected String doInBackground(String... urls) {
            try {
                JSONObject json = new JSONObject();
                json.put(SettingsActivity.NAME, QuestionsActivity.this.et1.getText().toString());
                json.put("course", QuestionsActivity.this.sp1.getSelectedItemPosition());
                json.put("faculty", QuestionsActivity.this.sp2.getSelectedItemPosition());
                json.put("gender", QuestionsActivity.this.sp3.getSelectedItemPosition());
                JSONArray arr = new JSONArray();
                for (Spinner aSp4 : QuestionsActivity.this.sp4) {
                    arr.put(aSp4.getSelectedItemPosition());
                }
                json.put("abilities", arr.toString());
                json.put("info", QuestionsActivity.this.sp5.getSelectedIndicies());
                json.put("tech", QuestionsActivity.this.sp6.getSelectedIndicies());
                json.put("apps", QuestionsActivity.this.sp7.getSelectedItemPosition());
                JSONArray arr2 = new JSONArray();
                for (CustomMultiSelectionSpinner aSp8 : QuestionsActivity.this.sp8) {
                    arr2.put(aSp8.getSelectedIndicies().toString());
                }
                json.put("purpose", arr2);
                HttpPost httpPost = new HttpPost(urls[0]);
                httpPost.setEntity(new StringEntity(json.toString()));
                InputStream inputStream = this.httpClient.execute(httpPost).getEntity().getContent();
                if (inputStream != null) {
                    Log.i("resp", QuestionsActivity.convertInputStreamToString(inputStream));
                } else {
                    Log.i("resp", "null");
                }
                Log.i(JsonFactory.FORMAT_NAME_JSON, json.toString());
            } catch (ClientProtocolException e) {
                cancel(true);
            } catch (IOException e2) {
                cancel(true);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            if (this.pDialog != null) {
                this.pDialog.cancel();
            }
            Editor editor = PreferenceManager.getDefaultSharedPreferences(QuestionsActivity.this.getApplicationContext()).edit();
            editor.putBoolean("completed", true);
            editor.apply();
            Toast.makeText(QuestionsActivity.this.getApplicationContext(), "Dėkui, kad užpildėte apklausą!", 0).show();
            QuestionsActivity.this.startActivity(new Intent(QuestionsActivity.this, StartActivity.class));
        }
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        this.et1 = (EditText) findViewById(R.id.editText);
        ArrayList<String> opt1 = new ArrayList();
        opt1.add("Pasirinkite kursą...");
        opt1.add("1 kursas");
        opt1.add("2 kursas");
        opt1.add("3 kursas");
        opt1.add("4 kursas");
        this.sp1 = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> ad1 = new ArrayAdapter(this, 17367048, opt1);
        ad1.setDropDownViewResource(17367049);
        this.sp1.setAdapter(ad1);
        ArrayList<String> opt2 = new ArrayList();
        opt2.add("Pasirinkite fakultetą...");
        opt2.add("Cheminės technologijos fakultetas");
        opt2.add("Ekonomikos ir verslo fakultetas");
        opt2.add("Elektros ir elektronikos fakultetas");
        opt2.add("Informatikos fakultetas");
        opt2.add("Matematikos ir gamtos mokslų fakultetas");
        opt2.add("Mechanikos inžinerijos ir dizaino");
        opt2.add("Socialinių, humanitarinių mokslų ir menų fakultetas");
        opt2.add("Statybos ir architektūros fakultetas");
        this.sp2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> ad2 = new ArrayAdapter(this, 17367048, opt2);
        ad2.setDropDownViewResource(17367049);
        this.sp2.setAdapter(ad2);
        ArrayList<String> opt3 = new ArrayList();
        opt3.add("Pasirinkite lytį...");
        opt3.add("Vyras");
        opt3.add("Moteris");
        this.sp3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> ad3 = new ArrayAdapter(this, 17367048, opt3);
        ad3.setDropDownViewResource(17367049);
        this.sp3.setAdapter(ad3);
        ArrayList<String> opt4 = new ArrayList();
        opt4.add("Pasirinkti...");
        opt4.add("1");
        opt4.add("2");
        opt4.add("3");
        opt4.add("4");
        opt4.add("5");
        this.sp4 = new ArrayList();
        this.sp4.add((Spinner) findViewById(R.id.spinner4));
        this.sp4.add((Spinner) findViewById(R.id.spinner5));
        this.sp4.add((Spinner) findViewById(R.id.spinner6));
        this.sp4.add((Spinner) findViewById(R.id.spinner7));
        this.sp4.add((Spinner) findViewById(R.id.spinner8));
        this.sp4.add((Spinner) findViewById(R.id.spinner9));
        this.sp4.add((Spinner) findViewById(R.id.spinner10));
        this.sp4.add((Spinner) findViewById(R.id.spinner11));
        this.sp4.add((Spinner) findViewById(R.id.spinner12));
        this.sp4.add((Spinner) findViewById(R.id.spinner13));
        this.sp4.add((Spinner) findViewById(R.id.spinner14));
        this.sp4.add((Spinner) findViewById(R.id.spinner15));
        this.sp4.add((Spinner) findViewById(R.id.spinner16));
        for (i = 0; i < this.sp4.size(); i++) {
            ArrayAdapter<String> ad4 = new ArrayAdapter(this, 17367048, opt4);
            ad4.setDropDownViewResource(17367049);
            ((Spinner) this.sp4.get(i)).setAdapter(ad4);
        }
        List opt5 = new ArrayList();
        opt5.add("Iš dėstytojų");
        opt5.add("Iš studijų draugų");
        opt5.add("Iš šeimos");
        opt5.add("Ieškau bibliotekoje");
        opt5.add("Ieškau internete");
        this.sp5 = (CustomMultiSelectionSpinner) findViewById(R.id.spinner17);
        this.sp5.setItems(opt5);
        this.sp5.setSelection(new int[0]);
        this.sp5.setCallback(this);
        List opt6 = new ArrayList();
        opt6.add("Bendrauju socialiniuose tinkluose");
        opt6.add("Naudojuosi elektroniniu paštu");
        opt6.add("Naudojuosi internetine telefonija (skypu)");
        opt6.add("Naudojuosi vaizdo įrašų svetainių įrašais");
        opt6.add("Įkeliu įrašus į vaizdo įrašų svetaines");
        opt6.add("Skaitau ir komentuoju tinklaraščius (blog)");
        opt6.add("Rašau tinklaraščius");
        opt6.add("Užsisakau RSS naujienas");
        opt6.add("Užsisakau garso ar vaizdo transliacijų kanalus (podcast)");
        opt6.add("Ieškau informacijos Vikis svetainėse");
        opt6.add("Pildau straipsnius Vikis svetainėje");
        this.sp6 = (CustomMultiSelectionSpinner) findViewById(R.id.spinner18);
        this.sp6.setItems(opt6);
        this.sp6.setSelection(new int[0]);
        this.sp6.setCallback(this);
        ArrayList<String> opt7 = new ArrayList();
        opt7.add("Taip");
        opt7.add("Ne");
        this.sp7 = (Spinner) findViewById(R.id.spinner19);
        ArrayAdapter<String> ad7 = new ArrayAdapter(this, 17367048, opt7);
        ad7.setDropDownViewResource(17367049);
        this.sp7.setAdapter(ad7);
        ArrayList<String> opt8 = new ArrayList();
        opt8.add("Bendravimui");
        opt8.add("Darbui grupėse");
        opt8.add("Tikslingam mokymuisi");
        opt8.add("Informacijos sklaidai");
        opt8.add("Informacijos gavimui");
        opt8.add("Informacijos kūrimui");
        this.sp8 = new ArrayList();
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner20));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner21));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner22));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner23));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner24));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner25));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner26));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner27));
        this.sp8.add((CustomMultiSelectionSpinner) findViewById(R.id.spinner28));
        for (i = 0; i < this.sp8.size(); i++) {
            ArrayAdapter<String> ad8 = new ArrayAdapter(this, 17367048, opt8);
            ((CustomMultiSelectionSpinner) this.sp8.get(i)).setItems((List) opt8);
            ((CustomMultiSelectionSpinner) this.sp8.get(i)).setSelection(new int[0]);
            ((CustomMultiSelectionSpinner) this.sp8.get(i)).setCallback(this);
        }
        ((Button) findViewById(R.id.sendButton)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (QuestionsActivity.this.et1.getText().toString().length() == 0 || QuestionsActivity.this.sp1.getSelectedItemPosition() == 0 || QuestionsActivity.this.sp2.getSelectedItemPosition() == 0 || QuestionsActivity.this.sp3.getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(0)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(1)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(2)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(3)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(4)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(5)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(6)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(7)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(8)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(9)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(10)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(11)).getSelectedItemPosition() == 0 || ((Spinner) QuestionsActivity.this.sp4.get(12)).getSelectedItemPosition() == 0) {
                    Toast.makeText(QuestionsActivity.this.getApplicationContext(), "Prašome užpildyti visą klausimyną!", 0).show();
                    return;
                }
                new sendData(QuestionsActivity.this).execute(new String[]{"http://asu.milasevicius.com/questionnaire2"});
            }
        });
    }

    public void OnItemSelected() {
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while (true) {
            line = bufferedReader.readLine();
            if (line != null) {
                result = result + line;
            } else {
                inputStream.close();
                return result;
            }
        }
    }
}
