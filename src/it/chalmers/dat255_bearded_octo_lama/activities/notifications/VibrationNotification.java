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

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;


public class VibrationNotification extends NotificationDecorator {
	private final Activity act;
	private final Vibrator vib;
	long[] standardPattern = {100,100,50,100,50,100,50,100};
	
	//TODO why using activity?
	public VibrationNotification(Notification decoratedNotification, Activity act) {
		super(decoratedNotification);
		this.act = act;
		Context c = act.getApplicationContext();
		vib = (Vibrator) c.getSystemService(c.VIBRATOR_SERVICE);
		Log.d("VibrationNotificaton","Running constructor, decorating");
		
	}
	@Override
	public void start() {
		super.start();
		Log.d("VibrationNotification", "Called start()");
			vib.vibrate(standardPattern, 0);
		//}
	}
	
	public void stop() {
			vib.cancel();
		//}
	}
}
