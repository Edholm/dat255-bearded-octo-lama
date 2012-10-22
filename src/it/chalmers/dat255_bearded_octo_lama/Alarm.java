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

import it.chalmers.dat255_bearded_octo_lama.utilities.Days;
import it.chalmers.dat255_bearded_octo_lama.utilities.Tuple;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

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
	private final Extras extras;

	/**
	 * Create a new Alarm from a content provider.
	 * @param c the cursor object tied to the content provider.
	 */
	public Alarm(Cursor c) {	
		this.id       = c.getInt(Columns.idOf(Columns.ID));
		this.hour     = c.getInt(Columns.idOf(Columns.HOUR));
		this.minute   = c.getInt(Columns.idOf(Columns.MINUTE));
		this.timeInMS = c.getLong(Columns.idOf(Columns.TIME));
		this.enabled  = c.getInt(Columns.idOf(Columns.ENABLED)) == 1;
		Extras.Builder b = new Extras.Builder()
		                      .useSound(c.getInt(Columns.idOf(Columns.SOUND_NOTIFICATION)) == 1)
		                      .useVibration(c.getInt(Columns.idOf(Columns.VIBRATION_NOTIFICATION)) == 1)
		                      .gameNotification(c.getInt(Columns.idOf(Columns.GAME_NOTIFICATION)) == 1)
		                      .gameName(c.getString(Columns.idOf(Columns.GAME_NAME)))
		                      .snoozeInterval(c.getInt(Columns.idOf(Columns.SNOOZE_INTERVAL)))
		                      .repetitionDays(Days.decode(c.getInt(Columns.idOf(Columns.REPETITION_DAYS))));

		String[] toneID = c.getString(Columns.idOf(Columns.RINGTONE)).split(",");
		for(String s : toneID){
			if(s.isEmpty()){
				continue;
			}

			//Put try-catch inside of loop if an ID in the middle would fail
			//I would still like rest of IDs to be parsed.
			try {
				int i = Integer.parseInt(s.trim());
				b.addRingtoneID(i);
			} catch (NumberFormatException e) {
				Log.e("Alarm-constructor-exception", "Tried to parse something different then int");
			}
		}

		this.extras = b.build();
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

	/** Retrieve any extra information stored with the alarm. */
	public Extras getExtras() {
		return extras;
	}

	@Override
	public String toString() {
		return "Alarm " + id + " {\n" +
				"\tHour: " + hour +
				"\n\tMinute: " + minute +
				"\n\tTime (millisec): " + timeInMS +
				"\n\tIs enabled: " + enabled + 
				"\n\t" + extras.toString() + 
				"\n}";
		//TODO update
	}

	/**
	 * @return returns a string detailing the time the alarm is set to go off in the format of "HH:mm".
	 */
	public String toPrettyString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(new Date(getTimeInMS()));
	}

	/**
	 * Defines the extra (optional) values of the alarm and a builder for setting them.
	 */
	public static class Extras implements Parcelable {
		private final boolean       useSound;
		private final boolean       useVibration;
		private final List<Integer> ringtoneIDs;
		private final boolean       gameNotification;  
		private final String        gameName;
		private final int 			snoozeInterval;
		private final Days          repetitionDays;
		
		public Extras(Parcel p) {
			this.useSound            = p.readInt() == 1;
			this.useVibration        = p.readInt() == 1;
			
			int size = p.readInt();
			ringtoneIDs = new ArrayList<Integer>(size);
			for (int i = 0; i < size; i++) {
				ringtoneIDs.add(p.readInt());
			}
			
			this.gameNotification    = p.readInt() == 1;
			this.gameName            = p.readString();
			this.snoozeInterval		 = p.readInt();
			this.repetitionDays      = Days.decode(p.readInt());
		}

		private Extras(Builder b) {
			this.useSound         = b.useSound;
			this.useVibration     = b.useVibration;
			this.ringtoneIDs      = b.ringtoneIDs;
			this.gameNotification = b.gameNotification;
			this.gameName         = b.gameName;
			this.snoozeInterval	  = b.snoozeInterval;
			this.repetitionDays   = b.repetitionDays;
		}

		@Override
		public String toString() {
			return "Extras {" + 
					"\n\tSound notification: " + useSound +
					"\n\tVibration notification: " + useVibration +
					"\n\tGame notification: " + gameNotification +
					"\n\tGame name: " + gameName +
					"\n\tSnooze Interval: " + snoozeInterval +
					"\n\t" + repetitionDays.toString() +
					"\n}";
		}

		/** 
		 * @return {@code this} converted to a {@code ContentValues}.
		 */
		public ContentValues toContentValues() {
			ContentValues values = new ContentValues();

			values.put(Columns.SOUND_NOTIFICATION.getLeft(), useSound ? 1 : 0);
			values.put(Columns.VIBRATION_NOTIFICATION.getLeft(), useVibration ? 1 : 0);
			values.put(Columns.GAME_NOTIFICATION.getLeft(), gameNotification ? 1 : 0);
			values.put(Columns.GAME_NAME.getLeft(), gameName);
			values.put(Columns.SNOOZE_INTERVAL.getLeft(), snoozeInterval);
			values.put(Columns.REPETITION_DAYS.getLeft(), repetitionDays.encode());

			String s = "";
			for(Integer i : ringtoneIDs){
				s += i + ",";
			}
			//Used to remove last ","
			if(ringtoneIDs.size() > 0) {
				s = s.substring(0, s.length()-1);
			}
			values.put(Alarm.Columns.RINGTONE.getLeft(), s);

			return values;
		}

		/** @return whether or not the alarm has sound notification */
		public boolean hasSoundNotification() { return useSound; }

		/** @return whether or not the alarm has vibration notification */
		public boolean hasVibrationNotification() { return useVibration; }

		/** 
		 * @return whether or not the alarm has game component that the 
		 *         user must complete before he is able to disable the alarm 
		 */
		public boolean hasGameNotification() { return gameNotification; }

		/** @return the name of the selected game. */
		public String getGameName() { return gameName; }

		/** @return a list of ringtone ids for use when the alarm goes off. */
		public List<Integer> getRingtoneIDs(){ return ringtoneIDs; }

		/** @return the number of minutes the alarm will sleep/snooze */
		public int getSnoozeInterval(){ return snoozeInterval; }
		
		/** Returns a immutable copy of the repetition days */
		public Days getRepetitionDays() { return new Days(repetitionDays); }

		/** Uses the builder pattern to create Alarm extras. */
		public static class Builder {
			// The optional fields set to their default value.
			private boolean             useSound          = true;
			private boolean             useVibration      = true;
			private final List<Integer> ringtoneIDs       = new ArrayList<Integer>();
			private boolean             gameNotification  = false;
			private String              gameName          = "";
			private Integer				snoozeInterval	  = 1;
			private Days 				repetitionDays    = new Days();

			public Builder useSound(boolean value)
				{ useSound = value; 	return this; }

			public Builder useVibration(boolean value)
				{ useVibration = value; 	return this; }

			public Builder addRingtoneID(Integer id)
				{ ringtoneIDs.add(id); 	return this; }	

			public Builder addAllRingtoneIDs(List<Integer> ids)
				{ ringtoneIDs.addAll(ids); return this; }

			public Builder gameNotification(boolean value)
				{ gameNotification = value; 	return this; }

			public Builder gameName(String name)
				{ gameName = name; 	return this; }

			public Builder snoozeInterval(Integer time)
				{ snoozeInterval = time; return this; }

			public Builder repetitionDays(Days days) 
				{ repetitionDays = new Days(days); return this; }
			
			public Extras build() {
				return new Extras(this);
			}
		}
		
		public static final Parcelable.Creator<Extras> CREATOR
					= new Parcelable.Creator<Extras>() {
						public Extras createFromParcel (Parcel p) {
							return new Extras(p);
						}
						public Extras[] newArray(int size) {
							return new Extras[size];
						}
					};

		public int describeContents() {
			return 0;
		}

		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(useSound ? 1 : 0);
			dest.writeInt(useVibration ? 1 : 0);
			
			dest.writeInt(ringtoneIDs.size());
			for (Integer i : ringtoneIDs) {
				dest.writeInt(i);
			}
			
			dest.writeInt(gameNotification ? 1 : 0);
			dest.writeString(gameName);
			dest.writeInt(snoozeInterval);
			dest.writeInt(repetitionDays.encode());
		}
	}

	/** 
	 * This class describes the columns for use with a ContentProvider. 
	 * @see http://www.androidcompetencycenter.com/2009/01/basics-of-android-part-iv-android-content-providers/
	 */
	public static class Columns implements BaseColumns {
		/** The uri that represents an alarm */
		public static final Uri CONTENT_URI = Uri.parse("content://it.chalmers.dat255-bearded-octo-lama/alarm");

		// This describes the table structure for the database.
		// The left hand side of the tuple is the table name and the right is the type.
		public static final Tuple<String, String> ID                     = intCol(_ID);
		public static final Tuple<String, String> HOUR                   = intCol("HOUR");
		public static final Tuple<String, String> MINUTE                 = intCol("MINUTE");
		public static final Tuple<String, String> TIME                   = intCol("TIME_IN_MS");
		public static final Tuple<String, String> ENABLED                = intCol("ENABLED");
		public static final Tuple<String, String> SOUND_NOTIFICATION     = intCol("SOUND_NOTIFICATION");
		public static final Tuple<String, String> VIBRATION_NOTIFICATION = intCol("VIBRATION_NOTIFICATION");
		public static final Tuple<String, String> RINGTONE               = strCol("RINGTONE");
		public static final Tuple<String, String> GAME_NOTIFICATION      = intCol("GAME_NOTIFICATION");
		public static final Tuple<String, String> GAME_NAME              = strCol("GAME_NAME");
		public static final Tuple<String, String> SNOOZE_INTERVAL        = intCol("SNOOZE_INTERVAL");
		public static final Tuple<String, String> REPETITION_DAYS        = intCol("REPETITION_DAYS");

		/** The list is sorted alphabetically after the field names. */
		public static final String[] ALL_COLUMN_NAMES               = getColumnNames();
		public static final List<Tuple<String, String>> ALL_COLUMNS = getColumns();

		

		/** Retrieves the ID/index of a specified db column. */
		public static<E, T> int idOf(Tuple<String, T> column) {
			return ALL_COLUMNS.indexOf(column);
		}
		
		// Shortcut methods used for brevity.
		private static Tuple<String, String> intCol(String colName) {
			return Tuple.valueOf(colName, "INTEGER");
		}
		
		private static Tuple<String, String> strCol(String colName) {
			return Tuple.valueOf(colName, "STRING");
		}
		
		@SuppressWarnings("unchecked") // We know the type of our fields and can ignore.
		private static List<Tuple<String, String>> getColumns() {
			Field[] fields = Columns.class.getDeclaredFields();
			SortedSet<Field> sortedFields = new TreeSet<Field>(new FieldComparator());
			
			// Only add our column (Tuple) fields.
			for(Field f : fields) {
				if(Modifier.isStatic(f.getModifiers()) &&
						f.getType() == Tuple.class) {
					sortedFields.add(f);
				}
			}
			
			// Convert our fields to their tuple value.
			List<Tuple<String, String>> list = new ArrayList<Tuple<String,String>>(sortedFields.size());
			Tuple<String, String> tmpTuple = null;
			
			for(Field f : sortedFields) {
				try {
					tmpTuple = (Tuple<String, String>)f.get(tmpTuple);
					
					list.add(tmpTuple);
				} catch (IllegalArgumentException e) {
					Log.d("Alarm.Columns", 
							"Caught IllegalArgumentException. Msg: " + e.getMessage() +
							"\nField name: " + f.getName());
				} catch (IllegalAccessException e) {
					Log.d("Alarm.Columns", 
							"Caught IllegalAccessException. Msg: " + e.getMessage() +
							"\nField name: " + f.getName());
				}
			}
			
			return Collections.unmodifiableList(list);
		}
		
		private static String[] getColumnNames() {
			 List<Tuple<String, String>> columns = getColumns();
			 String[] names = new String[columns.size()];
			 
			 for(int i = 0; i < names.length; i++) {
				 names[i] = columns.get(i).getLeft();
			 }
			 
			 return names;
		}
		
		private static class FieldComparator implements Comparator<Field> {
			public int compare(Field lhs, Field rhs) {
				// Sort by the variable name, alphabetically.
				return lhs.getName().compareTo(rhs.getName());
			}
		}
	}
}
