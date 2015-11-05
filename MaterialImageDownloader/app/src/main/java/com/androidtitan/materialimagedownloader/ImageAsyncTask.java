package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Parameters for AsyncTask are:
 *  Params, the type of the parameters sent to the task upon execution.
 *  Progress, the type of the progress units published during the background computation.
 *  Result, the type of the result of the background computation.
 */
public class ImageAsyncTask
      extends AsyncTask<String, Void, Bitmap> {

    protected final String TAG = getClass().getSimpleName();

//    MyGridAdapter gridAdapter = new MyGridAdapter();
//    GridViewFragment gridFrag = new GridViewFragment();


    public Context activity;
    private String ourUrl;
    private Bitmap bitmap;

    public ImageAsyncTask(Context a, String downloadUrl) { //needs constructor

        this.activity = a;
        this.ourUrl = downloadUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Bitmap doInBackground(String... params) {

        //ourUrl = params[0];
        bitmap = null;

        try{

            InputStream input = new java.net.URL(ourUrl).openStream();
            bitmap = BitmapFactory.decodeStream(input);

            if(bitmap != null) {
                Log.e(TAG, "Bitmap found!");
            }
            else{
                Log.e(TAG, "Bitmap not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        //the image is the view that we are using
        //this is on the UI thread so we just need to make a reference to the GridView
        //image.setImageBitmap(result);
        Log.e(TAG, String.valueOf(bitmap));

        ((LandingActivity) activity).updateFragmentGridView(bitmap);

    }
}
