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
package it.chalmers.dat255_bearded_octo_lama.activities;

import it.chalmers.dat255_bearded_octo_lama.Alarm;
import it.chalmers.dat255_bearded_octo_lama.AlarmController;
import it.chalmers.dat255_bearded_octo_lama.NotificationFactory;
import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.activities.notifications.Notification;
import android.os.Bundle;
import android.util.Log;

public class NotificationActivity extends AbstractActivity implements Notification {

	Notification n;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		Alarm alarm = AlarmController.INSTANCE.getAlarm(getApplicationContext(), savedInstanceState.getInt(Alarm.AlarmColumns._ID));
		n = NotificationFactory.create(alarm, this);
		Log.d("horvtest", "reachedbal");
		setContentView(R.layout.activity_notification);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		n.start();
		
	} 

	@Override
	protected void onPause() {
		super.onPause();
		n.stop();
	}

	public void start() {

	}

	public void stop() {
	}
	
}
