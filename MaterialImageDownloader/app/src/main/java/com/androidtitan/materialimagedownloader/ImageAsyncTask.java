package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
    private BitmapDownloadCollection collector;
    private AsyncDelegate delegate;
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

        this.delegate = (AsyncDelegate) activity;
        collector = BitmapDownloadCollection.getInstance(activity);

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

                Boolean canUseBitmap = true;

                for(BitmapDownloader bmdl : collector.getBitmapArrayList()) {
                    if(bmdl.toString().equals(bitmap.toString())) {
                        canUseBitmap = false;
                        break;
                    }
                }
                if (canUseBitmap == true) {
                    return grayScaleFilter(bitmap);
                } else {
                    return null;
                }
            }
            else{
                Log.e(TAG, "Bitmap not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        //the image is the view that we are using
        //this is on the UI thread so we just need to make a reference to the GridView
        //image.setImageBitmap(result);
        Log.e(TAG, "onPostExecute() - " + String.valueOf(bitmap));

        if(bitmap != null) {
            delegate.asyncComplete(bitmap);

        }

    }

    /**
     * Apply a grayscale filter to the @a pathToImageFile and return a
     * Uri to the filtered image.
     */
    public static Bitmap grayScaleFilter(Bitmap originalImage) {
//        Bitmap originalImage =
//                decodeImageFromPath(context,
//                        pathToImageFile);

        // Bail out if something is wrong with the image.
        if (originalImage == null)
            return null;

        Bitmap grayScaleImage =
                originalImage.copy(originalImage.getConfig(),
                        true);

        boolean hasTransparent = grayScaleImage.hasAlpha();
        int width = grayScaleImage.getWidth();
        int height = grayScaleImage.getHeight();

        // A common pixel-by-pixel grayscale conversion algorithm
        // using values obtained from en.wikipedia.org/wiki/Grayscale.
        for (int i = 0; i < height; ++i) {
            // Break out if we've been interrupted.
            if (Thread.interrupted())
                return null;

            for (int j = 0; j < width; ++j) {
                // Check if the pixel is transparent in the original
                // by checking if the alpha is 0.
                if (hasTransparent
                        && ((grayScaleImage.getPixel(j, i)
                        & 0xff000000) >> 24) == 0)
                    continue;

                // Convert the pixel to grayscale.
                int pixel = grayScaleImage.getPixel(j, i);
                int grayScale =
                        (int) (Color.red(pixel) * .299
                                + Color.green(pixel) * .587
                                + Color.blue(pixel) * .114);
                grayScaleImage.setPixel(j, i,
                        Color.rgb(grayScale,
                                grayScale,
                                grayScale));
            }
        }

        return grayScaleImage;
    }
}
