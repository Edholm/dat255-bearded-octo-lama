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
import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.utilities.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * The applications main activity, i.e. the starting screen.
 * @author Johan Gustafsson
 *
 */
public class MainActivity extends Activity {
	private TextView currentTimeView, currentDateView;
	private Button listAlarmsBtn, newAlaramBtn;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        currentTimeView = (TextView) findViewById(R.id.currentTime);
        currentDateView = (TextView) findViewById(R.id.currentDate);
       
        initButtons();
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		setClock();
		updateNextAlarm();
	}
	
	private void updateNextAlarm() {
		TextView tv = (TextView)findViewById(R.id.timeTilAlarmTextView);
		Alarm next = AlarmController.INSTANCE.getNextInQueue(this);
		
		tv.setText(next != null 
					? Time.getTimeLeft(next.getTimeInMS(), false) 
					: "No alarm has been set");
		
	}

	private void setClock() {
		String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
		String currentDateString = DateFormat.getDateInstance().format(new Date());
		
		currentTimeView.setText(currentTimeString);
		currentDateView.setText(currentDateString);
	}
	
	private void initButtons() {
		listAlarmsBtn = (Button) findViewById(R.id.notificationBtn);
		newAlaramBtn = (Button) findViewById(R.id.newAlarmBtn);
		
		listAlarmsBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), ListAlarmsActivity.class));
			}
		});
		
		newAlaramBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), AddAlarmActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
}
