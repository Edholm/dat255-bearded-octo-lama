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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author Emil Edholm
 * @date 27 sep 2012
 */
public final class AddAlarmActivity extends Activity {
	
	private AlarmChooseType alarmType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_add_alarm);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void onToggle(View view) {
		Toast t = Toast.makeText(getApplicationContext(), "bepa", Toast.LENGTH_SHORT);
		t.show();
		
		String option = (String)view.getTag();
		if(option.equals("in")) {
			alarmType = AlarmChooseType.IN;
		} else if(option.equals("at")) {
			alarmType = AlarmChooseType.AT;
		} else {
			throw new AssertionError("Wrong tag");
		}
	}
    
    private static enum AlarmChooseType {
    	AT, IN;
    }
}
