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

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.games.AbstractGameView;
import it.chalmers.dat255_bearded_octo_lama.games.RocketLanderGame;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameNotification extends NotificationDecorator {
	
	private RelativeLayout mainContentHolder;
	private LinearLayout dismissAlarmLayout;
	private final Activity act;
	private final String gameName;

	public GameNotification(Notification decoratedNotification, Activity act, String gameName) {
		super(decoratedNotification);
		
		this.act = act;
		this.gameName = gameName;
	}

	@Override
	public void start() {
		super.start();
		
		AbstractGameView gameView = new RocketLanderGame(act);
		
		mainContentHolder = (RelativeLayout) act.findViewById(R.id.mainContentLayout);
		dismissAlarmLayout = (LinearLayout) act.findViewById(R.id.dismissAlarmLayout);

		//Make the holder for dismiss/snooze alarm buttons invisible while the game is running.
		dismissAlarmLayout.setVisibility(View.GONE);
		mainContentHolder.addView(gameView);

		//Adding all views that build the games UI after the surfaceView has been added.
		//Otherwise the UI views would all get stuck under the surface view.
		List<View> uiList = gameView.getUIComponents();
		if(uiList != null) {
			for(View v : uiList) {
				mainContentHolder.addView(v);
			}
		} 
		gameView.resume();
	}

	@Override
	public void stop() {
		super.stop();
	}
}
