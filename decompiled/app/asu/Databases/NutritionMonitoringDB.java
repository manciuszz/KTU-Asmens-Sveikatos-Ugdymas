package app.asu.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.asu.Models.NutritionMonitoringModel;
import java.util.ArrayList;
import java.util.List;

public class NutritionMonitoringDB extends SQLiteOpenHelper {
    private final Context context;

    public NutritionMonitoringDB(Context context) {
        super(context, "DB", null, 1);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nutritionMonitoring");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nutritionMonitoring");
        onCreate(db);
    }

    public void addNutritionMonitoringItems(List<NutritionMonitoringModel> items, String date, int time) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("nutritionMonitoring", "date='" + date + "' AND time='" + time + "'", null);
        for (NutritionMonitoringModel item : items) {
            ContentValues values = new ContentValues();
            values.put("date", item.getDate());
            values.put("time", Integer.valueOf(item.getTime()));
            values.put("foodProductID", Integer.valueOf(item.getFoodProductModel().getId()));
            db.insert("nutritionMonitoring", null, values);
        }
        db.close();
    }

    public List<NutritionMonitoringModel> getNutritionMonitoringItems(String date, int time) {
        List<NutritionMonitoringModel> items = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM nutritionMonitoring WHERE date='" + date + "' AND time='" + time + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FoodProductsDB foodProductsDB = new FoodProductsDB(this.context);
            items.add(new NutritionMonitoringModel(cursor.getString(1), cursor.getInt(2), foodProductsDB.getFoodProduct(cursor.getInt(3))));
            foodProductsDB.close();
            cursor.moveToNext();
        }
        db.close();
        return items;
    }

    public Cursor getCursor(String query) {
        return getWritableDatabase().rawQuery(query, null);
    }
}
