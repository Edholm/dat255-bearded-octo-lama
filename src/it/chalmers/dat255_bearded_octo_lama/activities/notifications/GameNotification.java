package it.chalmers.dat255_bearded_octo_lama.activities.notifications;

import java.util.List;

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;
import it.chalmers.dat255_bearded_octo_lama.games.AbstractGameView;
import it.chalmers.dat255_bearded_octo_lama.games.RocketLanderGame;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameNotification extends NotificationDecorator {
	
	private RelativeLayout mainContentHolder;
	private LinearLayout dismissAlarmLayout;
	private Activity act;

	public GameNotification(Notification decoratedNotification, Activity act) {
		super(decoratedNotification);
		
		this.act = act;
	}

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

	public void stop() {
		super.stop();
	}
}
