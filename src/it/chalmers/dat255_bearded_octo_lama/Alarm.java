/**
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

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


/**
 * Represents a chosen alarm time in the format of hh:mm.
 * @author Emil Edholm
 * @date 28 sep 2012
 */
public class Alarm {
	
	/** The time when the alarm is set to go off */
	private final Calendar then;
	
	private PendingIntent alarmIntent;
	
	/**
	 * Construct a new alarm in the format of hh:mm (h0 h1 : m0 m1).
	 * @param h0 the 1st hour
	 * @param h1 the 2nd hour
	 * @param m0 the 1st minute
	 * @param m1 the 2nd minute
	 */
	private Alarm(int h0, int h1, int m0, int m1) {
		this(h0 * 10 + h1, m0 * 10 + m1);
	}
	
	private Alarm(int hour, int minute) {
		then = Calendar.getInstance();
		then.set(Calendar.HOUR_OF_DAY, hour);
		then.set(Calendar.MINUTE, minute);
		
		if(then.before(Calendar.getInstance())) // Before "now" means we have to add a day
			then.add(Calendar.DAY_OF_YEAR, 1);
	}
	
	/**
	 * Create a new alarm at the specified hour and time but does not activate it.
	 * 
	 */
	public static Alarm newAlarmAt(int h0, int h1, int m0, int m1) {
		return new Alarm(h0, h1, m0, m1);
	}
	
	/**
	 * Create a new alarm IN the specified amount of time but does not activate it
	 */
	public static Alarm newAlarmIn(int h0, int h1, int m0, int m1) {
		int h = h0 * 10 + h1;
		int m = m0 * 10 + m1;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, h);
		cal.add(Calendar.MINUTE, m);
		
		return new Alarm(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	
	/** Activate the alarm */
	public void activateAlarm(Context con) {		
		AlarmManager am = (AlarmManager)con.getSystemService(Context.ALARM_SERVICE);
		
		if(alarmIntent == null)
			alarmIntent = PendingIntent.getBroadcast(con, 0, new Intent(con, AlarmReceiver.class), 0);
		
		am.set(AlarmManager.RTC_WAKEUP, then.getTimeInMillis(), alarmIntent);
	}
	
	/** Deactivate the alarm */
	public void deactivateAlarm(Context con) {
		AlarmManager am = (AlarmManager)con.getSystemService(Context.ALARM_SERVICE);
		am.cancel(alarmIntent);
	}
	
	/** @return true of alarm is activated, else false */
	public boolean isAlarmActivated() { 
		// TODO: fix actual query.
		return false;
	}
	
	/**
	 * Toggles alarm state. If alarm is inactivated, it is  activated and vice versa.
	 */
	public void toggleAlarm(Context con) {
		if(!isAlarmActivated()) {
			deactivateAlarm(con);
			return;
		}
		
		activateAlarm(con);
	}
	
	/** Retrieves the hour the alarm is set to go off */
	public int getHour() {
		return then.get(Calendar.HOUR_OF_DAY);
	}
	
	/** Retrieves the minute the alarm is set to go off */
	public int getMinute() {
		return then.get(Calendar.MINUTE);
	}
	
	/**
	 * @return returns the time left until the alarm time (regardless of activation state) in format {@code hh:mm:ss}.
	 */
	public String getTimeLeft() {
		Calendar now = Calendar.getInstance();
		
		long millis = then.getTimeInMillis() - now.getTimeInMillis();
		
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - 
			    		TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    		TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		return String.format("%d hour %d min, %d sec", hours , minutes, seconds);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getHour();
		result = prime * result + getMinute();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Alarm))
			return false;

		Alarm other = (Alarm) obj;
		if (getHour() != other.getHour() || 
				getMinute() != other.getMinute())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getHour() + ":" + getMinute();
	}
	
	

}
