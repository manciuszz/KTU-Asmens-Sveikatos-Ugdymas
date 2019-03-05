package app.asu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import app.asu.Databases.EAdditivesDB;
import app.asu.Databases.FoodProductsDB;
import app.asu.Databases.RecreationZonesDB;
import app.asu.Models.EAdditiveModel;
import app.asu.Models.FoodProductModel;
import app.asu.Models.RecreationZoneModel;
import app.asu.questions_2.Questions2Activity;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class StartActivity extends Activity implements OnClickListener {
    private ProgressDialog dialog;
    boolean[] updates = new boolean[3];

    private class getUpdatesFromServer extends AsyncTask<String, Void, String> {
        private final HttpClient httpClient = new DefaultHttpClient();
        int pos;

        protected String doInBackground(String... strArr) {
            int i = 0;
            try {
                JSONArray jSONArray = new JSONArray((String) this.httpClient.execute(new HttpGet(strArr[0]), new BasicResponseHandler()));
                int i2;
                switch (this.pos) {
                    case 0:
                        EAdditivesDB eAdditivesDB = new EAdditivesDB(StartActivity.this.getApplicationContext());
                        while (i < jSONArray.length()) {
                            eAdditivesDB.addEAdditive(new EAdditiveModel(jSONArray.getJSONObject(i).getString("code"), jSONArray.getJSONObject(i).getInt("type_id"), jSONArray.getJSONObject(i).getString("description")));
                            i++;
                        }
                        eAdditivesDB.close();
                        break;
                    case 1:
                        FoodProductsDB foodProductsDB = new FoodProductsDB(StartActivity.this.getApplicationContext());
                        for (i2 = 0; i2 < jSONArray.length(); i2++) {
                            foodProductsDB.addFoodProduct(new FoodProductModel(jSONArray.getJSONObject(i2).getString("name"), Double.valueOf(jSONArray.getJSONObject(i2).getDouble("calories")), Double.valueOf(jSONArray.getJSONObject(i2).getDouble("protein")), Double.valueOf(jSONArray.getJSONObject(i2).getDouble("carbohydrates")), Double.valueOf(jSONArray.getJSONObject(i2).getDouble("fat"))));
                        }
                        foodProductsDB.close();
                        break;
                    case 2:
                        RecreationZonesDB recreationZonesDB = new RecreationZonesDB(StartActivity.this.getApplicationContext());
                        for (i2 = 0; i2 < jSONArray.length(); i2++) {
                            recreationZonesDB.addRecreationZone(new RecreationZoneModel(jSONArray.getJSONObject(i2).getString("name"), jSONArray.getJSONObject(i2).getInt("type_id"), jSONArray.getJSONObject(i2).getString("website"), jSONArray.getJSONObject(i2).getString("address"), jSONArray.getJSONObject(i2).getJSONArray("track").toString()));
                        }
                        recreationZonesDB.close();
                        break;
                }
            } catch (ClientProtocolException e) {
                cancel(true);
            } catch (IOException e2) {
                cancel(true);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            return null;
        }

        getUpdatesFromServer(int i) {
            this.pos = i;
        }

        protected void onPreExecute() {
            Log.i("debug", "getUpdatesFromServer " + this.pos + ".START");
        }

        protected void onPostExecute(String str) {
            StartActivity.this.updates[this.pos] = false;
            StartActivity.this.dismissLoadingDialog();
            Log.i("debug", "getUpdatesFromServer " + this.pos + ".END");
        }
    }

    private class CheckForUpdates extends AsyncTask<String, Void, String> {
        private final HttpClient httpClient = new DefaultHttpClient();
        private JSONArray tableData = null;

        protected String doInBackground(String... strArr) {
            try {
                this.tableData = new JSONArray((String) this.httpClient.execute(new HttpGet(strArr[0]), new BasicResponseHandler()));
            } catch (ClientProtocolException e) {
                cancel(true);
            } catch (IOException e2) {
                cancel(true);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String str) {
            if (this.tableData != null) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(StartActivity.this.getApplicationContext());
                Editor edit = defaultSharedPreferences.edit();
                for (int i = 0; i < this.tableData.length(); i++) {
                    try {
                        String obj = this.tableData.getJSONObject(i).get("table_name").toString();
                        int i2 = this.tableData.getJSONObject(i).getInt("version");
                        if (obj.equals("e")) {
                            if (defaultSharedPreferences.getInt("db_e_version", 0) != i2) {
                                StartActivity.this.updates[0] = true;
                                edit.putInt("db_e_version", i2);
                                edit.apply();
                            }
                            Log.i("prefs", String.valueOf(defaultSharedPreferences.getInt("db_e_version", 0)) + i2);
                        } else if (obj.equals("products")) {
                            if (defaultSharedPreferences.getInt("db_products_version", 0) != i2) {
                                StartActivity.this.updates[1] = true;
                                edit.putInt("db_products_version", i2);
                                edit.apply();
                            }
                        } else if (obj.equals("places") && defaultSharedPreferences.getInt("db_places_version", 0) != i2) {
                            StartActivity.this.updates[2] = true;
                            edit.putInt("db_places_version", i2);
                            edit.apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (StartActivity.this.updates[0]) {
                    new getUpdatesFromServer(0).execute(new String[]{"http://asu.milasevicius.com/data/e"});
                }
                if (StartActivity.this.updates[1]) {
                    new getUpdatesFromServer(1).execute(new String[]{"http://asu.milasevicius.com/data/products"});
                }
                if (StartActivity.this.updates[2]) {
                    new getUpdatesFromServer(2).execute(new String[]{"http://asu.milasevicius.com/data/places"});
                }
                StartActivity.this.dismissLoadingDialog();
            } else {
                StartActivity.this.dialog.dismiss();
            }
            Log.i("debug", "CheckForUpdates.END");
        }

        private CheckForUpdates() {
        }

        protected void onPreExecute() {
            StartActivity.this.dialog = ProgressDialog.show(StartActivity.this, "", "Kraunasi...", true);
        }
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case 2131361813:
                if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("name", "").length() == 0) {
                    Toast.makeText(getApplicationContext(), "Nor?dami per?i?r?ti tvarkara?t?, ?veskite KTU prisijungimo vard?", 0).show();
                    break;
                } else {
                    intent = new Intent(this, ScheduleActivity.class);
                    break;
                }
            case 2131361944:
                intent = new Intent(this, NutritionActivity.class);
                break;
            case 2131361945:
                if (StaticMethods.isGpsConnecter(this)) {
                    intent = new Intent(this, SportsActivity.class);
                    break;
                }
                break;
            case 2131361946:
                intent = new Intent(this, ErgonomicsActivity.class);
                break;
            case 2131361947:
                intent = new Intent(this, InformationActivity.class);
                break;
            case 2131361948:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903047);
        boolean z = false;
        for (RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            boolean z2;
            if (ErgonomicsService.class.getName().equals(runningServiceInfo.service.getClassName())) {
                z2 = true;
            } else {
                z2 = z;
            }
            z = z2;
        }
        if (!z) {
            getApplicationContext().startService(new Intent(getApplicationContext(), ErgonomicsService.class));
        }
        ((Button) findViewById(2131361813)).setOnClickListener(this);
        ((Button) findViewById(2131361944)).setOnClickListener(this);
        ((Button) findViewById(2131361945)).setOnClickListener(this);
        ((Button) findViewById(2131361946)).setOnClickListener(this);
        ((Button) findViewById(2131361947)).setOnClickListener(this);
        ((Button) findViewById(2131361948)).setOnClickListener(this);
        for (int i = 0; i < this.updates.length; i++) {
            this.updates[i] = false;
        }
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase("DB", 0, null);
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS e (  _id INTEGER PRIMARY KEY AUTOINCREMENT, code STRING, type_id INTEGER, description STRING )");
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS products (  _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, calories DOUBLE, protein DOUBLE, carbohydrates DOUBLE, fat DOUBLE )");
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS nutritionMonitoring (  _id INTEGER PRIMARY KEY AUTOINCREMENT, date STRING, time INTEGER, foodProductID INT )");
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS places (  _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type_id INTEGER, website STRING, address STRING, coordinates STRING )");
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS activities (  _id INTEGER PRIMARY KEY AUTOINCREMENT, type_id INTEGER, start_date LONG, end_date LONG )");
        openOrCreateDatabase.close();

        if (!StaticMethods.isNetworkConnected(this))
        	Toast.makeText(this, "Nepavyko prisijungti prie interneto.", 0).show();
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    private void dismissLoadingDialog() {
        if (!(this.updates[0] || this.updates[1] || this.updates[2])) {
            this.dialog.dismiss();
        }
        Log.i("debug", "dismissLoadingDialog().FIRED");
    }
}
