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

import java.util.ArrayList;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NotificationActivity extends AbstractActivity {

	private Notification n;
	private RelativeLayout contentHolder;
	private LinearLayout btnHolder;
	private boolean gameIsActive;
	private AbstractGameView gameView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		int bundledID = getIntent().getExtras().getInt(BaseColumns._ID);
		Alarm alarm = AlarmController.INSTANCE.getAlarm(this, bundledID);
		
		n = NotificationFactory.create(alarm, this);
		Log.d("horvtest", "reachedbal");
		setContentView(R.layout.activity_notification);
        contentHolder = (RelativeLayout) findViewById(R.id.contentHolder);
        btnHolder = (LinearLayout) findViewById(R.id.btnHolder);
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		n.start();
		
		
		if(gameIsActive) {
			gameView.resume();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if(gameIsActive) {
			gameView.pause();
		}
	}

	private void initGame() {
		//Make the holder for dismiss/snooze alarm buttons invisible while the game is running.
		btnHolder.setVisibility(View.GONE);
		contentHolder.addView(gameView);
		
		//Adding all views that build the games UI after the surfaceView has been added.
		//Otherwise the ui views would all get stuck under the surfaceview.
		ArrayList<View> uiList = gameView.getUIComponents();
		if(uiList != null) {
			for(View v : uiList) {
				contentHolder.addView(v);
			}
		}
		gameView.resume();
	} 
}
