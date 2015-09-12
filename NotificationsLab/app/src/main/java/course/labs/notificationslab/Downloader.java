package course.labs.notificationslab;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by amohnacs on 9/12/15.
 */
public class Downloader extends android.app.Application {

    public DownloadFinishedListener finisher = null;

    public static final String TAG = "Downloader";
    public Boolean downloadCompleted;
    private final int MY_NOTIFICATION_ID = 11151990;

    //Context context;

    String[] completedTweets = new String[] {};

    //MainActivity ma = MainActivity.getInstance();

    Context context;

    public Downloader(MainActivity ma) {

         context = ma;
        finisher = (DownloadFinishedListener) context;

    }

    public Downloader(Context alarm) {

        context = alarm;

    }


    public void prepAndRunDownloader() {
        DownloaderTask downloaderAsyncTask = new DownloaderTask();


        // Prepare them for use with DownloaderTask.
        Integer[] crushArray = new Integer[MainActivity.sRawTextFeedIds.size()];
        for (int i = 0; i < MainActivity.sRawTextFeedIds.size(); i++) {
            crushArray[i] = MainActivity.sRawTextFeedIds.get(i);
        }


        downloaderAsyncTask.execute(crushArray);

    }

    public class DownloaderTask extends AsyncTask<Integer, Integer, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(Integer[] crushArray) {
            Log.e(TAG, "downloading...");
            return downloadTweets(crushArray);
        }

        @Override
        protected void onPostExecute(String[] tweets) {

            try {
                finisher.notifyDataRefreshed(tweets);

            } catch(Exception e) {
                Log.e(TAG, "Fire! " + String.valueOf(e));
            }
        }

    }

    private String[] downloadTweets(Integer resourceIDS[]) {

        final int simulatedDelay = 2000;
        String[] feeds = new String[resourceIDS.length];
        downloadCompleted = false;

        try {
            for (int idx = 0; idx < resourceIDS.length; idx++) {
                InputStream inputStream;
                BufferedReader in;
                try {
                    // Pretend downloading takes a long time
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                inputStream = context.getResources().openRawResource(
                        resourceIDS[idx]);
                in = new BufferedReader(new InputStreamReader(inputStream));

                String readLine;
                StringBuffer buf = new StringBuffer();

                while ((readLine = in.readLine()) != null) {
                    buf.append(readLine);
                }

                feeds[idx] = buf.toString();

                if (null != in) {
                    in.close();
                }
            }

            downloadCompleted = true;
            saveTweetsToFile(feeds);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Notify user that downloading has finished
        notify(downloadCompleted);

        return feeds;

    }

    private void notify(final boolean success) {

        final Intent restartMainActivityIntent = new Intent(context,
                MainActivity.class);
        restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Sends an ordered broadcast to determine whether MainActivity is
        // active and in the foreground. Creates a new BroadcastReceiver
        // to receive a result indicating the state of MainActivity

        // The Action for this broadcast Intent is
        // MainActivity.DATA_REFRESHED_ACTION
        // The result, MainActivity.IS_ALIVE, indicates that MainActivity is
        // active and in the foreground.

        context.sendOrderedBroadcast(new Intent(
                        MainActivity.DATA_REFRESHED_ACTION), null,
                new BroadcastReceiver() {

                    final String failMsg = context
                            .getString(R.string.download_failed_string);
                    final String successMsg = context
                            .getString(R.string.download_succes_string);
                    final String notificationSentMsg = context
                            .getString(R.string.notification_sent_string);

                    @Override
                    public void onReceive(Context context, Intent intent) {


                        if (getResultCode() != MainActivity.IS_ALIVE) {

                            //Pending intent

                            PendingIntent pIntent = PendingIntent.getActivity(context, MY_NOTIFICATION_ID,
                                    restartMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                            // Uses R.layout.custom_notification for the
                            // layout of the notification View. The xml
                            // file is in res/layout/custom_notification.xml

                            RemoteViews mContentView = new RemoteViews(
                                    context.getPackageName(),
                                    R.layout.custom_notification);

                            // reflect whether the download completed
                            // successfully

                            if (downloadCompleted) {
                                mContentView.setTextViewText(R.id.text, successMsg);
                            } else {
                                mContentView.setTextViewText(R.id.text, failMsg);
                            }

                            // create the Notification.

                            Notification.Builder notificationBuilder = new Notification.Builder(
                                    context)
                                    .setTicker(notificationSentMsg)
                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setAutoCancel(true)
                                    .setContentIntent(pIntent)
                                    .setContent(mContentView);


                            NotificationManager nm = (NotificationManager) context.getSystemService(
                                    context.NOTIFICATION_SERVICE);
                            nm.notify(MY_NOTIFICATION_ID, notificationBuilder.build());


                            Toast.makeText(context, notificationSentMsg,
                                    Toast.LENGTH_LONG).show();

                        } else { //Main activity is still alive
                            //todo

                            Log.e(TAG, success ? successMsg : failMsg);
                            Toast.makeText(context,
                                    success ? successMsg : failMsg,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, null, 0, null, null);
    }


    private void saveTweetsToFile(String[] result) {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = context.openFileOutput(
                    MainActivity.TWEET_FILENAME, Context.MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(fos)));

            for (String s : result) {
                writer.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


}
