package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by amohnacs on 11/4/15.
 */
public class BitmapDownloadCollection {

    private static BitmapDownloadCollection instance = null;
    private static ArrayList<BitmapDownloader> bitmapArrayList;

    // Singleton
    //ensures that there is only one instance of our model
    public static BitmapDownloadCollection getInstance(Context context) {
        if (instance == null) {
            instance = new BitmapDownloadCollection();
        }

        return instance;
    }

    public BitmapDownloadCollection() {
        bitmapArrayList = new ArrayList<BitmapDownloader>();

    }

    public BitmapDownloadCollection(ArrayList<BitmapDownloader> bmArray) {
        this.bitmapArrayList = bmArray;
    }

    public ArrayList<BitmapDownloader> getBitmapArrayList() {
        return bitmapArrayList;
    }

    public ArrayList<Bitmap> getAllbitmaps() {
        ArrayList<Bitmap> bitmapper = new ArrayList<>();

        for(BitmapDownloader bm : bitmapArrayList) {
            bitmapper.add(bm.getBitmap());
        }
        return bitmapper;
    }

    public void setBitmapArrayList(ArrayList<BitmapDownloader> bitmapArrayList) {
        this.bitmapArrayList = bitmapArrayList;
    }

    public void add2BitmapArrayList(BitmapDownloader bmer) {
        bitmapArrayList.add(bmer);
    }

    public void wipeTable() {

    }
}
