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

/**
 * Represents a day of the week.
 * @see java.util.EnumSet<E>
 * @author Emil Edholm
 * @date 20 okt 2012
 */
public enum Weekday {
	
	MONDAY("Monday"),
	TUESDAY("Tuesday"),
	WEDNESDAY("Wednesday"),
	THURSDAY("Thursday"),
	FRIDAY("Friday"),
	SATURDAY("Saturday", true),
	SUNDAY("Sunday", true);
	
	private final boolean isWeekend;
	private final String  stringRepresentation;
	
	private Weekday(String stringRepresentation) {
		this.isWeekend = false;
		this.stringRepresentation = stringRepresentation;
	}
	
	private Weekday(String stringRep, boolean isWeekend) {
		this.isWeekend = isWeekend;
		this.stringRepresentation = stringRep;
	}

	public boolean isWeekend() {
		return isWeekend;
	}
	
	@Override
	public String toString() {
		return stringRepresentation;
	}
	
	public String toShortString() {
		int retLength = 3;
		return toString().substring(0, retLength);
	}
}
