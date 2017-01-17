package net.dust_bowl.togetheragain;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by quaz4 on 17/01/2017.
 */

//TODO Disable alarm

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if(action.equals("android.intent.action.BOOT_COMPLETED"))
        {
            setupAlarm(context);
        }
        else if(action.equals("android.intent.action.ALARM"))
        {
            //TODO Implement network activities
            Toast.makeText(context, "Checking db...", Toast.LENGTH_SHORT).show();
        }
    }

    //Setup alarm to repeat every 2 Min
    public void setupAlarm(Context context)
    {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //TODO Implement alarm listener service (UpdateMonitor)
        Intent intent = new Intent(context, UpdateMonitor.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Set the alarm to start in 2 Min
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 2);

        //Set alarm and then repeat about every 2 Min
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 2, alarmIntent);
    }
}
