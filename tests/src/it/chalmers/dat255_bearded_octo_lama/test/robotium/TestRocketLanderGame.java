package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.Alarm.Extras;
import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * This will test the RocketLanderGame by using robotium.
 * @author Johan Gustafsson
 * @date 22 okt 2012
 */
public class TestRocketLanderGame extends
				ActivityInstrumentationTestCase2<MainActivity>{
	private Solo solo;
	
	public TestRocketLanderGame() {
		super("it.chalmers.dat255_bearded_octo_lama", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testEnableRocketLander() {
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("Add alarm");
		solo.clickOnText("Settings");
		solo.clickOnCheckBox(2);
		solo.pressSpinnerItem(1, 2);
		solo.goBack();
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}
	
	public void testLandingRocket() {
		Intent intent = new Intent(getActivity(), NotificationActivity.class);
		
		String gName = "Rocket Lander"; //RocketLanderGame.class.getAnnotation(Game.class)
		Extras extra = new Extras.Builder().gameName(gName).gameNotification(true).build();
		intent.putExtra("isTest", true);
		intent.putExtra("extras", extra);
		getActivity().startActivity(intent);
		
		solo.waitForActivity("NotificiationActivity", 3000);
		
		for(int i = 0; i <15; i++) {
			solo.clickLongOnScreen(20, 600, 850);
			solo.clickLongOnScreen(450, 600, 850);
		}
		
		solo.clickOnText("Dismiss alarm");
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}

}
