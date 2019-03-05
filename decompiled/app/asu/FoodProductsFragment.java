package app.asu;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.asu.Databases.FoodProductsDB;

public class FoodProductsFragment extends Fragment {
    private ClientCursorAdapter adapter;
    private FoodProductsDB db;

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
            return FoodProductsFragment.this.db.getCursor("SELECT  * FROM products WHERE name LIKE '" + constraint + "%'");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_products, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        this.db = new FoodProductsDB(getActivity().getApplicationContext());
        this.adapter = new ClientCursorAdapter(getActivity().getApplicationContext(), this.db.getCursor("SELECT  * FROM products"));
        listView.setAdapter(this.adapter);
        final EditText filter = (EditText) view.findViewById(R.id.editText);
        filter.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getKeyCode() != 66) {
                    return false;
                }
                ((InputMethodManager) FoodProductsFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(v.getApplicationWindowToken(), 2);
                userValidateEntry();
                return true;
            }

            private void userValidateEntry() {
                System.out.println("user validate entry!");
            }
        });
        filter.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FoodProductsFragment.this.adapter.getFilter().filter(s);
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
