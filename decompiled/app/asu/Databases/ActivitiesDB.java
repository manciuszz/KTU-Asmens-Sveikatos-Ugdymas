package app.asu.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.asu.Models.ActivityModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivitiesDB extends SQLiteOpenHelper {
    public ActivitiesDB(Context context) {
        super(context, "DB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activities");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS activities");
        onCreate(db);
    }

    public void addActivity(ActivityModel item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type_id", Integer.valueOf(item.getActivityID()));
        values.put("start_date", Long.valueOf(item.getStartDate()));
        values.put("end_date", Long.valueOf(item.getEndDate()));
        db.insert("activities", null, values);
        db.close();
    }

    public ActivityModel getActivity() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM activities", null);
        cursor.moveToFirst();
        db.close();
        return new ActivityModel(cursor.getInt(0), cursor.getInt(1), cursor.getLong(2), cursor.getLong(3));
    }

    public long getOldestActivity() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT min(start_date) FROM activities", null);
        cursor.moveToFirst();
        db.close();
        return cursor.getLong(0);
    }

    public List<ActivityModel> getActitivies(long date_start, long date_end) {
        List<ActivityModel> items = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM activities  WHERE start_date >='" + date_start + "' AND  end_date <='" + date_end + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(new ActivityModel(cursor.getInt(1), cursor.getLong(2), cursor.getLong(3)));
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    public JSONArray getActitiviesJSON(long date_start) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM activities  WHERE start_date >='" + date_start + "'", null);
        JSONArray jsonArray = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", cursor.getInt(1));
                obj.put("start_date", cursor.getLong(2));
                obj.put("end_date", cursor.getLong(3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(obj);
            cursor.moveToNext();
        }
        db.close();
        return jsonArray;
    }

    public Cursor getCursor(String query) {
        return getWritableDatabase().rawQuery(query, null);
    }
}
