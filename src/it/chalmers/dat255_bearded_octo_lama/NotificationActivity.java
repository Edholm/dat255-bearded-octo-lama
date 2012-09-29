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
