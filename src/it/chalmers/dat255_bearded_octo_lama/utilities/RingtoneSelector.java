package it.chalmers.dat255_bearded_octo_lama.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;

public class RingtoneSelector {

	/**	Provides a list of names of the devices possible ringtones (including alarm and notification sounds)
	 * 
	 * @param currentActivity - Current activity to carry out resource gathering
	 * @return - list of ringtones.
	 */
	public static List<String> getRingtoneNames(Activity currentActivity){	
		//TODO: cleaner implementation with activity
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Cursor c = rm.getCursor();
		List<String> toneNames = new ArrayList<String>();
		c.moveToFirst();
		String title = "";
		
		//Looping through the rows of cursor for ringtones and adding titles
		while(!c.isAfterLast()){
			title = c.getString(RingtoneManager.TITLE_COLUMN_INDEX);
			if(!toneNames.contains(title)){
				toneNames.add(title);
			}
			c.moveToNext();
		}	
		
		Collections.sort(toneNames);
		return toneNames;

	}
	
	/**
	 * 
	 * @param names - Title of songs to get.
	 * @param currentActivity -  Current activity to carry out resource gathering
	 * @return List of ringtone objects (if found) corresponding the list titles input.
	 */
	public static List<Ringtone> getRingtonesWithNames(List<String> names, Activity currentActivity){
		//TODO: Cleaner implementation with activity
		RingtoneManager rm = new RingtoneManager(currentActivity);
		List<Ringtone> tones = new ArrayList<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		String title = "";
		while(!c.isAfterLast()){
			title = c.getString(RingtoneManager.TITLE_COLUMN_INDEX);
			if(names.contains(title)){
				//Adding ringtone at current cursor position
				tones.add(rm.getRingtone(c.getPosition()));
			}
			c.moveToNext();
		}
		
		return tones;
	}

}
