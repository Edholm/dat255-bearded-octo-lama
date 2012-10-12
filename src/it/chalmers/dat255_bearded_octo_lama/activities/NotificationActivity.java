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

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.games.AbstractGameView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationActivity extends AbstractActivity {
	private TextView currentTimeView, currentDateView;
	private RelativeLayout mainContentLayout;
	private LinearLayout dismissAlarmLayout;
	private boolean gameIsActive;
	private AbstractGameView gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notification);
		
		currentTimeView = (TextView) findViewById(R.id.currentTime);
        currentDateView = (TextView) findViewById(R.id.currentDate);
        mainContentLayout = (RelativeLayout) findViewById(R.id.mainContentLayout);
        dismissAlarmLayout = (LinearLayout) findViewById(R.id.dismissAlarmLayout);
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		setClock();
		
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
		dismissAlarmLayout.setVisibility(View.GONE);
		mainContentLayout.addView(gameView);
		
		//Adding all views that build the games UI after the surfaceView has been added.
		//Otherwise the UI views would all get stuck under the surface view.
		List<View> uiList = gameView.getUIComponents();
		if(uiList != null) {
			for(View v : uiList) {
				mainContentLayout.addView(v);
			}
		}
		gameView.resume();
	}

	private void setClock() {
		//TODO: Do a cleaner and better version of this.
		String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
		String currentDateString = DateFormat.getDateInstance().format(new Date());
		
		currentTimeView.setText(currentTimeString);
		currentDateView.setText(currentDateString);
	}
}
