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
 * Interface for marking enums as stable for use in a serialized enumset.
 * It's unsafe to use the Enum getOrdinal value when serializing a enumset.
 * Therefore we have to make our own.
 * @see <a href="http://stackoverflow.com/a/2199657">Inspired by</a>
 * @author Emil Edholm
 * @date 20 okt 2012
 */
public interface Stable {
	
	/**
	 * The stable ID for use in a serialized form.
	 * @return a unique integer representing a id.
	 */
	int getStableID();
}
