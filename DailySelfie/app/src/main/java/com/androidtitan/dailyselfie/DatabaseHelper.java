package com.androidtitan.dailyselfie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amohnacs on 9/28/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "databasehhelper";

    private static DatabaseHelper instance;

    private static final String DATABASE_NAME="CAMERAITEM_DATABASE";
    private static final int DATABASE_VERSION = 1;

    private static final String CAMERA_TABLE = "cameras";

    private static final String KEY_ID = "_id";
    private static final String KEY_BITMAP_BLOB = "bitmap";
    private static final String KEY_PATH = "path";
    private static final String KEY_DATE = "date";

    private static final String CREATE_CAMERA_TABLE = "CREATE TABLE " + CAMERA_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_BITMAP_BLOB + " BLOB, "
            + KEY_PATH + " TEXT, "
            + KEY_DATE + " TEXT" + ")";

    public static synchronized DatabaseHelper getInstance(Context context) {

        //Singleton Pattern. Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CAMERA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//todo
    }

    public long createCameraItem(CameraItem item) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_BITMAP_BLOB, item.getThumbnailBytes());
        values.put(KEY_PATH, item.getCameraPath());
        values.put(KEY_DATE, item.getDateString());

        long camera_id = database.insert(CAMERA_TABLE, null, values);
        item.setId(camera_id);
        Log.e(TAG, "id set to " + camera_id);

        return camera_id;
    }

    public int updateCameraItem(CameraItem item) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BITMAP_BLOB, item.getThumbnailBytes());
        values.put(KEY_PATH, item.getCameraPath());
        values.put(KEY_DATE, item.getDateString());

        return database.update(CAMERA_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    public long deleteCameraItem(long id) {
        SQLiteDatabase database = getWritableDatabase();

        Log.e(TAG, "delete from " + CAMERA_TABLE + " where " + KEY_ID + " = " + String.valueOf(id));

        return database.delete(CAMERA_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public CameraItem getCameraItem(long id) {
        SQLiteDatabase database = getReadableDatabase();

        String selectionQuery = "SELECT * FROM " + CAMERA_TABLE + " WHERE " + KEY_ID + " = " + id;

        Cursor cursor = database.rawQuery(selectionQuery, null);

        if(cursor != null)
            cursor.moveToFirst();

        CameraItem item = new CameraItem();

        try {
            item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            item.setThumbnailBytes(cursor.getBlob(cursor.getColumnIndex(KEY_BITMAP_BLOB)));
            item.setCameraPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
            item.setDateString(cursor.getString(cursor.getColumnIndex(KEY_DATE)));

        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e));
        }

        return item;
    }

    public List<CameraItem> getAllCameraItems() {
        SQLiteDatabase database = getReadableDatabase();

        ArrayList<CameraItem> itemsArray = new ArrayList<CameraItem>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + CAMERA_TABLE, null);

        if(cursor.moveToFirst()) {
            do {
                CameraItem item = new CameraItem();
        try {
            item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            item.setThumbnailBytes(cursor.getBlob(cursor.getColumnIndex(KEY_BITMAP_BLOB)));
            item.setCameraPath(cursor.getString(cursor.getColumnIndex(KEY_PATH)));
            item.setDateString(cursor.getString(cursor.getColumnIndex(KEY_DATE)));

            itemsArray.add(item);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e));
        }

            } while (cursor.moveToNext());
        }
        return itemsArray;
    }

    public long deleteAllCameraItems() {
        SQLiteDatabase database = getWritableDatabase();

        return database.delete(CAMERA_TABLE, null, null);
    }
}
