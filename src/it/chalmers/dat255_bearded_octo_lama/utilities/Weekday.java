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
public enum Weekday implements Stable {
	
	MONDAY(0),
	TUESDAY(1),
	WEDNESDAY(2),
	THURSDAY(3),
	FRIDAY(4),
	SATURDAY(5),
	SUNDAY(6);
	
	private final int stableID;
	
	private Weekday(int stableID) {
		this.stableID = stableID;
	}

	public int getStableID() {
		return stableID;
	}
}
