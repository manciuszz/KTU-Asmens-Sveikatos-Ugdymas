package app.asu.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.asu.Models.RecreationZoneModel;
import app.asu.SettingsActivity;
import java.util.ArrayList;
import java.util.List;

public class RecreationZonesDB extends SQLiteOpenHelper {
    public RecreationZonesDB(Context context) {
        super(context, "DB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS places");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS places");
        onCreate(db);
    }

    public void addRecreationZone(RecreationZoneModel item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SettingsActivity.NAME, item.getName());
        values.put("type_id", Integer.valueOf(item.getTypeId()));
        values.put("website", item.getWebsite());
        values.put("address", item.getAddress());
        values.put("coordinates", item.getCoordinatesJSON());
        db.insert("places", null, values);
        db.close();
    }

    public RecreationZoneModel getRecreationZone() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM places", null);
        cursor.moveToFirst();
        db.close();
        Log.i("zona", cursor.getString(cursor.getColumnIndex(SettingsActivity.NAME)));
        Log.i("zona", cursor.getString(cursor.getColumnIndex("coordinates")));
        return new RecreationZoneModel();
    }

    public List<RecreationZoneModel> getRecreationZones() {
        List<RecreationZoneModel> items = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM places", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(new RecreationZoneModel(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    public Cursor getCursor(String query) {
        return getWritableDatabase().rawQuery(query, null);
    }
}
