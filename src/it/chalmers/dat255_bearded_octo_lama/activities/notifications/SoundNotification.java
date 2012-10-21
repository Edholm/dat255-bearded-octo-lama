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


import it.chalmers.dat255_bearded_octo_lama.utilities.RingtoneFinder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Used to describe a sound notification.
 * @author Johan Andersson
 * @modified Emil Edholm
 */
public class SoundNotification extends NotificationDecorator {

	private List<Ringtone> notificationSounds = Collections.emptyList();
	private Ringtone selectedSound = null;
	private final Context context;
	private final Activity act;
	private MediaPlayer mp = null;
	private int origVolume;
	private static boolean isPlaying = false;

	public SoundNotification(Notification decoratedNotification, List<Ringtone> ringtones, Activity act) {
		super(decoratedNotification);
		this.context = act;
		this.act = act;
		notificationSounds = ringtones;
	}

	@Override
	public void start() {
		super.start();
		//If clause to check if running on emulator
		if(!(Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))){
			//If clause to make sure a second sound loop doesn't start
			if(!isPlaying){
				//Using random song of those listed.
				Collections.shuffle(notificationSounds);
				if(!notificationSounds.isEmpty()){
					selectedSound = notificationSounds.get(0);
				} else {
					Log.d("SoundNotification", "notificationSounds is empty");
					// Use default sound if no sounds listed previously.
					selectedSound = RingtoneManager.getRingtone(context.getApplicationContext(), 
							Settings.System.DEFAULT_ALARM_ALERT_URI);
				}
				//If clause if you use a device without sound/default alarm.
				Uri uri = null;
				if(selectedSound != null){
					uri = RingtoneFinder.findRingtoneUri(act, selectedSound);	
					AudioManager audio = (AudioManager) act.getSystemService(Context.AUDIO_SERVICE);
					//Saves volume to reset to previous state.
					origVolume = audio.getStreamVolume(AudioManager.STREAM_ALARM);
					int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_ALARM);
					audio.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
					mp = new MediaPlayer();
					try {
						mp.reset();
						mp.setDataSource(context, uri);
						mp.setAudioStreamType(AudioManager.STREAM_ALARM);
						mp.setLooping(true);
						mp.setVolume(1f,1.f);
						mp.prepare();
						mp.start();
						isPlaying = true;
					} catch (IllegalArgumentException e) {
						Log.e("SoundNotification", "IllegalArgumentException");
						e.printStackTrace();
					} catch (SecurityException e) {
						Log.e("SoundNotification", "SecurityException");
						e.printStackTrace();
					} catch (IllegalStateException e) {
						Log.e("SoundNotification", "IllegalStateException");
						e.printStackTrace();
					} catch (IOException e) {
						Log.e("SoundNotification", "IOException");
						e.printStackTrace();
					}
				}
			}
		}
	}


	@Override
	public void stop() {
		super.stop();
		//Gets the audiomanager and return volume to system original
		((AudioManager) act.getSystemService(Context.AUDIO_SERVICE))
		.setStreamVolume(AudioManager.STREAM_ALARM, origVolume, 0);
		if(mp != null && mp.isPlaying()){
			mp.stop();
			isPlaying = false;
		}
	}
}
