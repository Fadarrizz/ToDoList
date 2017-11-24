package com.example.auke_pc.to_dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Auke-PC on 19-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {

    private static final String DB = null;

    private static final String TABLE_NAME = "todos";
    private static final String COL1 = "_id";
    private static final String COL2 = "title";
    private static final String COL3 = "completed";

    private static TodoDatabase instance;
    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ( " + COL1 + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " INTEGER);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + "," + COL3 + ") " +
                "VALUES ('Bread', 1);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + "," + COL3 + ") " +
                "VALUES ('Eggs', 0);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL2 + "," + COL3 + ") " +
                "VALUES ('Milk', 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(sqLiteDatabase);
    }

    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context.getApplicationContext(), DB, null, 1);
        }
        return instance;
    }

    public Cursor selectAll() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM todos;",
                null, null);
        return cursor;
    }

    public void insert(String title, int completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2, title);
        values.put(COL3, completed);
        db.insert(TABLE_NAME, null, values);
    }

    public void update(long id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL3, status);
        db.update(TABLE_NAME, values, COL1 + "= " + id, null);
    }

    public void delete(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL1 + "= " + id, null);
    }
}
