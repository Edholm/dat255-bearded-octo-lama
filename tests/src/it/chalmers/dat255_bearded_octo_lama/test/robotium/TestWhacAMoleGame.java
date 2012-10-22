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

import it.chalmers.dat255_bearded_octo_lama.Alarm.Extras;
import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * This will test the WhacAMoleGame by using robotium.
 * @author Johan Gustafsson
 * @date 22 okt 2012
 */
public class TestWhacAMoleGame extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	
	public TestWhacAMoleGame() {
		super("it.chalmers.dat255_bearded_octo_lama", MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testEnableWhacAMole() {
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("Add alarm");
		solo.clickOnText("Settings");
		solo.clickOnCheckBox(2);
		solo.pressSpinnerItem(1, 1);
		solo.goBack();
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}
	
	public void testAllButtons() {
		Intent intent = new Intent(getActivity(), NotificationActivity.class);
		
		String gName = "WhacAMole";
		Extras extra = new Extras.Builder().gameName(gName).gameNotification(true).build();
		intent.putExtra("isTest", true);
		intent.putExtra("extras", extra);
		getActivity().startActivity(intent);
		
		solo.waitForActivity("NotificiationActivity", 3000);
		
		for(int i = 8; i >= 0; i--) {
			solo.clickOnButton(i);
		}
		solo.assertCurrentActivity("Check on activity", NotificationActivity.class);
		solo.goBack();
	}
	
	public void testFinishingGame() {
		Intent intent = new Intent(getActivity(), NotificationActivity.class);
		
		String gName = "WhacAMole";
		Extras extra = new Extras.Builder().gameName(gName).gameNotification(true).build();
		intent.putExtra("isTest", true);
		intent.putExtra("extras", extra);
		getActivity().startActivity(intent);
		
		solo.waitForActivity("NotificiationActivity", 3000);
		
		solo.clickOnButton(0);
		solo.clickOnButton(1);
		solo.clickOnButton(2);
		
		solo.clickOnText("Dismiss alarm");
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}

}
