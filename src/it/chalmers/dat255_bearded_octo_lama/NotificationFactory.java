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
import android.media.Ringtone;
/**
 * A factory for building Notifications.
 * @author Johan Andersson
 * @date 10 oct 2012
 *
 */
public enum NotificationFactory {
	;
	
	/** Creates a notification from an alarm.
	 * 
	 * @param extras - extras
	 * @param act - Activity to handle playing and gathering of resources.
	 * @return - A decorated notification built from the settings in the alarm object.
	 */
	public static Notification create(Alarm.Extras extras, Activity act){
		Notification n = new BaseNotification();
	
		if(extras.hasSoundNotification()){
			List<Integer> toneIDs = extras.getRingtoneIDs();
			List<Ringtone> tones = RingtoneFinder.getRingtonesFromID(act, toneIDs);
			n = new SoundNotification(n, tones, act, extras.getVolume());
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
