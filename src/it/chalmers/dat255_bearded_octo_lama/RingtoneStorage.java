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

import java.util.List;

/**
 * Used for passing ringtones between activities safely.
 * @author Emil Edholm
 * @date 22 okt 2012
 * @see {@link http://developer.android.com/guide/faq/framework.html#3}
 */
public enum RingtoneStorage {
	INSTANCE;
	
	private List<String> selectedRingtones;
	
	public void setSelectedRingtones(List<String> selectedRingtones) {
		this.selectedRingtones = selectedRingtones;
	}
	
	public List<String> getSelectedRingtones() {
		return selectedRingtones;
	}
}
