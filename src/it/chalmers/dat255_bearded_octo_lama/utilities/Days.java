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

	/**
	 * @see java.util.AbstractCollection#add(java.lang.Object)
	 */
	public boolean add(Weekday day) {
		return days.add(day);
	}

	/**
	 * @see java.util.AbstractCollection#contains(java.lang.Object)
	 */
	public boolean contains(Object object) {
		return days.contains(object);
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