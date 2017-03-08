package com.elis.ltm.todo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.elis.ltm.todo.model.Nota;

import java.util.ArrayList;

/**
 * Created by davide on 04/03/17.
 */

public class Databasehandler extends SQLiteOpenHelper {

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";

    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "notes";

    private static final String TABLE_NOTES = "contacts";
    private static final String KEY_DATE = "expiry_date";
    private static final String KEY_STATUS = "status";


    public Databasehandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_STATUS + " BOOLEAN"
                + ")";
        db.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);
    }

    public ArrayList<Nota> getAllNotes() {
        ArrayList<Nota> notesList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Nota nota = new Nota();
                nota.setId(Integer.parseInt(cursor.getString(0)));
                nota.setTitle(cursor.getString(1));
                nota.setBody(cursor.getString(2));
                nota.setExpiryDate(cursor.getString(3));
                nota.setStatus(cursor.getString(4).equals("1")?true:false);

                notesList.add(nota);
            } while (cursor.moveToNext());
        }
        return notesList;
    }

    public long addNote(Nota note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY, note.getBody());
        values.put(KEY_DATE, note.getExpiryDate());
        values.put(KEY_STATUS, note.isStatus());

        long insert = db.insert(TABLE_NOTES, null, values);
        note.setId((int) insert);
        db.close();
        return insert;
    }

    public int deleteNote(Nota note) {
        SQLiteDatabase db = this.getWritableDatabase();
        int x = db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        return x;
    }

    public int updateNote(Nota note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_BODY, note.getBody());
        values.put(KEY_DATE, note.getExpiryDate());
        values.put(KEY_STATUS, note.isStatus());

        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }
}
