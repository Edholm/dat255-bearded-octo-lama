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

package it.chalmers.dat255_bearded_octo_lama.activities.notifications;


import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;

public class SoundNotification extends NotificationDecorator {
	
	List<Ringtone> notificationSounds = null;
	Ringtone playing = null;
	Activity act;
	public SoundNotification(Notification decoratedNotification, List<Ringtone> ringtones, Activity act) {
		super(decoratedNotification);
		this.act = act;
		notificationSounds = ringtones;
	}
		
	@Override
	public void start() {
		super.start();
		Collections.shuffle(notificationSounds);
		if(notificationSounds == null || notificationSounds.size() > 0){
			playing = notificationSounds.get(0);
		} else {
			//TODO: Fix context here.
			playing = RingtoneManager.getRingtone(null, Settings.System.DEFAULT_ALARM_ALERT_URI);
		}
		playing.play();
	}

	public void stop() {
		super.stop();
		if(playing != null && playing.isPlaying()){
			playing.stop();
		}
	}

}
