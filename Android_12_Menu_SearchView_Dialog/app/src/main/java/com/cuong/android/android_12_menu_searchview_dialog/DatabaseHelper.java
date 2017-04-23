package com.cuong.android.android_12_menu_searchview_dialog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 17/4/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "contact.db";
    private final static int DB_VERSION = 1;

    private final static String COMMAND_CREATE_CONTACT_TABLE =
            "CREATE TABLE "
                    + ContactContract.ContactEntry.TABLE_NAME
                    + "( "
                    + ContactContract.ContactEntry._ID
                    + " INTEGER, "
                    + ContactContract.ContactEntry.COLUMN_NAME
                    + " TEXT, "
                    + ContactContract.ContactEntry.COLUMN_PHONE
                    + " TEXT, "
                    + ContactContract.ContactEntry.COLUMN_ADDRESS
                    + " TEXT"
                    + " );";

    private final static String COMMAND_DELETE_CONTACT_TABLE =
            "DROP TABLE " + ContactContract.ContactEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_CONTACT_TABLE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_CONTACT_TABLE);
    }

    public List<Contact> getAllContacts() {
        List<Contact> result = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor c = database.query(ContactContract.ContactEntry.TABLE_NAME, new String[]{ContactContract.ContactEntry._ID, ContactContract.ContactEntry.COLUMN_NAME, ContactContract.ContactEntry.COLUMN_PHONE, ContactContract.ContactEntry.COLUMN_ADDRESS},
                null,
                null,
                null,
                null,
                null);
        if (c != null && c.moveToFirst()) {
            do {
                result.add(new Contact(c.getInt(c.getColumnIndex(ContactContract.ContactEntry._ID)),
                        c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME)),
                        c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE)),
                        c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_ADDRESS))));
            } while (c.moveToNext());
        }
        database.close();
        return result;
    }

    public boolean insertContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        int newId = getNewId();
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry._ID, contact.getId());
        values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
        values.put(ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone());
        values.put(ContactContract.ContactEntry.COLUMN_ADDRESS, contact.getAddress());

        long result = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);
        db.close();
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean deleteContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        String whereClause = ContactContract.ContactEntry._ID + " = ?";
        String[] whereArgs = {contact.getId()+""};
        int result = database.delete(ContactContract.ContactEntry.TABLE_NAME, whereClause, whereArgs);
        if (result == 0) return false;
        return true;
    }

    public int getNewId() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(" + ContactContract.ContactEntry._ID + ") as MAX FROM "
                + ContactContract.ContactEntry.TABLE_NAME + ";";
        Cursor c = db.rawQuery(sql, null);
        Log.d("MainActivity", "c=" + c + "; moveToFirst=" + c.moveToFirst());
        if (c != null && c.moveToFirst()) {
            int max = c.getInt(c.getColumnIndex("MAX"));
            return max+1;
        }
        return 1;
    }

    public boolean updateContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        String whereClause = ContactContract.ContactEntry._ID + " = ?";
        String[] whereArgs = {contact.getId()+""};
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
        values.put(ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone());
        values.put(ContactContract.ContactEntry.COLUMN_ADDRESS, contact.getAddress());
        int result = database.update(ContactContract.ContactEntry.TABLE_NAME, values, whereClause, whereArgs);
        database.close();
        if (result > 0) return true;
        return false;
    }
}
