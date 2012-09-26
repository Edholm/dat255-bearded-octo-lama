package it.chalmers.dat255_bearded_octo_lama;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends Activity {
	TextView currentTimeView, currentDateView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notification);
		
		currentTimeView = (TextView) findViewById(R.id.currentTime);
        currentDateView = (TextView) findViewById(R.id.currentDate);
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
}
