/**
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

import it.chalmers.dat255_bearded_octo_lama.R.array;
import it.chalmers.dat255_bearded_octo_lama.R.id;
import it.chalmers.dat255_bearded_octo_lama.R.layout;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author Emil Edholm
 * @date 27 sep 2012
 */
public final class AddAlarmActivity extends Activity implements OnItemSelectedListener {
	
	private Button currentTimeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(layout.activity_add_alarm);
		
		Spinner spinner = (Spinner)findViewById(id.time_options_spinner);
		spinner.setOnItemSelectedListener(this);
		
		// Set to the first (hour 0) button.
		selectTimeButton(id.h0);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void selectTimeButton(int id) {
		if(currentTimeButton != null)
			currentTimeButton.setBackgroundColor(getResources().getColor(R.color.white));
		
		currentTimeButton = (Button)findViewById(id);
		currentTimeButton.setBackgroundColor(getResources().getColor(R.color.green));
	}
	
	private void selectNextTimeButton() {
		switch(currentTimeButton.getId()) {
			case R.id.h0: 
				selectTimeButton(id.h1);
				break;
			case R.id.h1:
				selectTimeButton(id.m0);
				break;
			case R.id.m0:
				selectTimeButton(id.m1);
				break;
			default:
				selectTimeButton(id.h0);
				break;
				
		}
	}
	
	private void addAlarm() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
//		cal.set(Calendar.MINUTE, minute);
//		
//		AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
//		PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(),
//                0, new Intent(view.getContext(), AlarmReceiver.class), 0);
//		
//		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
//		
//		Toast.makeText(getActivity(), "Alarm set", Toast.LENGTH_LONG).show();
		
		
		Toast t = Toast.makeText(getApplicationContext(), "Alarm add", Toast.LENGTH_SHORT);
		t.show();
	}
	
	public void onNumpadClick(View view) {
		// Check if allowed number and if so, select next time button
		
		String numClicked = ((Button)view).getText().toString();
		currentTimeButton.setText(numClicked);
		
		selectNextTimeButton();
	}
	
	public void onTimeClick(View view) {
		selectTimeButton(view.getId());
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		
		
		
		String option = String.valueOf(parent.getItemAtPosition(pos));
		TypedArray options = getResources().obtainTypedArray(array.time_options_array);
		//TODO: choose how to calc alarm
		Toast t = Toast.makeText(getApplicationContext(), options.getText(0), Toast.LENGTH_SHORT);
		t.show();
    }
	
	public void onNothingSelected(AdapterView<?> parent) { /* Do nothing  */ }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.menu_add:
	        	addAlarm(); 
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_add_alarm, menu);
	    return true;
	}

	
}
