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
 * Represents a generic way to store a 2-tuple.
 * A tuple is a "list" containing two elements, one on the left and one on the right.
 * @author Emil Edholm
 * @date 21 okt 2012
 */
public final class Tuple<E, T> {

	private final E left;
	private final T right;
	
	
	private Tuple(E left, T right) {
		this.left = left;
		this.right = right;
	}
	
	public static <E, T> Tuple<E, T> valueOf(E left, T right) {
		return new Tuple<E, T>(left, right);
	}
	
	public static <E, T> Tuple<E, T> valueOf(Tuple<E, T> other) {
		return valueOf(other.left, other.right);
	}
	
	public E getLeft() { return left; }
	
	public T getRight() { return right; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Tuple)) {
			return false;
		}
		
		@SuppressWarnings("rawtypes") // We don't care about the types atm.
		Tuple other = (Tuple) obj;
		return this.left.equals(other.left) && 
				this.right.equals(other.right);
	}
	
	
}
