package it.chalmers.dat255_bearded_octo_lama.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;

public class RingtoneFinder {

	/**	Provides a list of the devices possible ringtones (including alarm and notification sounds)
	 * 
	 * @param currentActivity - Current activity to carry out resource gathering
	 * @return - list of ringtones.
	 */
	public static List<Ringtone> getRingtones(Activity currentActivity){	
		//TODO: Cleaner implementation with activity
		RingtoneManager rm = new RingtoneManager(currentActivity);
		List<Ringtone> tones = new ArrayList<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		Ringtone tone;
		while(!c.isAfterLast()){
			tone = rm.getRingtone(c.getPosition());
			//Adding ringtone at current cursor position
			if(!tones.contains(tone)){
				tones.add(rm.getRingtone(c.getPosition()));
			}
			c.moveToNext();
		}
		
		return tones;

	}
}
