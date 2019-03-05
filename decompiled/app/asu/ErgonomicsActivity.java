package app.asu;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import app.asu.Databases.ActivitiesDB;
import app.asu.Models.ActivityModel;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.cast.TextTrackStyle;
import it.bradipao.lib.descharts.BarChartView;
import it.bradipao.lib.descharts.ChartValue;
import it.bradipao.lib.descharts.ChartValueSerie;
import java.util.ArrayList;
import java.util.Calendar;

public class ErgonomicsActivity extends NavigationActivity {
    private static final String TAG = ErgonomicsActivity.class.getSimpleName();
    public OnDateSetListener dateSetListener = new OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            ErgonomicsActivity.this.selectedDate.set(i, i2, i3);
            ErgonomicsActivity.this.setChart();
            ErgonomicsActivity.this.setDateTextView(ErgonomicsActivity.this.selectedDate);
        }
    };
    ActivitiesDB db;
    Calendar selectedDate;
    ArrayList<ChartValueSerie> series;
    BarChartView vChart;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergonomics);
        this.db = new ActivitiesDB(getApplicationContext());
        this.db.getReadableDatabase();
        long oldestPossibleDate = this.db.getOldestActivity();
        this.selectedDate = Calendar.getInstance();
        this.selectedDate.setFirstDayOfWeek(2);
        this.selectedDate.set(11, 0);
        this.selectedDate.set(12, 0);
        this.selectedDate.set(13, 0);
        this.selectedDate.set(14, 0);
        this.vChart = (BarChartView) findViewById(R.id.chart);
        this.vChart.setBackgroundColor(Color.parseColor("#e0e0e0"));
        this.vChart.setGridWidth(0.0f, 0.0f, 0.0f);
        this.vChart.setYgrid(false, 0.0f, 24.0f, 24);
        if (oldestPossibleDate != 0) {
            setChart();
            setDateTextView(this.selectedDate);
            setupButton(oldestPossibleDate);
            return;
        }
        TextView noRecordsTextView = (TextView) findViewById(R.id.noDataRecordTextView);
        noRecordsTextView.setVisibility(0);
        noRecordsTextView.setText(R.string.noActivityRecords);
        ((TextView) findViewById(R.id.dateTextView)).setVisibility(8);
        this.vChart.setVisibility(8);
        ((Button) findViewById(R.id.button)).setVisibility(8);
    }

    private void setupButton(final long oldestPossibleDate) {
        ((Button) findViewById(R.id.button)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ErgonomicsActivity.this, ErgonomicsActivity.this.dateSetListener, ErgonomicsActivity.this.selectedDate.get(1), ErgonomicsActivity.this.selectedDate.get(2), ErgonomicsActivity.this.selectedDate.get(5));
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(11, maxDate.getMaximum(11));
                maxDate.set(12, maxDate.getMaximum(12));
                maxDate.set(13, maxDate.getMaximum(13));
                maxDate.set(14, maxDate.getMaximum(14));
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                Calendar minDate = Calendar.getInstance();
                minDate.setTimeInMillis(oldestPossibleDate);
                minDate.set(11, minDate.getMinimum(11));
                minDate.set(12, minDate.getMinimum(12));
                minDate.set(13, minDate.getMinimum(13));
                minDate.set(14, minDate.getMinimum(14));
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    private void setChart() {
        Calendar start_date = this.selectedDate;
        Calendar end_date = Calendar.getInstance();
        end_date.setTimeInMillis(start_date.getTimeInMillis());
        end_date.add(5, 1);
        long[] times = new long[]{0, 0, 0, 0};
        for (ActivityModel activity : this.db.getActitivies(start_date.getTimeInMillis(), end_date.getTimeInMillis())) {
            switch (activity.getActivityID()) {
                case 1:
                    times[3] = times[3] + (activity.getEndDate() - activity.getStartDate());
                    break;
                case 3:
                    times[0] = times[0] + (activity.getEndDate() - activity.getStartDate());
                    break;
                case 7:
                    times[1] = times[1] + (activity.getEndDate() - activity.getStartDate());
                    break;
                case 8:
                    times[2] = times[2] + (activity.getEndDate() - activity.getStartDate());
                    break;
                default:
                    break;
            }
        }
        Log.d("Tracking", "F: " + this.selectedDate.getTime() + ", T: " + end_date.getTime() + " t0: " + times[0] + " t1: " + times[1] + " t2: " + times[2] + " t3: " + times[3]);
        this.db.close();
        ChartValueSerie passive = new ChartValueSerie();
        passive.setStyle(Color.parseColor("#addbcb"), Color.parseColor("#addbcb"), TextTrackStyle.DEFAULT_FONT_SCALE, true);
        passive.addPoint(new ChartValue("Pasyvus", (float) ((times[0] / 1000) / 3600)));
        passive.addPoint(new ChartValue("Ėjimas", (float) ((times[1] / 1000) / 3600)));
        passive.addPoint(new ChartValue("Bėgimas", (float) ((times[2] / 1000) / 3600)));
        passive.addPoint(new ChartValue("Važiavimas dviračiu", (float) ((times[3] / 1000) / 3600)));
        this.vChart.clearSeries();
        this.vChart.addSerie(passive);
    }

    private void setDateTextView(Calendar date) {
        int year = date.get(1);
        int month = date.get(2);
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateTextView.setText(year + " m. " + new String[]{"Sausio", "Vasario", "Kovo", "Balandžio", "Gegužės", "Birželio", "Liepos", "Rugpjūtčio", "Rugsėjo", "Spalio", "Lapkritčio", "Gruodio"}[month] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + date.get(5) + " d.");
    }
}
