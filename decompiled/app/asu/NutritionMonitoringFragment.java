package app.asu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import app.asu.Databases.FoodProductsDB;
import app.asu.Databases.NutritionMonitoringDB;
import app.asu.Models.FoodProductModel;
import app.asu.Models.NutritionMonitoringModel;
import com.google.android.gms.cast.TextTrackStyle;
import it.bradipao.lib.descharts.BarChartView;
import it.bradipao.lib.descharts.ChartValue;
import it.bradipao.lib.descharts.ChartValueSerie;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NutritionMonitoringFragment extends Fragment {
    private List<NutritionMonitoringModel> breakfastList;
    private ListView breakfastListView;
    private CustomListViewAdapter breakfastListViewAdapter;
    private Button chooseDateButton;
    private String date = "";
    private List<NutritionMonitoringModel> dinnerList;
    private ListView dinnerListView;
    private CustomListViewAdapter dinnerListViewAdapter;
    TextView kcalTextView;
    private List<NutritionMonitoringModel> lunchList;
    private ListView lunchListView;
    private CustomListViewAdapter lunchListViewAdapter;
    ArrayList<ChartValueSerie> series;
    BarChartView vChart;

    private class CustomListViewAdapter extends BaseAdapter {
        final Context context;
        final List<NutritionMonitoringModel> items;

        CustomListViewAdapter(Context context, List<NutritionMonitoringModel> items) {
            this.context = context;
            this.items = items;
        }

        public int getCount() {
            return this.items.size();
        }

        public NutritionMonitoringModel getItem(int position) {
            return (NutritionMonitoringModel) this.items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.listview_nutrition_monitoring, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.fatTextView)).setText(((NutritionMonitoringModel) this.items.get(position)).getFoodProductModel().getName());
            ImageButton close = (ImageButton) convertView.findViewById(R.id.imageButton);
            close.setTag(Integer.valueOf(position));
            close.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    switch (Integer.parseInt(((ListView) v.getParent().getParent()).getTag().toString())) {
                        case 0:
                            NutritionMonitoringFragment.this.breakfastList.remove(Integer.parseInt(v.getTag().toString()));
                            NutritionMonitoringFragment.this.breakfastListViewAdapter.notifyDataSetChanged();
                            NutritionMonitoringFragment.this.setListViewHeightBasedOnChildren(NutritionMonitoringFragment.this.breakfastListView);
                            NutritionMonitoringFragment.this.setGraphicData(NutritionMonitoringFragment.this.breakfastList, 0);
                            return;
                        case 1:
                            NutritionMonitoringFragment.this.lunchList.remove(Integer.parseInt(v.getTag().toString()));
                            NutritionMonitoringFragment.this.lunchListViewAdapter.notifyDataSetChanged();
                            NutritionMonitoringFragment.this.setListViewHeightBasedOnChildren(NutritionMonitoringFragment.this.lunchListView);
                            NutritionMonitoringFragment.this.setGraphicData(NutritionMonitoringFragment.this.lunchList, 1);
                            return;
                        case 2:
                            NutritionMonitoringFragment.this.dinnerList.remove(Integer.parseInt(v.getTag().toString()));
                            NutritionMonitoringFragment.this.dinnerListViewAdapter.notifyDataSetChanged();
                            NutritionMonitoringFragment.this.setListViewHeightBasedOnChildren(NutritionMonitoringFragment.this.dinnerListView);
                            NutritionMonitoringFragment.this.setGraphicData(NutritionMonitoringFragment.this.dinnerList, 2);
                            return;
                        default:
                            return;
                    }
                }
            });
            return convertView;
        }
    }

    @SuppressLint({"ValidFragment"})
    private class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        private DatePickerFragment() {
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(1), c.get(2), c.get(5));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            NutritionMonitoringFragment.this.date = Integer.toString(year) + "-" + (Integer.toString(month + 1).length() == 1 ? "0" + Integer.toString(month + 1) : Integer.toString(month + 1)) + "-" + (Integer.toString(day).length() == 1 ? "0" + Integer.toString(day) : Integer.toString(day));
            NutritionMonitoringFragment.this.setDate(NutritionMonitoringFragment.this.date);
        }
    }

    @SuppressLint({"ValidFragment"})
    private class FoodProductPickerDialogFragment extends DialogFragment {
        ClientCursorAdapter adapter;
        FoodProductsDB db;
        int time;

        private class ClientCursorAdapter extends ResourceCursorAdapter implements Filterable {
            public ClientCursorAdapter(Context context, Cursor c) {
                super(context, R.layout.listview_food_products, c, 0);
            }

            public void bindView(View view, Context context, Cursor cursor) {
                ((TextView) view.findViewById(R.id.fatTextView)).setText(cursor.getString(cursor.getColumnIndex(SettingsActivity.NAME)));
                ((TextView) view.findViewById(R.id.textView2)).setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("calories"))));
                ((TextView) view.findViewById(R.id.textView4)).setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("protein"))));
                ((TextView) view.findViewById(R.id.textView3)).setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("carbohydrates"))));
                ((TextView) view.findViewById(R.id.textView5)).setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("fat"))));
            }

            public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
                return FoodProductPickerDialogFragment.this.db.getCursor("SELECT  * FROM products WHERE name LIKE '" + constraint + "%'");
            }

            public Object getItem(int position) {
                FoodProductModel model = new FoodProductModel();
                Cursor c = getCursor();
                c.moveToPosition(position);
                model.setId(c.getInt(c.getColumnIndex("_id")));
                model.setName(c.getString(c.getColumnIndex(SettingsActivity.NAME)));
                model.setCalories(Double.valueOf(c.getDouble(c.getColumnIndex("calories"))));
                model.setCarbohydrates(Double.valueOf(c.getDouble(c.getColumnIndex("carbohydrates"))));
                model.setFat(Double.valueOf(c.getDouble(c.getColumnIndex("fat"))));
                model.setProtein(Double.valueOf(c.getDouble(c.getColumnIndex("protein"))));
                return model;
            }
        }

        private FoodProductPickerDialogFragment() {
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.getWindow().requestFeature(1);
            return dialog;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_food_products, container, false);
            this.time = getArguments().getInt("time");
            final ListView listView = (ListView) view.findViewById(R.id.listView);
            this.db = new FoodProductsDB(getActivity().getApplicationContext());
            this.db.getWritableDatabase();
            this.adapter = new ClientCursorAdapter(getActivity().getApplicationContext(), this.db.getCursor("SELECT  * FROM products"));
            listView.setAdapter(this.adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    NutritionMonitoringFragment.this.setData(new NutritionMonitoringModel(NutritionMonitoringFragment.this.date, FoodProductPickerDialogFragment.this.time, (FoodProductModel) listView.getItemAtPosition(position)), FoodProductPickerDialogFragment.this.time);
                    FoodProductPickerDialogFragment.this.getDialog().dismiss();
                }
            });
            final EditText filter = (EditText) view.findViewById(R.id.editText);
            filter.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    FoodProductPickerDialogFragment.this.adapter.getFilter().filter(s);
                }

                public void afterTextChanged(Editable s) {
                }
            });
            ((ImageButton) view.findViewById(R.id.imageButton)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    filter.setText("");
                }
            });
            return view;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_monitoring, container, false);
        this.vChart = (BarChartView) view.findViewById(R.id.chart);
        this.vChart.setBackgroundColor(Color.parseColor("#e0e0e0"));
        this.vChart.setGridWidth(0.0f, 0.0f, 0.0f);
        ChartValueSerie fat = new ChartValueSerie();
        fat.setStyle(Color.parseColor("#dcb9ac"), Color.parseColor("#dcb9ac"), TextTrackStyle.DEFAULT_FONT_SCALE, true);
        fat.addPoint(new ChartValue("Pusryčiai", TextTrackStyle.DEFAULT_FONT_SCALE));
        fat.addPoint(new ChartValue("Pietūs", 0.0f));
        fat.addPoint(new ChartValue("Vakarienė", 0.0f));
        fat.mYmin = 0.0f;
        ChartValueSerie prot = new ChartValueSerie();
        prot.setStyle(Color.parseColor("#addbcb"), Color.parseColor("#addbcb"), TextTrackStyle.DEFAULT_FONT_SCALE, true);
        prot.addPoint(new ChartValue("Pusryčiai", 0.0f));
        prot.addPoint(new ChartValue("Pietūs", 0.0f));
        prot.addPoint(new ChartValue("Vakarienė", 0.0f));
        prot.mYmin = 0.0f;
        ChartValueSerie carb = new ChartValueSerie();
        carb.setStyle(Color.parseColor("#2e415f"), Color.parseColor("#2e415f"), TextTrackStyle.DEFAULT_FONT_SCALE, true);
        carb.addPoint(new ChartValue("Pusryčiai", 0.0f));
        carb.addPoint(new ChartValue("Pietūs", 0.0f));
        carb.addPoint(new ChartValue("Vakarienė", 0.0f));
        carb.mYmin = 0.0f;
        ChartValueSerie kcal = new ChartValueSerie();
        kcal.setStyle(Color.parseColor("#dae026"), Color.parseColor("#dae026"), TextTrackStyle.DEFAULT_FONT_SCALE, true);
        kcal.addPoint(new ChartValue("Pusryčiai", 0.0f));
        kcal.addPoint(new ChartValue("Pietūs", 0.0f));
        kcal.addPoint(new ChartValue("Vakarienė", 0.0f));
        kcal.mYmin = 0.0f;
        this.series = new ArrayList();
        this.series.add(fat);
        this.series.add(prot);
        this.series.add(carb);
        this.breakfastList = new ArrayList();
        this.breakfastListView = (ListView) view.findViewById(R.id.listView);
        this.breakfastListView.setTag(Integer.valueOf(0));
        this.breakfastListViewAdapter = new CustomListViewAdapter(getActivity().getApplicationContext(), this.breakfastList);
        this.breakfastListView.setAdapter(this.breakfastListViewAdapter);
        this.lunchList = new ArrayList();
        this.lunchListView = (ListView) view.findViewById(R.id.listView2);
        this.lunchListView.setTag(Integer.valueOf(1));
        this.lunchListViewAdapter = new CustomListViewAdapter(getActivity().getApplicationContext(), this.lunchList);
        this.lunchListView.setAdapter(this.lunchListViewAdapter);
        this.dinnerList = new ArrayList();
        this.dinnerListView = (ListView) view.findViewById(R.id.listView3);
        this.dinnerListView.setTag(Integer.valueOf(2));
        this.dinnerListViewAdapter = new CustomListViewAdapter(getActivity().getApplicationContext(), this.dinnerList);
        this.dinnerListView.setAdapter(this.dinnerListViewAdapter);
        this.chooseDateButton = (Button) view.findViewById(R.id.button);
        this.chooseDateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DatePickerFragment().show(NutritionMonitoringFragment.this.getFragmentManager(), "datePicker");
            }
        });
        Calendar c = Calendar.getInstance();
        this.date = c.get(1) + "-" + (String.valueOf(c.get(2) + 1).length() == 1 ? "0" : "") + (c.get(2) + 1) + "-" + (String.valueOf(c.get(5)).length() == 1 ? "0" : "") + c.get(5);
        setDate(this.date);
        ((Button) view.findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (NutritionMonitoringFragment.this.date.length() != 0) {
                    NutritionMonitoringDB db = new NutritionMonitoringDB(NutritionMonitoringFragment.this.getActivity().getApplicationContext());
                    db.getWritableDatabase();
                    db.addNutritionMonitoringItems(NutritionMonitoringFragment.this.breakfastList, NutritionMonitoringFragment.this.date, 0);
                    db.addNutritionMonitoringItems(NutritionMonitoringFragment.this.lunchList, NutritionMonitoringFragment.this.date, 1);
                    db.addNutritionMonitoringItems(NutritionMonitoringFragment.this.dinnerList, NutritionMonitoringFragment.this.date, 2);
                    db.close();
                }
                Toast.makeText(NutritionMonitoringFragment.this.getActivity().getApplicationContext(), NutritionMonitoringFragment.this.date.length() != 0 ? "Išsaugota!" : "Nepasirinkta data!", 0).show();
            }
        });
        OnClickListener addToListListener = new OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                switch (v.getId()) {
                    case R.id.imageButton /*2131361957*/:
                        args.putInt("time", 0);
                        break;
                    case R.id.imageButton2 /*2131361964*/:
                        args.putInt("time", 1);
                        break;
                    case R.id.imageButton3 /*2131361965*/:
                        args.putInt("time", 2);
                        break;
                }
                DialogFragment fragment = new FoodProductPickerDialogFragment();
                fragment.setArguments(args);
                fragment.show(NutritionMonitoringFragment.this.getFragmentManager(), "FoodProductPickerDialogFragment");
            }
        };
        ((ImageButton) view.findViewById(R.id.imageButton)).setOnClickListener(addToListListener);
        ((ImageButton) view.findViewById(R.id.imageButton2)).setOnClickListener(addToListListener);
        ((ImageButton) view.findViewById(R.id.imageButton3)).setOnClickListener(addToListListener);
        this.kcalTextView = (TextView) view.findViewById(R.id.kcalTextView);
        ((TextView) view.findViewById(R.id.dailyKcalTextView)).setText(getString(R.string.nutritionMonitoringDailyIntake).replace(":value", String.valueOf(getDailyKcalntake())));
        return view;
    }

    private int getDailyKcalntake() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        int weight = prefs.getInt(SettingsActivity.WEIGHT, 0);
        int height = prefs.getInt(SettingsActivity.HEIGHT, 0);
        int age = prefs.getInt(SettingsActivity.AGE, 0);
        int sex = prefs.getInt(SettingsActivity.SEX, 0);
        if (sex == 0) {
            return (int) (((88.362d + (13.397d * ((double) weight))) + (4.799d * ((double) height))) - (5.677d * ((double) age)));
        }
        if (1 == sex) {
            return (int) (((447.593d + (9.247d * ((double) weight))) + (3.098d * ((double) height))) - (4.33d * ((double) age)));
        }
        return 0;
    }

    private void setDate(String date) {
        this.chooseDateButton.setText("Pasirinkta data: " + date);
        Log.i("date", date);
        NutritionMonitoringDB db = new NutritionMonitoringDB(getActivity().getApplicationContext());
        db.getWritableDatabase();
        setData(db.getNutritionMonitoringItems(date, 0), 0);
        setData(db.getNutritionMonitoringItems(date, 1), 1);
        setData(db.getNutritionMonitoringItems(date, 2), 2);
        db.close();
    }

    private void setData(NutritionMonitoringModel item, int time) {
        switch (time) {
            case 0:
                this.breakfastList.add(item);
                this.breakfastListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.breakfastListView);
                setGraphicData(this.breakfastList, 0);
                return;
            case 1:
                this.lunchList.add(item);
                this.lunchListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.lunchListView);
                setGraphicData(this.lunchList, 1);
                return;
            case 2:
                this.dinnerList.add(item);
                this.dinnerListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.dinnerListView);
                setGraphicData(this.dinnerList, 2);
                return;
            default:
                return;
        }
    }

    private void setData(List<NutritionMonitoringModel> items, int time) {
        switch (time) {
            case 0:
                this.breakfastList.clear();
                this.breakfastList.addAll(items);
                this.breakfastListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.breakfastListView);
                setGraphicData(this.breakfastList, 0);
                return;
            case 1:
                this.lunchList.clear();
                this.lunchList.addAll(items);
                this.lunchListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.lunchListView);
                setGraphicData(this.lunchList, 1);
                return;
            case 2:
                this.dinnerList.clear();
                this.dinnerList.addAll(items);
                this.dinnerListViewAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(this.dinnerListView);
                setGraphicData(this.dinnerList, 2);
                return;
            default:
                return;
        }
    }

    private void setGraphicData(List<NutritionMonitoringModel> items, int time) {
        int i;
        float[] seriesValues = new float[4];
        for (i = 0; i < seriesValues.length; i++) {
            seriesValues[i] = 0.0f;
        }
        for (NutritionMonitoringModel item : items) {
            seriesValues[0] = (float) (((double) seriesValues[0]) + item.getFoodProductModel().getProtein().doubleValue());
            seriesValues[1] = (float) (((double) seriesValues[1]) + item.getFoodProductModel().getFat().doubleValue());
            seriesValues[2] = (float) (((double) seriesValues[2]) + item.getFoodProductModel().getCarbohydrates().doubleValue());
            seriesValues[3] = (float) (((double) seriesValues[3]) + item.getFoodProductModel().getCalories().doubleValue());
        }
        double kcal = 0.0d;
        for (NutritionMonitoringModel item2 : this.breakfastList) {
            kcal += item2.getFoodProductModel().getCalories().doubleValue();
        }
        for (NutritionMonitoringModel item22 : this.lunchList) {
            kcal += item22.getFoodProductModel().getCalories().doubleValue();
        }
        for (NutritionMonitoringModel item222 : this.dinnerList) {
            kcal += item222.getFoodProductModel().getCalories().doubleValue();
        }
        if (this.kcalTextView != null) {
            this.kcalTextView.setText("Kcal.: " + String.format("%.1f", new Object[]{Double.valueOf(kcal)}));
        }
        String seriesName = "";
        switch (time) {
            case 0:
                seriesName = "Pusryčiai";
                break;
            case 1:
                seriesName = "Pietūs";
                break;
            case 2:
                seriesName = "Vakarienė";
                break;
        }
        this.vChart.clearSeries();
        for (i = 0; i < this.series.size(); i++) {
            for (int j = 0; j < ((ChartValueSerie) this.series.get(i)).getPointList().size(); j++) {
                if (((ChartValueSerie) this.series.get(i)).getPoint(j).t.equals(seriesName)) {
                    ((ChartValueSerie) this.series.get(i)).setPoint(new ChartValue(seriesName, seriesValues[i]), j);
                }
            }
            this.vChart.addSerie((ChartValueSerie) this.series.get(i));
        }
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                if (listItem instanceof ViewGroup) {
                    listItem.setLayoutParams(new LayoutParams(-2, -2));
                }
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
            listView.setLayoutParams(params);
        }
    }
}
