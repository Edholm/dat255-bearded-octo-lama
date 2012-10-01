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
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;


/**
 * Represents a chosen alarm time in the format of hh:mm.
 * @author Emil Edholm
 * @date 28 sep 2012
 */
public class Alarm implements Parcelable {
	
	private final int id;
	private final int hour, minute;
	private final long timeInMS;
	private final boolean enabled;
	// More options goes here later...
	
	/**
	 * Create a new Alarm from a content provider.
	 * @param c the cursor object tied to the content provider.
	 */
	public Alarm(Cursor c) {	
		this.id       = c.getInt(c.getColumnIndex(AlarmColumns._ID));
		this.hour     = c.getInt(c.getColumnIndex(AlarmColumns.HOUR));
		this.minute   = c.getInt(c.getColumnIndex(AlarmColumns.MINUTE));
		this.timeInMS = c.getLong(c.getColumnIndex(AlarmColumns.TIME));
		this.enabled  = c.getInt(c.getColumnIndex(AlarmColumns.ENABLED)) == 1;
	}
	
	/**
	 * Create a new alarm from a {@code Parcel}
	 * @param p the parcel that contains all information about the alarm.
	 */
	public Alarm(Parcel p) {
		this.id       = p.readInt();
		this.hour     = p.readInt();
		this.minute   = p.readInt();
		this.timeInMS = p.readLong();
		this.enabled  = p.readInt() == 1;
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
	
	@Override
	public String toString() {
		return "Alarm " + id + " {\n" +
				"\tHour: " + hour +
				"\tMInute: " + minute +
				"\tTime (millisec): " + timeInMS +
				"\tIs enabled: " + enabled + "\n}";
	}

	public int describeContents() { return 0; }

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(hour);
		dest.writeInt(minute);
		dest.writeLong(timeInMS);
		dest.writeInt(enabled ? 1 : 0); // No writeToBoolean :(
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
		
		// Some convenience fields
		public static final String[] ALL_COLUMNS = {HOUR, MINUTE, TIME, ENABLED};
	}
}
