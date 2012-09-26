package it.chalmers.dat255_bearded_octo_lama;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView currentTimeView, currentDateView;
	Button settingsBtn, notificationBtn, newAlaramBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
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
	}
	
	private void setClock() {
		//TODO: Do a cleaner and better version of this.
		String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
		String currentDateString = DateFormat.getDateInstance().format(new Date());
		
		currentTimeView.setText(currentTimeString);
		currentDateView.setText(currentDateString);
	}
	
	private void initButtons() {
		settingsBtn = (Button) findViewById(R.id.settingsBtn);
		notificationBtn = (Button) findViewById(R.id.notificationBtn);
		newAlaramBtn = (Button) findViewById(R.id.newAlarmBtn);
		
		settingsBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), SettingsActivity.class));
			}
		});
		
		notificationBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), NotificationActivity.class));
			}
		});
		
		newAlaramBtn.setOnClickListener(new View.OnClickListener() {
			
			//TODO: Add proper implementation
			//This will cause the alarm to go off in 5 seconds.
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MILLISECOND, 5000);
				
				AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
				PendingIntent pIntent = PendingIntent.getBroadcast(v.getContext(),
		                0, new Intent(v.getContext(), AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
				
				am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
				Toast.makeText(v.getContext(), "Alarm set", Toast.LENGTH_LONG).show();
			}
		});
	}
}
