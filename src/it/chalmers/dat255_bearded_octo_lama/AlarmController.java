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

import it.chalmers.dat255_bearded_octo_lama.utilities.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Manages all the alarms.
 * @author Emil Edholm
 * @date 1 okt 2012
 */
public enum AlarmController {
	INSTANCE;
	
	/**
	 * Add a new alarm to the database.
	 * @param c the context
	 * @param enabled whether or not the alarm should be enabled
	 * @param hour the hour the alarm is to activate
	 * @param minute the minute the alarm is to activate
	 * @return the uri to the newly added alarm
	 */
	public Uri addAlarm(Context c, boolean enabled, int hour, int minute, List<Integer> ringToneID) {
		ContentResolver cr = c.getContentResolver();
		long time = Time.timeInMsAt(hour, minute);
		
		//TODO remove hardcoded values
		Calendar then = Calendar.getInstance();
		then.add(Calendar.SECOND, 5);
		
		//TODO: Remove hardcoded values
		Log.d("AlarmController", "ringToneID" + ringToneID.toString());
		//ContentValues values = constructContentValues(hour, minute, enabled, time, 1, 1, 1, ringToneID);
		ContentValues values = constructContentValues(
				then.get(Calendar.HOUR_OF_DAY), then.get(Calendar.MINUTE),
				true, time, 1, 1, 1, ringToneID);

		
		Uri uri = cr.insert(Alarm.AlarmColumns.CONTENT_URI, values);
		renewAlarmQueue(c);
		return uri;
	}
	
	/** Only used for testing. Remove in production code */
	public Uri addTestAlarm(Context c) {
		ContentResolver cr = c.getContentResolver();
		Calendar then = Calendar.getInstance();
		then.add(Calendar.SECOND, 5);
		
		List<Integer> ringtoneIDs = new ArrayList<Integer>();
		ringtoneIDs.add(0);
		
		long time = then.getTimeInMillis();
		ContentValues values = constructContentValues(
				then.get(Calendar.HOUR_OF_DAY), then.get(Calendar.MINUTE),
				true, time, 1, 1, 1, ringtoneIDs);

		Uri uri = cr.insert(Alarm.AlarmColumns.CONTENT_URI, values);
		renewAlarmQueue(c);
		return uri;
	}

	private ContentValues constructContentValues(int hour, int minute,
			boolean enabled, long time, int textNot, int soundNot, int vibrationNot, List<Integer> RingtoneIDs) {
		ContentValues values = new ContentValues();
		
		values.put(Alarm.AlarmColumns.HOUR, hour);
		values.put(Alarm.AlarmColumns.MINUTE, minute);
		values.put(Alarm.AlarmColumns.ENABLED, enabled ? 1 : 0);
		values.put(Alarm.AlarmColumns.TIME, time);
		values.put(Alarm.AlarmColumns.TEXT_NOTIFICATION, textNot);
		values.put(Alarm.AlarmColumns.SOUND_NOTIFICATION, soundNot);
		values.put(Alarm.AlarmColumns.VIBRATION_NOTIFICATION, vibrationNot);
		
		String s = "";
		for(Integer i:RingtoneIDs){
			s += i + ",";
		}
		Log.d("AlarmController.s", "S = " + s);
		//Used to remove last ","
		s = s.substring(0, s.length()-1);
		values.put(Alarm.AlarmColumns.RINGTONE, s);
		

		return values;
	}
	
	/**
	 * Removes a specific alarm from the database
	 * @param c the context
	 * @param alarmID the ID of the alarm to remove.
	 */
	public void deleteAlarm(Context c, int alarmID) {
		ContentResolver cr = c.getContentResolver();
		cr.delete(Uri.withAppendedPath(Alarm.AlarmColumns.CONTENT_URI, alarmID + ""), "", null);
		
		renewAlarmQueue(c);
	}
	
	public void toggleAlarm(Context c, int alarmID) {
		ContentResolver cr = c.getContentResolver();
		Alarm alarm = getAlarm(c, alarmID);
		
		Uri uri = Alarm.AlarmColumns.CONTENT_URI.buildUpon().appendPath(alarmID + "").build();
		ContentValues values = new ContentValues();
		
		// Reverse enabled
		values.put(Alarm.AlarmColumns.ENABLED, alarm.isEnabled() ? 0 : 1);
		
		// If we are re-enabling the alarm again, and its time has passed/expired, we need to update it first.
		long now = System.currentTimeMillis();
		if(!alarm.isEnabled() && alarm.getTimeInMS() < now) { // Since we are reversing the boolean...
			long time = Time.timeInMsAt(alarm.getHour(), alarm.getMinute());
			values.put(Alarm.AlarmColumns.TIME, time);
		}
		
		cr.update(uri, values, null, null);
	}
	
	/**
	 * Returns the alarm with the specified ID.
	 * @param c the context
	 * @param alarmID the alarm to fetch
	 * @return the alarm with the specified ID if found, else null.
	 */
	public Alarm getAlarm(Context c, int alarmID) {
		ContentResolver cr = c.getContentResolver();
		
		Uri uri = Uri.withAppendedPath(Alarm.AlarmColumns.CONTENT_URI, alarmID + "");
		Cursor cur = cr.query(uri, Alarm.AlarmColumns.ALL_COLUMNS, null, null, null);
		
		Alarm a = null;
		if(cur != null && cur.moveToFirst()) {
			a = new Alarm(cur);
			cur.close();
		}
		
		return a;
	}
	
	/**
	 * Returns all alarms in the database sorted by hour and minute in ascending order.
	 * @param c the context
	 * @return a list of all alarms in the database.
	 */
	public List<Alarm> getAllAlarms(Context c) {
		return getAlarms(c, null, null, "HOUR, MINUTE ASC");
	}
	
	private List<Alarm> getAlarms(Context c, String where, String[] args, String sortOrder) {
		ContentResolver cr = c.getContentResolver();
		
		Uri uri = Alarm.AlarmColumns.CONTENT_URI;
		Cursor cur = cr.query(uri, Alarm.AlarmColumns.ALL_COLUMNS, where, args, sortOrder);
		
		
		if(cur != null && cur.moveToFirst()) {
			List<Alarm> alarms = new ArrayList<Alarm>();
			
			do {
				alarms.add(new Alarm(cur));
			} while(cur.moveToNext());
			
			return alarms;
		}
		
		return Collections.emptyList();
	}
	
	/**
	 * @return given an uri that points to a specific alarm, returns the id of that Alarm
	 */
	public int extractIDFromUri(Uri uri) {
		String segment = uri.getPathSegments().get(1);
        return Integer.parseInt(segment);
	}
	
	/** 
	 * Activates next alarm in the database if one exists.
	 * @param c the context
	 */
	public void renewAlarmQueue(Context c) {		
		Alarm a = getNextInQueue(c);
		
		if(a != null) {
			enableAlarmManager(c, a);
		}
		
		disableAlarmManager(c);
	}
	/** Get the next alarm that is enabled and nearest in time to now */
	private Alarm getNextInQueue(Context context) {
		// Get all enabled alarms in descending order.
		List<Alarm> alarms = getAlarms(context, "Enabled=?", new String[] {"1"}, "TIME_IN_MS DESC");
		
		long minTime = Long.MAX_VALUE, now = System.currentTimeMillis();
		Alarm theAlarm = null;
		for(Alarm a : alarms) {
			
			// If alarm time has passed, disable alarm.
			if(a.getTimeInMS() < now) {
				toggleAlarm(context, a.getId());
				continue;
			}
			
			if(a.getTimeInMS() < minTime) {
				minTime = a.getTimeInMS();
				theAlarm = a;
			}
		}
		Log.d("queue", "Next in queue: " + theAlarm.toString());
		return theAlarm;
	} 
	
	/** Disable all alarms that have expired (time for their alert has passed) */ 
	public void disableExpired(Context c) {
		ContentResolver cr = c.getContentResolver();
		long now = System.currentTimeMillis();
		List<Alarm> alarms = getAlarms(c, "TIME_IN_MS <= ?", new String[]{now + ""}, null);
		
		Uri uri = null;
		ContentValues value = new ContentValues();
		value.put(Alarm.AlarmColumns.ENABLED, 0); // == Disable alarm.
		for(Alarm a : alarms) {
			uri = Alarm.AlarmColumns.CONTENT_URI.buildUpon().appendPath(a.getId() + "").build();
			cr.update(uri, value, null, null);
		}
	}
	
	/** Enable an alarm in the AlarmManager. Only one alarm should be activated at a time */
	private void enableAlarmManager(Context c, Alarm a) {
		AlarmManager am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);

		// Append the alarm ID to the intent, then the receiving class can fetch the alarm.
		Intent intent = new Intent(c, AlarmReceiver.class);
		intent.putExtra(Alarm.AlarmColumns._ID, a.getId());
		
		PendingIntent alarmIntent = PendingIntent.getBroadcast(c, 12, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		am.set(AlarmManager.RTC_WAKEUP, a.getTimeInMS(), alarmIntent);
	}
	
	/** Disable/cancel the pending intent in the AlarmManager */
	private void disableAlarmManager(Context c) {
		AlarmManager am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(c, 0, new Intent(c, AlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
	
		am.cancel(alarmIntent);
	}
}
