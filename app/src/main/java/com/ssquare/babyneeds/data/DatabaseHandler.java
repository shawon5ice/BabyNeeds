package com.ssquare.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ssquare.babyneeds.models.BabyItem;
import com.ssquare.babyneeds.utils.Constants;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;
    static List<BabyItem>babyItemList = new ArrayList<>();

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY, "+Constants.KEY_GROCERY_ITEM+" TEXT,"
                +Constants.KEY_COLOR+" TEXT, "+Constants.KEY_QTY_NUMBER+" TEXT, "+Constants.KEY_SIZE+" NUMBER, "+Constants.KEY_DATE_NAME+" LONG);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //CRUD METHODS

    public long addItem(BabyItem item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getItemColor());
        values.put(Constants.KEY_QTY_NUMBER,item.getItemQuantity());
        values.put(Constants.KEY_SIZE,item.getItemSize());
        values.put(Constants.KEY_DATE_NAME,java.lang.System.currentTimeMillis());
        long rowId = database.insert(Constants.TABLE_NAME,null,values);
        database.close();
        return rowId;
    }

    public BabyItem getItem(int id){
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(Constants.TABLE_NAME,new String[]{
                Constants.KEY_GROCERY_ITEM,
                Constants.KEY_COLOR,
                Constants.KEY_QTY_NUMBER,
                Constants.KEY_SIZE,
                Constants.KEY_DATE_NAME},Constants.KEY_ID+ "=?"+String.valueOf(id),new String[]{String.valueOf(id)},null,null,null);
        BabyItem item = new BabyItem();
        if(cursor!=null){
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
            item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
            item.setItemSize(cursor.getString(cursor.getColumnIndex(Constants.KEY_SIZE)));
            item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

            //date formating
            DateFormat dateFormat = DateFormat.getDateInstance();
            String format = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            item.setItemAddedDate(format);
        }
        return item;
    }

    public List<BabyItem> getALlBabyItem() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(Constants.TABLE_NAME, new String[]{
                Constants.KEY_ID,
                Constants.KEY_GROCERY_ITEM,
                Constants.KEY_COLOR,
                Constants.KEY_QTY_NUMBER,
                Constants.KEY_SIZE,
                Constants.KEY_DATE_NAME}, null, null, null, null, Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                BabyItem item = new BabyItem();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemSize(cursor.getString(cursor.getColumnIndex(Constants.KEY_SIZE)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String format = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                item.setItemAddedDate(format);

                babyItemList.add(item);

            } while (cursor.moveToNext());
        }
        return babyItemList;
    }

    public int updateItem(BabyItem item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getItemColor());
        values.put(Constants.KEY_QTY_NUMBER,item.getItemQuantity());
        values.put(Constants.KEY_SIZE,item.getItemSize());

        return database.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        database.close();
    }

    public int getItemCount(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+Constants.TABLE_NAME,null);
        return cursor.getCount();
    }
}
