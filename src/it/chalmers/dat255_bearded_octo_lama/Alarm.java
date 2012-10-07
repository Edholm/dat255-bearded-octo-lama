/**
 * Copyright (C) 2012 Emil Edholm, Emil Johansson, Johan Andersson, Johan Gustafsson
 * 
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
package it.chalmers.dat255_bearded_octo_lama;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Represents a chosen alarm time in the format of hh:mm.
 * @author Emil Edholm
 * @date 28 sep 2012
 */
public class Alarm {
	
	private final int id;
	private final int hour, minute;
	private final long timeInMS;
	private final boolean enabled;
	private final boolean textNotification;
	private final boolean soundNotification;
	// More options goes here later...
	
	/**
	 * Create a new Alarm from a content provider.
	 * @param c the cursor object tied to the content provider.
	 */
	public Alarm(Cursor c) {	
		this.id       = c.getInt(AlarmColumns.ID_ID);
		this.hour     = c.getInt(AlarmColumns.HOUR_ID);
		this.minute   = c.getInt(AlarmColumns.MINUTE_ID);
		this.timeInMS = c.getLong(AlarmColumns.TIME_ID);
		this.enabled  = c.getInt(AlarmColumns.ENABLED_ID) == 1;
		this.textNotification = c.getInt(AlarmColumns.TEXT_NOTIFICATION_ID) == 1;
		this.soundNotification = c.getInt(AlarmColumns.SOUND_NOTIFICATION_ID) == 1;
	}
	
	/**
	 * @return the alarm id
	 */

	public int getId() {
		return id;
	}

	/**
	 * @return the hour the alarm is set to (Range: 0-24)
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minute the alarm is set to (Range: 0-59).
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @return the time in milliseconds the alarm is set to.
	 */
	public long getTimeInMS() {
		return timeInMS;
	}

	/**
	 * @return whether or not the alarm is enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @return whether or not the alarm has text notification
	 */
	public boolean hasTextNotification() {
		return textNotification;
	}
	
	/**
	 * @return whether or not the alarm has sound notification
	 */
	public boolean hasSoundNotification() {
		return soundNotification;
	}
	
	
	@Override
	public String toString() {
		return "Alarm " + id + " {\n" +
				"\tHour: " + hour +
				"\n\tMInute: " + minute +
				"\n\tTime (millisec): " + timeInMS +
				"\n\tIs enabled: " + enabled + "\n}";
		//TODO update
	}
	
	/** 
	 * This class describes the columns for use with a ContentProvider 
	 * @see http://www.androidcompetencycenter.com/2009/01/basics-of-android-part-iv-android-content-providers/
	 */
	public static class AlarmColumns implements BaseColumns {
		/** The uri that represents an alarm */
		public static final Uri CONTENT_URI = Uri.parse("content://it.chalmers.dat255-bearded-octo-lama/alarm");
		
		// The rest is pretty self explanatory
		public static final String HOUR = "HOUR";
		public static final String MINUTE = "MINUTE";
		public static final String TIME = "TIME_IN_MS";
		public static final String ENABLED = "ENABLED";
		public static final String TEXT_NOTIFICATION = "TEXT_NOTIFICATION";
		public static final String SOUND_NOTIFICATION = "SOUND_NOTIFICATION";
		
		// Some convenience fields. Makes a lot of stuff easier.
		public static final String[] ALL_COLUMNS = {_ID, HOUR, MINUTE, TIME, ENABLED, TEXT_NOTIFICATION, SOUND_NOTIFICATION};
		
		public static final int ID_ID      = 0;
		public static final int HOUR_ID    = 1;
		public static final int MINUTE_ID  = 2;
		public static final int TIME_ID    = 3;
		public static final int ENABLED_ID = 4;
		public static final int TEXT_NOTIFICATION_ID = 5;
		public static final int SOUND_NOTIFICATION_ID = 6;
		
	}
}
