package com.androidtitan.dailyselfie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amohnacs on 9/28/15.
 */
public class SelfieAdapter extends BaseAdapter {

    List<CameraItem> cameraList = new ArrayList<CameraItem>();
    LayoutInflater inflater;
    Context context;

    DatabaseHelper databaseHelper;

    public SelfieAdapter(Context context1, List<CameraItem> myList) {
        this.context = context1;
        this.cameraList = myList;
        this.inflater = LayoutInflater.from(context);

        databaseHelper = DatabaseHelper.getInstance(context);
    }


    @Override
    public int getCount() {
        return cameraList.size();
    }

    @Override
    public Object getItem(int position) {
        return cameraList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.selfie_adapter_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        final CameraItem cItem = (CameraItem) getItem(position);

        mViewHolder.thumbnail.setImageBitmap(cItem.getThumbnail());
        mViewHolder.thumbnailText.setText(cItem.getDateString());


        /*String selectedImageUri = String.valueOf(ContentUris.parseId(Uri.parse(cItem.getCameraPath())));
        Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), selectedImageUri,MediaStore.Images.Thumbnails.MICRO_KIND,
                null );*/

        mViewHolder.deleter.setTag(position);

        mViewHolder.deleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("adapter", "preDelete: " + v.getTag() + " cItem-> " + cItem.getId());
                databaseHelper.deleteCameraItem(cItem.getId());
                remove((Integer) v.getTag());
            }
        });

        return convertView;
    }

    public void add(CameraItem listItem) {
        cameraList.add(listItem);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        cameraList.remove(position);
        notifyDataSetChanged();
    }

    private class ViewHolder {

        ImageView thumbnail;
        TextView thumbnailText;
        TextView deleter;

        public ViewHolder(View item) {
            thumbnail = (ImageView) item.findViewById(R.id.thumbnail);
            thumbnailText = (TextView) item.findViewById(R.id.thumbnailText);
            deleter = (TextView) item.findViewById(R.id.deleteText);
        }

    }
}

/*
 private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
 */
