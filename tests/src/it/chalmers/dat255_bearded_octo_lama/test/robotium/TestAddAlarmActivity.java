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
package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.activities.AddAlarmActivity;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * This will test the AddAlarmActivity by using robotium.
 * @author Emil Johansson
 * @date 22 okt 2012
 */
public class TestAddAlarmActivity extends
		ActivityInstrumentationTestCase2<AddAlarmActivity> {
	private Solo solo;
	
	public TestAddAlarmActivity() {
		super("it.chalmers.dat255_bearded_octo_lama", AddAlarmActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	public void testAddAlarm(){
		solo.assertCurrentActivity("Check on activity", AddAlarmActivity.class);
		solo.sleep(250);		
		solo.clickOnText("1");
		solo.clickOnText("2");
		solo.clickOnText("3");
		solo.clickOnText("4");
		// Check that the device android version is Honeycomb or higher to use ActionBar API.
	    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
	        solo.clickOnView(solo.getView(R.id.menu_add));
	    }
	    else {
			solo.sendKey(Solo.MENU);
			solo.sleep(250);
			solo.clickOnText("Add alarm");
	    }
	}
	public void testSettings(){
		solo.assertCurrentActivity("Check on activity", AddAlarmActivity.class);
		solo.clickOnText("Settings");
		solo.clickOnCheckBox(0);
		solo.sleep(250);
		solo.clickOnCheckBox(1);
		solo.sleep(250);
		solo.clickOnCheckBox(2);
		solo.sleep(250);
		solo.clickOnCheckBox(2);
		solo.sleep(250);
		solo.clickOnCheckBox(1);
		solo.sleep(250);
		solo.clickOnCheckBox(0);
		solo.pressSpinnerItem(0, 3);
		solo.sleep(250);
		solo.pressSpinnerItem(1, 2);
	}
}
