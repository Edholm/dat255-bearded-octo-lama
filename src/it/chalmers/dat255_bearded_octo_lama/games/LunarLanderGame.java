package it.chalmers.dat255_bearded_octo_lama.games;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LunarLanderGame extends AbstractGameView {
	
	//Set all physics constants
	private final int GRAV_ACCEL = 1;
	private final int ENGINE_ACCEL = 1;
	private final int MAX_SPD = 1;
	private final int INIT_SPD = 1;
	
	//Set goal constants
	private final int MAX_LANDING_SPEED = 1;
	
	private long lastTime;
	private int currentSpeed;
	private double rocketX, rocketY;
	private boolean engineIsRunning;
	private int groundYLevel;
	
	public LunarLanderGame(Context context, RelativeLayout parentView,
			LinearLayout dismissAlarmLayout) {
		super(context, parentView, dismissAlarmLayout);
		// TODO Auto-generated constructor stub
		
		initUI();
		initGame();
	}


	private void initUI() {
		// TODO Auto-generated method stub
		
	}
	
	private void initGame() {
		rocketX = parentView.getWidth()/2;
		rocketY = parentView.getHeight()/2;
		
		lastTime = System.currentTimeMillis();
	}

	
	@Override
	protected void updateGame() {
		
		long now = System.currentTimeMillis();
		
		double timeSinceLast = (now - lastTime)/1000;
		
		//Set and calculate acceleration.
		double ddx = 0;
		double ddy = -GRAV_ACCEL * timeSinceLast;
		
		//Calculate new speed of the aircraft.
		if(engineIsRunning) {
			//Add engine acceleration.
			ddy += ENGINE_ACCEL * timeSinceLast;
		}
		
		rocketX += (0);
		rocketY += (ddy * timeSinceLast);
		
		//Check if aircraft has landed or crashed.
		if(currentSpeed > MAX_LANDING_SPEED) {
			//TODO: Crash
		}
		
		lastTime = now;
	}

	@Override
	protected void updateGraphics(Canvas c) {
		float canvasWidth = parentView.getWidth();
		float canvasHeight = parentView.getHeight();
		
		// Paint heaven then ground.
		painter.setARGB(100, 51, 204, 255);
		c.drawRect(0, 0, canvasWidth, groundYLevel, painter);
		painter.setARGB(100, 102, 0, 0);
		c.drawRect(0, groundYLevel, canvasWidth, canvasHeight, painter);
		
		
	}

}
