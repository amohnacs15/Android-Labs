package course.labs.notificationslab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by amohnacs on 9/12/15.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Lab-Notifications", "Alarm call");
        Downloader thed = new Downloader(context);
        thed.prepAndRunDownloader();
        //AlarmInterface alarmInterface = (AlarmInterface) ;

    }
}
