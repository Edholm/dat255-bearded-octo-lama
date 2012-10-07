/**
 * Copyright (C) 2012 Emil Edholm, Emil Johansson, Johan Andersson, Johan Gustafsson
 *
 * This file is part of dat255-bearded-octo-lama
 *
 *  dat255-bearded-octo-lama is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  dat255-bearded-octo-lama is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with dat255-bearded-octo-lama.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.chalmers.dat255_bearded_octo_lama;

import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			
			Alarm alarm = AlarmController.INSTANCE.getAlarm(context,intent.getExtras().getInt(Alarm.AlarmColumns._ID));
			NotificationActivity na = NotificationActivityFactory.create(alarm);

			Intent notificationIntent = new Intent(context, na.getClass());
			
			notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			notificationIntent.putExtras(intent); // Forward all extras.
			
			context.startActivity(notificationIntent);
		}
		catch (Exception e) {
			e.printStackTrace();
		}		 
    }
}
