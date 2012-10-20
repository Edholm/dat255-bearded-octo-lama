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
import it.chalmers.dat255_bearded_octo_lama.games.AbstractGameView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationActivity extends AbstractActivity {

	private Notification n;
	private LinearLayout dismissAlarmLayout;
	private RelativeLayout mainContentHolder;
	private boolean gameIsActive;
	private AbstractGameView gameView; 
	private TextView currentTimeView, currentDateView;
	private Alarm alarm;
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);

		int bundledID = getIntent().getExtras().getInt(BaseColumns._ID);
		alarm = AlarmController.INSTANCE.getAlarm(this, bundledID);

		n = NotificationFactory.create(alarm, this);
		setContentView(R.layout.activity_notification);

		mainContentHolder = (RelativeLayout) findViewById(R.id.mainContentLayout);
		dismissAlarmLayout = (LinearLayout) findViewById(R.id.dismissAlarmLayout);

		currentTimeView = (TextView)findViewById(R.id.currentTime);
		currentDateView = (TextView)findViewById(R.id.currentDate);

		Button disAlarm = (Button) findViewById(R.id.disAlarmBtn);
		disAlarm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				n.stop();
				finish();
			}
		});
		Button snoozeAlarm = (Button) findViewById(R.id.snoozeBtn);
		snoozeAlarm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				n.stop();
				AlarmController ac = AlarmController.INSTANCE;
				int snoozeInterval = alarm.getExtras().getSnoozeInterval();
				
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.SECOND, snoozeInterval);

				ac.addAlarm(getApplicationContext(), true, cal.getTimeInMillis(), alarm.getExtras());
				Log.d("NotificationActivity", "Snooze activated");
				Log.d("NotificationActivity", "Snooze interval" + snoozeInterval);
				finish();
			}
		});

	} 

	private void setClock() {
		//TODO: Do a cleaner and better version of this.
		String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
		String currentDateString = DateFormat.getDateInstance().format(new Date());

		currentTimeView.setText(currentTimeString);
		currentDateView.setText(currentDateString);
	}

	@Override
	protected void onResume() {
		super.onResume();
		n.start();

		if(gameIsActive) {
			gameView.resume();
		}

		setClock();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if(gameIsActive) {
			gameView.pause();
		}
	}

	public void endGame(AbstractGameView gameView) {
		//This will set the dismiss controls to visible again while removing the views used by the game.
		dismissAlarmLayout.setVisibility(View.VISIBLE);
		mainContentHolder.removeView(gameView);
		if(gameView.getUIComponents() != null) {
			for(View v : gameView.getUIComponents()) {
				mainContentHolder.removeView(v);
			}
		}
	}


}
