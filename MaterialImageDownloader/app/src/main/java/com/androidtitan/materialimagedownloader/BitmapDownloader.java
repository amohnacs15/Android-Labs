package com.androidtitan.materialimagedownloader;

import android.graphics.Bitmap;

/**
 * Created by amohnacs on 11/4/15.
 */
public class BitmapDownloader {

    BitmapDownloadCollection collection;

    private Bitmap bitmap;

    public BitmapDownloader(Bitmap bm) {
        this.bitmap = bm;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
