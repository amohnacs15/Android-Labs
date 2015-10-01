package com.androidtitan.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static final int REQUEST_IMAGE_CAP = 1;
    static final String URI_EXTRA = "extra_uri";

    DatabaseHelper databaseHelper;

    ImageView mImageView;
    ListView selfieList;
    SelfieAdapter adapter;

    String cameraSavePath;
    String timeStamp;
    String prettyTime;

    boolean largeImageDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmCreation();

        databaseHelper = DatabaseHelper.getInstance(MainActivity.this);

        mImageView = (ImageView) findViewById(R.id.expandedViewItem);
        selfieList = (ListView) findViewById(R.id.selfieList);
        adapter = new SelfieAdapter(MainActivity.this, databaseHelper.getAllCameraItems());

        selfieList.setAdapter(adapter);

        selfieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!largeImageDisplayed) {
                    newSetPic(databaseHelper.getAllCameraItems().get(position).getThumbnail());
                }
            }
        });
    }

        @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
        selfieList.invalidateViews();
        }



    public void takePicturesIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "An error occured while creating the file...");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        //Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAP);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(largeImageDisplayed) {
            mImageView.setImageBitmap(null);
        }

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("MMMMM dd, yyyy", Locale.US);
        prettyTime = postFormater.format(date);

        prettyTime = DateFormat.getDateTimeInstance().format(new Date());

        if (requestCode == REQUEST_IMAGE_CAP && resultCode == RESULT_OK) {

            //getting what we need from the Camera Activity Result
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //create our CameraItem
            CameraItem createdCamera = new CameraItem(cameraSavePath, prettyTime, imageBitmap);
            databaseHelper.createCameraItem(createdCamera);

            adapter.add(createdCamera);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {

            Log.i(TAG, "Get ready to say cheese!");

            if(largeImageDisplayed) {
                mImageView.setImageDrawable(null);
                largeImageDisplayed = false;
            }

            takePicturesIntent();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(largeImageDisplayed) {
            mImageView.setImageDrawable(null);
            largeImageDisplayed = false;
        }
    }

    private File createImageFile() throws IOException {
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Create an image file name
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        cameraSavePath = "file:" + image.getAbsolutePath();
        Log.e(TAG, cameraSavePath);

        return image;
    }

    public void newSetPic(Bitmap bm) {

        largeImageDisplayed = true;

        int width = bm.getWidth();
        int height = bm.getHeight();

        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        float scaleWidth = ((float) targetW) / width;
        float scaleHeight = ((float) targetH) / height;

        // create a matrix for the manipulation

        Matrix matrix = new Matrix();

        // resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        mImageView.setImageBitmap(resizedBitmap);

    }

    public void alarmCreation() {

        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(),
                120000, alarmPendingIntent);

        Log.e(TAG, "Alarm Scheduled...");
    }
}
