package app.asu.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import app.asu.Models.FoodProductModel;
import app.asu.SettingsActivity;

public class FoodProductsDB extends SQLiteOpenHelper {
    public FoodProductsDB(Context context) {
        super(context, "DB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    public void addFoodProduct(FoodProductModel item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SettingsActivity.NAME, item.getName());
        values.put("calories", item.getCalories());
        values.put("protein", item.getProtein());
        values.put("carbohydrates", item.getCarbohydrates());
        values.put("fat", item.getFat());
        db.insert("products", null, values);
        db.close();
    }

    public FoodProductModel getFoodProduct(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE _id=" + id + "", null);
        cursor.moveToFirst();
        db.close();
        return new FoodProductModel(cursor.getInt(0), cursor.getString(1), Double.valueOf(cursor.getDouble(2)), Double.valueOf(cursor.getDouble(3)), Double.valueOf(cursor.getDouble(4)), Double.valueOf(cursor.getDouble(5)));
    }

    public Cursor getCursor(String query) {
        return getWritableDatabase().rawQuery(query, null);
    }
}
