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
import java.util.EnumSet;

/**
 * Used to store weekdays in a set.
 * @author Emil Edholm
 * @date 20 okt 2012
 */
public class Days {

	private final EnumSet<Weekday> days;
	
	/** Create an empty set */
	public Days() {
		days = EnumSet.noneOf(Weekday.class);
	}
	
	/**
	 * @param initDay - start the collection with this day.
	 */
	public Days(Weekday initDay) {
		days = EnumSet.of(initDay);
	}
	
	/**
	 * Create a list of days from the specified start to end.
	 * Eg. start = Monday and End Thursday. Monday, Tuesday, Wednesday and Thursday will be added. 
	 * @param start - the start day
	 * @param end - the end day
	 */
	public Days(Weekday start, Weekday end) {
		days = EnumSet.range(start, end);
	}
	
	/**
	 * @param baseUpon - base the Days upon this set of day(s)
	 */
	public Days(EnumSet<Weekday> baseUpon) {
		days = EnumSet.copyOf(baseUpon);
	}
	
	/** Copy constructor */
	public Days(Days rhs) {
		days = EnumSet.copyOf(rhs.days);
	}

	/**
	 * @see java.util.AbstractCollection#add(java.lang.Object)
	 */
	public boolean add(Weekday day) {
		return days.add(day);
	}

	/**
	 * @see java.util.AbstractCollection#contains(java.lang.Object)
	 */
	public boolean contains(Weekday day) {
		return days.contains(day);
	}

	/**
	 * @see java.util.AbstractCollection#isEmpty()
	 */
	public boolean isEmpty() {
		return days.isEmpty();
	}

	/**
	 * @see java.util.AbstractCollection#remove(java.lang.Object)
	 */
	public boolean remove(Object object) {
		return days.remove(object);
	}

	/**
	 * The number of days added to the list.
	 * @see java.util.AbstractCollection#size()
	 */
	public int size() {
		return days.size();
	}
	
	/** Whether or not all days are added to the list */
	public boolean containsAllDays() {
		return size() == Weekday.values().length;
	}
	
	/**
	 * Whether or not the set contains the specified {@code day}.
	 * @param day - an integer that describes the day of week where 0 
	 *              correlates to Monday and 6 correlates to Sunday
	 * @return true if the corresponding day exists in the set, else false.
	 */
	public boolean contains(int day) {
		for(Weekday d : days) {
			if(d.ordinal() == day) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return returns the number of days left until the nearest day in the set
	 *  	   or 0 if same day or -1 if there are no days in the set.
	 */
	public int daysLeft() {
		Calendar cal = Calendar.getInstance();
		return daysLeft(cal);
	}
	
	/** Same as {@code Days#daysLeft()} but with specified calendar to check against. */
	public int daysLeft(Calendar cal) {
		if(size() == 0) return -1;
		
		// Normalize the day since DAY_OF_WEEK starts on sunday.
		int today = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7;
		
		int dayCount = 0, day = 0;
		for(; dayCount < 7; dayCount++) {
			day = (today + dayCount) % 7;
			if(contains(day)) {
				break;
			}
		}
		
		return dayCount;
	}
	
	/**
	 * @see java.util.AbstractSet#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}
		
		if(!(object instanceof Days)) {
			return false;
		}
		
		// Now null-safe
		Days rhs = (Days)object;
		if(this.size() != rhs.size()) {
			return false;
		}
		
		for(Weekday day : days) {
			if(!rhs.contains(day)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @see java.util.AbstractSet#hashCode()
	 */
	@Override
	public int hashCode() {
		return days.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Days: ");
		
		for(Weekday d : days) {
			sb.append(d.toString());
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length()); // Remove last comma.
		return sb.toString();
	}
	
	/**
	 * This will return a list with every day in the {@code Days}.
	 * The weekdays will be written with short annotations.
	 * Will return {@value "All days"} if all weekdays are represented in {@code Days}.
	 */
	public String toShortStringList() {
		StringBuilder sb = new StringBuilder();
		
		if(containsAllDays()) {
			sb.append("Everyday");
			return sb.toString();
		}
		
		for(Weekday d : days) {
			sb.append(d.toShortString());
			sb.append(", ");
		}
		if(sb.length() > 2) {
			sb.delete(sb.length()-2, sb.length()); // Remove last comma only if there are weekdays represented.
		}
		return sb.toString();
	}

	/**
	 * Decodes a previously encoded {@code Days} to new instance with the same values 
	 * @param encodedValue - the value generated by {@code Days.encode()}
	 * @return a decoded days.
	 * @see <a href="http://stackoverflow.com/a/2199693">Inspired by</a>
	 */
	public static Days decode(int encodedValue) {
        Weekday[] values = Weekday.values();
        EnumSet<Weekday> result = EnumSet.noneOf(Weekday.class);
        
        while (encodedValue != 0) {
            int ordinal = Integer.numberOfTrailingZeros(encodedValue);
            
            // Prepare next ordinal
            encodedValue ^= Integer.lowestOneBit(encodedValue);
            result.add(values[ordinal]);
        }
        return new Days(result);
	}
	
	/**
	 * Decodes the EnumSet into a form more suitable for storage in a database.
	 */
	public int encode() {
		  int encodedValue = 0;

		  for (Weekday day : days) {
		    encodedValue |= (1 << day.ordinal());
		  }

		  return encodedValue;
	}
}
