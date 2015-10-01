package com.androidtitan.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by amohnacs on 9/26/15.
 */
public class CameraItem {
    private static final String TAG = "CameraItem";

    long id;
    String cameraPath;
    String dateString;
    Bitmap thumbnail;
    byte[] thumbnailBytes;

    public CameraItem() {

    }

    public CameraItem(String path) {
        this.cameraPath = path;
    }

    public CameraItem (String date, Bitmap thumb) {
        this.dateString = date;
        this.thumbnail = thumb;
        setThumbnailwithBytes(thumb);
    }

    public CameraItem(String path, String date) {
        this.cameraPath = path;
        this.dateString = date;
    }

    public CameraItem(String path, String date, Bitmap thumb) {
        this.cameraPath = path;
        this.dateString = date;
        this.thumbnail = thumb;
        setThumbnailwithBytes(thumb);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCameraPath() {
        return cameraPath;
    }

    public void setCameraPath(String cameraPath) {
        this.cameraPath = cameraPath;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Bitmap getThumbnail() {
        byte[] bitmapdata = getThumbnailBytes();

        Bitmap thumbnail = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);

        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public byte[] getThumbnailBytes() {
        return thumbnailBytes;
    }

    public void setThumbnailwithBytes(Bitmap thumbnailStarter) {
        //change our bitmap to a byteArray for database storage
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnailStarter.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        this.thumbnailBytes = byteArray;
    }

    public void setThumbnailBytes (byte[] tinyBytes) {
        this.thumbnailBytes = tinyBytes;
    }
}
