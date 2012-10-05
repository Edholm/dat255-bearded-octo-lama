package it.chalmers.dat255_bearded_octo_lama.activities.notifications;

import java.util.Collections;
import java.util.List;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.MediaStore.Audio.Media;
import android.provider.Settings;

public class SoundNotification extends NotificationDecorator {
	
	List<Ringtone> notificationSounds;
	Ringtone playing = null;
	
	public SoundNotification(Notification decoratedNotification, List<Ringtone> ringtones) {
		super(decoratedNotification);
	}
	
	@Override
	public void start() {
		super.start();
		Collections.shuffle(notificationSounds);
		if(notificationSounds.size() > 0){
			playing = notificationSounds.get(0);
		} else {
			playing = RingtoneManager.getRingtone(null, Settings.System.DEFAULT_ALARM_ALERT_URI);
		}
		playing.play();
	}

	public void stop() {
		if(playing != null && playing.isPlaying()){
			playing.stop();
		}
	}

}
