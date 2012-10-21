package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

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
		solo.pressSpinnerItem(2, 2);
		solo.goBack();
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
	}
	
	public void testLandingRocket() {
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("Add alarm");
		solo.clickOnText("Settings");
		solo.clickOnCheckBox(2);
		solo.pressSpinnerItem(2, 2);
		solo.clickOnText("Add alarm");
		solo.clickOnText("test");
		solo.goBack();
		solo.waitForActivity("NotificiationActivity");
		solo.clickLongOnScreen(200, 600, 2000);
		solo.clickLongOnScreen(200, 600, 5000);
	}
	
	public void testScreenOverlappingLeft() {
		
	}
	
	public void testScreenOverlappingRight() {
		
	}
}
