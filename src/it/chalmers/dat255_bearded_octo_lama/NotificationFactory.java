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

import it.chalmers.dat255_bearded_octo_lama.activities.notifications.BaseNotification;
import it.chalmers.dat255_bearded_octo_lama.activities.notifications.GameNotification;
import it.chalmers.dat255_bearded_octo_lama.activities.notifications.Notification;
import it.chalmers.dat255_bearded_octo_lama.activities.notifications.SoundNotification;
import it.chalmers.dat255_bearded_octo_lama.activities.notifications.VibrationNotification;
import it.chalmers.dat255_bearded_octo_lama.utilities.RingtoneFinder;

import java.util.List;

import android.app.Activity;
import android.util.Log;

public enum NotificationFactory {
	;
	public static Notification create(Alarm alarm, Activity act){
		Notification n = new BaseNotification();
		Alarm.Extras extras = alarm.getExtras();
	
		// TODO fix ringtones
		if(extras.hasSoundNotification()){
			List<Integer> toneIDs = extras.getRingtoneIDs();
			Log.d("NotificationFactory", toneIDs.toString());
			n = new SoundNotification(n, 
					RingtoneFinder.getRingtonesFromIDs(act, toneIDs), act);
		}
		if(extras.hasVibrationNotification()){
			n = new VibrationNotification(n, act);
		}
		if(extras.hasGameNotification()) {
			n = new GameNotification(n, act, extras.getGameName());
		}
		return n;
	}

}
