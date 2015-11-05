package com.androidtitan.materialimagedownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by amohnacs on 11/1/15.
 */
public class MyGridAdapter extends BaseAdapter{
    private String TAG = getClass().getSimpleName();

    private Context context;
    public ArrayList<Bitmap> sweet;

    public MyGridAdapter() {

    }

    public MyGridAdapter(Context c) {
        this.context = c;
        this.sweet = new ArrayList<Bitmap>();
    }

    public MyGridAdapter(Context cont, ArrayList<Bitmap> bitter) {
        this.context = cont;
        this.sweet = bitter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView gridViewItem;

        if(convertView == null) {

            gridViewItem = new ImageView(context);
            gridViewItem.setScaleType(ImageView.ScaleType.CENTER);
            gridViewItem.setPadding(8, 8, 8, 8);

        }
        else {
            gridViewItem = (ImageView) convertView;
        }

        Bitmap tinybitmap = sweet.get(position);
        Log.e(TAG, String.valueOf(tinybitmap));
        tinybitmap = grayScaleFilter(tinybitmap);

        gridViewItem.setImageBitmap(tinybitmap);

        return gridViewItem;
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

    @Override
    public int getCount() {
        return sweet.size();
    }

    @Override
    public Object getItem(int position) {
        return sweet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
