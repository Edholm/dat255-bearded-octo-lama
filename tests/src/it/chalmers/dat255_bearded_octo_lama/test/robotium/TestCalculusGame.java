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
