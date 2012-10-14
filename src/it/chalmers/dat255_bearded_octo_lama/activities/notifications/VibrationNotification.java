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

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;


public class VibrationNotification extends NotificationDecorator {
	
	private final Vibrator vib;
	long[] standardPattern = {100,100,50,100,50,100,50,100};
	
	/**
	 * @param decoratedNotification is an Alarm that is decorated with different notifications
	 * @param c is the Context
	 */
	public VibrationNotification(Notification decoratedNotification, Context c) {
		super(decoratedNotification);
		
		vib = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
		Log.d("VibrationNotificaton","Running constructor, decorating");
	}
	@Override
	public void start() {
		// Should use a check vib.hasVibrator() to make sure that the used device has a vibrator
		// but according to the method call we have to use API level 11, and we are using level 10
		super.start();
		Log.d("VibrationNotification", "Called start()");
			vib.vibrate(standardPattern, 0);
	}
	
	@Override
	public void stop() {
			super.stop();
			vib.cancel();
	}
}
