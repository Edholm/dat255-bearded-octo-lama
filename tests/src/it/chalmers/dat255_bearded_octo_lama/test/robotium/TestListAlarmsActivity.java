package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * This will test the ListAlarmsActivity by using robotium.
 * @author Emil Johansson
 * @date 22 okt 2012
 */
public class TestListAlarmsActivity extends
			ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;

	public TestListAlarmsActivity() {
		super("it.chalmers.dat255_bearded_octo_lama", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}


	public void testAlarmList(){
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("Add alarm");
		solo.clickOnText("1");
		// Check that the device android version is Honeycomb or higher to use ActionBar API.
	    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
	        solo.clickOnView(solo.getView(R.id.menu_add));
	    }
	    else {
			solo.sendKey(Solo.MENU);
			solo.sleep(250);
			solo.clickOnText("Add alarm");
	    }
		solo.clickOnButton("Alarms");
		solo.clickLongOnText("10:00");
		solo.sleep(250);
		solo.clickOnText("Delete");
		solo.goBack();
	}
}