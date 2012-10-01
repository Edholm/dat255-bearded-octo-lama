package it.chalmers.dat255_bearded_octo_lama;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

public abstract class AbstractActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initActionBar();
	}
	
	private void initActionBar() {
		// Check that the device android version is Honeycomb or higher to use ActionBar API.
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	        ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	    }
	}
}
