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
package it.chalmers.dat255_bearded_octo_lama.utilities;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Contains utility methods for working with time
 * @author Emil Edholm
 * @date 14 okt 2012
 */
public final class Time {
	;
	
	/** Returns the time left until a specified time (in ms) */
	public static String getTimeLeft(long then) {
		Calendar now = Calendar.getInstance();
		
		long millis = then - now.getTimeInMillis();
		
		long hours   = TimeUnit.MILLISECONDS.toHours(millis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - 
			    		TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    		TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
		
		return String.format("%d hour %d min, %d sec", hours , minutes, seconds);
	}
	
	/**
	 * Calculates the time in milliseconds at the (next) hour and minute time.
	 */
	public static long timeInMsAt(int hour, int minute) {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, hour);
		time.set(Calendar.MINUTE, minute);
		time.set(Calendar.SECOND, 0); // This makes the alarm go of at the right time
		
		if(time.before(Calendar.getInstance())) { // Before "now" means we have to add a day
			time.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return time.getTimeInMillis();
	}
}