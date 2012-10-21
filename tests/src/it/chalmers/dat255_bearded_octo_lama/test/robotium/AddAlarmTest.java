package it.chalmers.dat255_bearded_octo_lama.test.robotium;

import it.chalmers.dat255_bearded_octo_lama.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class AddAlarmTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	
	public AddAlarmTest() {
		super("it.chalmers.dat255_bearded_octo_lama", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testAddAlarm(){
		solo.assertCurrentActivity("Check on activity", MainActivity.class);
		solo.clickOnButton("+");
	}

}
