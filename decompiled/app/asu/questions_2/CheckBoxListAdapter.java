package app.asu.questions_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import app.asu.R;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxListAdapter extends BaseAdapter {
    private List<CheckBoxItem> checkBoxItems = new ArrayList();
    private LayoutInflater inflater;

    private class CheckBoxItem {
        private String entree;
        private boolean isChecked = false;

        public boolean isChecked() {
            return this.isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public String getEntree() {
            return this.entree;
        }

        public void setEntree(String entree) {
            this.entree = entree;
        }
    }

    class ViewHolder {
        CheckBox checkBox;
        View mainLayout;

        ViewHolder() {
        }
    }

    public CheckBoxListAdapter(Activity activity) {
        this.inflater = LayoutInflater.from(activity);
    }

    public int getCount() {
        return this.checkBoxItems.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.list_item_radio, null);
            holder.mainLayout = view.findViewById(R.id.mainLayout);
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mainLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((CheckBoxItem) CheckBoxListAdapter.this.checkBoxItems.get(i)).setIsChecked(b);
            }
        });
        holder.checkBox.setText(((CheckBoxItem) this.checkBoxItems.get(i)).getEntree());
        return view;
    }

    public void setItems(List<String> items) {
        for (String item : items) {
            CheckBoxItem checkBoxItem = new CheckBoxItem();
            checkBoxItem.setEntree(item);
            this.checkBoxItems.add(checkBoxItem);
        }
    }

    public List<Boolean> getCheckedList() {
        List<Boolean> checkedList = new ArrayList();
        for (CheckBoxItem item : this.checkBoxItems) {
            checkedList.add(Boolean.valueOf(item.isChecked()));
        }
        return checkedList;
    }
}
