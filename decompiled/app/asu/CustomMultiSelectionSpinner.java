package app.asu;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomMultiSelectionSpinner extends Spinner implements OnMultiChoiceClickListener {
    private String[] _items = null;
    OnItemSelected callback;
    private boolean[] mSelection = null;
    private final ArrayAdapter<String> simple_adapter;

    public interface OnItemSelected {
        void OnItemSelected();
    }

    public void setCallback(OnItemSelected callback) {
        this.callback = callback;
    }

    public CustomMultiSelectionSpinner(Context context) {
        super(context);
        this.simple_adapter = new ArrayAdapter(context, 17367048);
        super.setAdapter(this.simple_adapter);
    }

    public CustomMultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.simple_adapter = new ArrayAdapter(context, 17367048);
        super.setAdapter(this.simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (this.mSelection == null || which >= this.mSelection.length) {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
        this.mSelection[which] = isChecked;
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
        this.callback.OnItemSelected();
    }

    public boolean performClick() {
        Builder builder = new Builder(getContext());
        builder.setMultiChoiceItems(this._items, this.mSelection, this);
        builder.show();
        return true;
    }

    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException("setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        this._items = items;
        this.mSelection = new boolean[this._items.length];
        this.simple_adapter.clear();
        this.simple_adapter.add(this._items[0]);
        Arrays.fill(this.mSelection, false);
    }

    public void setItems(List<String> items) {
        this._items = (String[]) items.toArray(new String[items.size()]);
        this.mSelection = new boolean[this._items.length];
        this.simple_adapter.clear();
        this.simple_adapter.add(this._items[0]);
        Arrays.fill(this.mSelection, false);
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < this._items.length; j++) {
                if (this._items[j].equals(cell)) {
                    this.mSelection[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < this.mSelection.length; i++) {
            this.mSelection[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < this._items.length; j++) {
                if (this._items[j].equals(sel)) {
                    this.mSelection[j] = true;
                }
            }
        }
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i = 0; i < this.mSelection.length; i++) {
            this.mSelection[i] = false;
        }
        if (index < 0 || index >= this.mSelection.length) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds.");
        }
        this.mSelection[index] = true;
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndicies) {
        int i = 0;
        for (int i2 = 0; i2 < this.mSelection.length; i2++) {
            this.mSelection[i2] = false;
        }
        int length = selectedIndicies.length;
        while (i < length) {
            int index = selectedIndicies[i];
            if (index < 0 || index >= this.mSelection.length) {
                throw new IllegalArgumentException("Index " + index + " is out of bounds.");
            }
            this.mSelection[index] = true;
            i++;
        }
        this.simple_adapter.clear();
        this.simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList();
        for (int i = 0; i < this._items.length; i++) {
            if (this.mSelection[i]) {
                selection.add(this._items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList();
        for (int i = 0; i < this._items.length; i++) {
            if (this.mSelection[i]) {
                selection.add(Integer.valueOf(i));
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;
        for (int i = 0; i < this._items.length; i++) {
            if (this.mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(this._items[i]);
            }
        }
        if (sb.length() == 0) {
            sb.append("Pasirinkti...");
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;
        for (int i = 0; i < this._items.length; i++) {
            if (this.mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(this._items[i]);
            }
        }
        return sb.toString();
    }
}
