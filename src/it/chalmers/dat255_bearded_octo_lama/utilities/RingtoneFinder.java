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

/**
 * Class giving utilities to handle Ringtones on the device.
 * 
 */
public enum RingtoneFinder {;

	/**	
	 * Provides a list of the devices possible ringtones (including alarm and notification sounds).
	 * WARNING - much IO-activity involved and time demanding method.
	 * @param currentActivity - Current activity to carry out resource gathering.
	 * @return - list of ringtones.
	 */
	public static List<Ringtone> getRingtones(Activity currentActivity){	
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Set<Ringtone> tones = new HashSet<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		while(!c.isAfterLast()){
			//Adding all tones since it's a set doublets doesn't matter.
			tones.add(rm.getRingtone(c.getPosition()));				
			c.moveToNext();
		}
		return new ArrayList<Ringtone>(tones);
	}

	/**	
	 * Provides a list of the devices possible ringtones (including alarm and notification sounds).
	 * @param currentActivity - Current activity to carry out resource gathering.
	 * @return - list of ringtones.
	 */
	public static List<String> getRingtonesTitle(Activity currentActivity){	
		RingtoneManager rm = new RingtoneManager(currentActivity);
		List<String> titles = new ArrayList<String>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		String title = "";
		while(!c.isAfterLast()){
			title = c.getString(RingtoneManager.TITLE_COLUMN_INDEX);
			if(!titles.contains(title)){
				titles.add(title);
			}
			c.moveToNext();
			
		}
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
		String targetTitle = tone.getTitle(currentActivity);
		String currentTitle = "";
		while(!c.isAfterLast()){
			currentTitle = c.getString(RingtoneManager.TITLE_COLUMN_INDEX);
			if(currentTitle.equals(targetTitle)){
				//Uses Uri.parse() to make an URI from the string.
				return Uri.parse(c.getString(RingtoneManager.URI_COLUMN_INDEX));
			}
			c.moveToNext();
		}
		return null;
	}

	/**
	 * Takes a list of ringtone titles and returns a list of their IDs
	 * @param currentActivity - Activity to carry out the resource gathering.
	 * @param titles - List of ringtone titles.
	 * @return ids - List of IDs of ringtones if found, otherwise empty list.
	 */
	public static List<Integer> findRingtoneIDs(Activity currentActivity, List<String> titles){
		RingtoneManager rm = new RingtoneManager(currentActivity);
		Cursor c = rm.getCursor();
		c.moveToFirst();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		//To handle if a user input a null list.
		if(titles == null){
			return ids;
		}

		while(!c.isAfterLast()){
			//Check if current cursor row is one of the titles looked after, if so added.
			if(titles.contains(c.getString(RingtoneManager.TITLE_COLUMN_INDEX))){
				ids.add(c.getInt(RingtoneManager.ID_COLUMN_INDEX));
			}
			c.moveToNext();
		}
		return ids;
	}

	/**
	 * 
	 * @param currentActivity.
	 * @param IDs - List of IDs to locate songs from.
	 * @return - List of Ringtones. 
	 */
	public static List<Ringtone> getRingtonesFromID(Activity currentActivity, List<Integer> Ids){
		RingtoneManager rm = new RingtoneManager(currentActivity);
		List<Ringtone> tones = new ArrayList<Ringtone>();
		Cursor c = rm.getCursor();
		c.moveToFirst();
		while(!c.isAfterLast()){
			//If ID matches search list, add to list
			if(Ids.contains(c.getInt(RingtoneManager.ID_COLUMN_INDEX))){
				tones.add(rm.getRingtone(c.getPosition()));
			}
			c.moveToNext();
		}
		return tones;
	}



}
