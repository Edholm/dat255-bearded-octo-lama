/**
 * Copyright (C) 2012 Emil Edholm, Emil Johansson, Johan Andersson, Johan Gustafsson
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
package it.chalmers.dat255_bearded_octo_lama.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Class giving utilities to handle Ringtones on the device.
 * 
 */
public class RingtoneFinder {

	/**	Provides a list of the devices possible ringtones (including alarm and notification sounds)
	 * 
	 * @param currentActivity - Current activity to carry out resource gathering
	 * @return - list of ringtones.
	 */
	public static List<Ringtone> getRingtones(Activity currentActivity){	
		//TODO: Cleaner implementation with activity
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Set<Ringtone> tones = new HashSet<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		double t0 = System.currentTimeMillis();
		while(!c.isAfterLast()){
			//Adding all tones since it's a set doublets doesn't unimportant.
			tones.add(rm.getRingtone(c.getPosition()));				
			c.moveToNext();
		}
		double t1 = (System.currentTimeMillis() - t0)/1000.0;
		Log.d("RingtoneFinder", "Time taken: "+t1);
		return new ArrayList<Ringtone>(tones);
	}

	/**	Provides a list of the devices possible ringtones (including alarm and notification sounds)
	 * 
	 * @param currentActivity - Current activity to carry out resource gathering
	 * @return - list of ringtones.
	 */
	public static List<String> getRingtonesTitle(Activity currentActivity){	
		//TODO: Cleaner implementation with activity
		RingtoneManager rm = new RingtoneManager(currentActivity);

		List<String> titles = new ArrayList<String>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		double t0 = System.currentTimeMillis();
		String title = "";
		while(!c.isAfterLast()){
			title = c.getString(RingtoneManager.TITLE_COLUMN_INDEX);
			if(!titles.contains(title))
				titles.add(title);
			c.moveToNext();
		}
		double t1 = (System.currentTimeMillis() - t0)/1000.0;
		Log.d("RingtoneFinder", "Time taken for StringURIMap: "+t1);
		return titles;
	}


	/**
	 * Takes a ringtone and returns it's URI.
	 * @param currentActivity - Activity to carry out the resource gathering.
	 * @param tone - Ringtone to find URI of.
	 * @return Uri - Uri of ringtone if found, otherwise null.
	 */
	public static Uri findRingtoneUri(Activity currentActivity, Ringtone tone){
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Cursor c = rm.getCursor();
		c.moveToFirst();

		Ringtone current;
		while(!c.isAfterLast()){
			current = rm.getRingtone(c.getPosition());
			Log.d("RingtoneFinder", current.getTitle(currentActivity) + " " + tone.getTitle(currentActivity));
			if(current.getTitle(currentActivity).equals(
					(tone).getTitle(currentActivity))){
				return rm.getRingtoneUri(c.getPosition());
			}
			c.moveToNext();
		}
		return null;
	}

	/**
	 * Takes a ringtone and returns it's URI.
	 * @param currentActivity - Activity to carry out the resource gathering.
	 * @param tone - Ringtone to find ID of.
	 * @return ID - ID of ringtone if found, otherwise -1.
	 */
	public static List<Integer> findRingtoneID(Activity currentActivity, List<String> titles){
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Cursor c = rm.getCursor();
		c.moveToFirst();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		while(!c.isAfterLast()){
			if(titles.contains(c.getString(RingtoneManager.TITLE_COLUMN_INDEX))){
				ids.add(c.getInt(RingtoneManager.ID_COLUMN_INDEX));
			}
			c.moveToNext();
		}
		return ids;
	}

	/**
	 * 
	 * @param currentActivity 
	 * @param IDs - List of IDs to locate songs from.
	 * @return - List of Ringtones 
	 */
	public static List<Ringtone> getRingtonesFromIDs(Activity currentActivity, List<Integer> IDs){
		RingtoneManager rm = new RingtoneManager(currentActivity);
		List<Ringtone> tones = new ArrayList<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		while(!c.isAfterLast()){
			//If ID matches, add to list
			if(IDs.contains(c.getInt(RingtoneManager.ID_COLUMN_INDEX))){
				tones.add(rm.getRingtone(c.getPosition()));
			}
			c.moveToNext();
		}
		return tones;
	}



}
