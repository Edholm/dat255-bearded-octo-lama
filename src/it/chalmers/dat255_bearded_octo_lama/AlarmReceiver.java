package it.chalmers.dat255_bearded_octo_lama;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Intent notificationIntent = new Intent(context, NotificationActivity.class);
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(notificationIntent);
		}
		catch (Exception e) {
			e.printStackTrace();
		}		 
    }
}
