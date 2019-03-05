package app.asu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;

public class SettingsActivity extends NavigationActivity {
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String KEY_PHYSICAL = "physical";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final int SEX_FEMALE = 1;
    public static final int SEX_MALE = 0;
    public static final String WEIGHT = "weight";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final EditText name = (EditText) findViewById(R.id.editText);
        name.setText(preferences.getString(NAME, ""));
        final EditText height = (EditText) findViewById(R.id.editText2);
        height.setText(Integer.toString(preferences.getInt(HEIGHT, 0)));
        final EditText weight = (EditText) findViewById(R.id.editText3);
        weight.setText(Integer.toString(preferences.getInt(WEIGHT, 0)));
        final EditText age = (EditText) findViewById(R.id.editText4);
        age.setText(Integer.toString(preferences.getInt(AGE, 0)));
        final Spinner physicalActivityPicker = (Spinner) findViewById(R.id.physicalActivityPicker);
        ArrayAdapter<CharSequence> physicalAdapter = ArrayAdapter.createFromResource(this, R.array.physicalActivityEntries, 17367048);
        physicalAdapter.setDropDownViewResource(17367049);
        physicalActivityPicker.setAdapter(physicalAdapter);
        final Spinner sexActivityPicker = (Spinner) findViewById(R.id.sexPicker);
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.sexEntries, 17367048);
        sexAdapter.setDropDownViewResource(17367049);
        sexActivityPicker.setAdapter(sexAdapter);
        ((Button) findViewById(R.id.button)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!name.getText().toString().toLowerCase().equals(preferences.getString(SettingsActivity.NAME, "").toLowerCase())) {
                    new File(SettingsActivity.this.getApplicationContext().getFilesDir().getAbsolutePath() + "/schedule.ics").delete();
                }
                Editor editor = preferences.edit();
                editor.putString(SettingsActivity.NAME, name.getText().toString().toLowerCase());
                editor.putInt(SettingsActivity.HEIGHT, Integer.parseInt(height.getText().toString()));
                editor.putInt(SettingsActivity.WEIGHT, Integer.parseInt(weight.getText().toString()));
                editor.putInt(SettingsActivity.AGE, Integer.parseInt(age.getText().toString()));
                editor.putInt(SettingsActivity.KEY_PHYSICAL, physicalActivityPicker.getSelectedItemPosition());
                editor.putInt(SettingsActivity.SEX, sexActivityPicker.getSelectedItemPosition());
                editor.apply();
                Toast.makeText(SettingsActivity.this.getApplicationContext(), R.string.settingsSubmitText, 0).show();
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, StartActivity.class));
            }
        });
    }
}
