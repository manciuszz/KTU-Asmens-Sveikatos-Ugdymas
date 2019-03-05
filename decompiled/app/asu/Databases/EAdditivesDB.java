package app.asu.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.asu.Models.EAdditiveModel;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.List;

public class EAdditivesDB extends SQLiteOpenHelper {
    public EAdditivesDB(Context context) {
        super(context, "DB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS e");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS e");
        onCreate(db);
    }

    public void addEAdditive(EAdditiveModel item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("code", item.getCode());
        values.put("type_id", Integer.valueOf(item.getType_id()));
        values.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, item.getDescription());
        db.insert("e", null, values);
        db.close();
    }

    public List<EAdditiveModel> getEAdditives() {
        List<EAdditiveModel> items = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM e", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(new EAdditiveModel(cursor.getString(1), cursor.getInt(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    public Cursor getCursor(String query) {
        return getWritableDatabase().rawQuery(query, null);
    }
}
