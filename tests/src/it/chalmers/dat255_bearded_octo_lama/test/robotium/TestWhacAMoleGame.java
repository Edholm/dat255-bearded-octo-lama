package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.Alarm.Extras;
import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

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
		solo.pressSpinnerItem(2, 1);
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
