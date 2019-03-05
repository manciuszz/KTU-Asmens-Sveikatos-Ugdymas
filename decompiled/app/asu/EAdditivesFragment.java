package app.asu;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import app.asu.Databases.EAdditivesDB;
import com.google.android.gms.plus.PlusShare;

public class EAdditivesFragment extends Fragment {
    private ClientCursorAdapter adapter;
    private EAdditivesDB db;

    private class ClientCursorAdapter extends ResourceCursorAdapter implements Filterable {
        public ClientCursorAdapter(Context context, Cursor c) {
            super(context, R.layout.listview_e_additives, c, 0);
        }

        public void bindView(View view, Context context, Cursor cursor) {
            ((TextView) view.findViewById(R.id.fatTextView)).setText("E" + cursor.getInt(cursor.getColumnIndex("code")));
            String eType = "";
            Drawable eIcon = null;
            switch (cursor.getInt(cursor.getColumnIndex("type_id"))) {
                case 1:
                    eType = "Galimas";
                    eIcon = EAdditivesFragment.this.getResources().getDrawable(R.drawable.ic_leistinas);
                    break;
                case 2:
                    eType = "Vengtinas";
                    eIcon = EAdditivesFragment.this.getResources().getDrawable(R.drawable.ic_vengtinas);
                    break;
                case 3:
                    eType = "Pavojingas";
                    eIcon = EAdditivesFragment.this.getResources().getDrawable(R.drawable.ic_draudziamas);
                    break;
            }
            ((TextView) view.findViewById(R.id.textView2)).setText(eType);
            ((ImageView) view.findViewById(R.id.imageView)).setImageDrawable(eIcon);
            ((TextView) view.findViewById(R.id.textView3)).setText(cursor.getString(cursor.getColumnIndex(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION)));
        }

        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            return EAdditivesFragment.this.db.getCursor("SELECT  * FROM e WHERE code LIKE '" + constraint + "%'");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e_additives, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        this.db = new EAdditivesDB(getActivity().getApplicationContext());
        this.adapter = new ClientCursorAdapter(getActivity().getApplicationContext(), this.db.getCursor("SELECT  * FROM e"));
        listView.setAdapter(this.adapter);
        final EditText filter = (EditText) view.findViewById(R.id.editText);
        filter.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getKeyCode() != 66) {
                    return false;
                }
                ((InputMethodManager) EAdditivesFragment.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(v.getApplicationWindowToken(), 2);
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
                EAdditivesFragment.this.adapter.getFilter().filter(s);
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
