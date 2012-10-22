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
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

/**
 * This will test the CalculusGame by using robotium.
 * @author Johan Gustafsson
 * @date 22 okt 2012
 */
public class TestCalculusGame extends
			ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	
	public TestCalculusGame() {
		super("it.chalmers.dat255_bearded_octo_lama", MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testEnableCalculusGame() {
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("Add alarm");
		solo.clickOnText("Settings");
		solo.clickOnCheckBox(2);
		solo.pressSpinnerItem(1, 0);
		solo.goBack();
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}
	
	
	public void testEditTextView() {
		Intent intent = new Intent(getActivity(), NotificationActivity.class);
		
		String gName = "Calculus";
		Extras extra = new Extras.Builder().gameName(gName).gameNotification(true).build();
		intent.putExtra("isTest", true);
		intent.putExtra("extras", extra);
		getActivity().startActivity(intent);
		
		solo.waitForActivity("NotificiationActivity", 1000);
		
		EditText editText = solo.getEditText(0);
		
		solo.enterText(editText, "This should not work");
		assertTrue(editText.getText().toString().isEmpty());
		
		String numbers = "1234567890";
		solo.enterText(editText, numbers);
		assertEquals(numbers, editText.getText().toString());
		
		solo.clearEditText(editText);
		solo.enterText(editText, "200");
		
		solo.clickOnText("Dismiss alarm");
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}
	
	public void testFinishingGame() {
		Intent intent = new Intent(getActivity(), NotificationActivity.class);
		
		String gName = "Calculus";
		Extras extra = new Extras.Builder().gameName(gName).gameNotification(true).build();
		intent.putExtra("isTest", true);
		intent.putExtra("extras", extra);
		getActivity().startActivity(intent);
		
		solo.waitForActivity("NotificiationActivity", 3000);

		solo.enterText(0, "200");
		
		solo.clickOnText("Dismiss alarm");
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}

}
