package it.chalmers.dat255_bearded_octo_lama.games;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GameTestClass extends AbstractGameView {

	private int var1, var2;
	private float x, y;
	
	public GameTestClass(Context context, LinearLayout parentView) {
		super(context, parentView);
		
		initGame();
	}

	private void initGame() {
		Random randomGenerator = new Random();
		
		var1 = randomGenerator.nextInt(100) + 1;
		var2 = randomGenerator.nextInt(100) + 1;
		
		x = this.getWidth()/2;
		y = this.getHeight()/2;
	}

	@Override
	protected void updateGraphics(Canvas c) {
		//TODO: Implement rendering and clean testCode
		String text = var1 + " + " + var2;
		
		c.drawText(text, this.getWidth()/2, 0, painter);
		c.drawARGB(100, 51, 204, 255);
		c.drawCircle(x, y, 20, painter);
	}


	@Override
	protected void updateGame() {
		//TODO: Fix game logic
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//Sleep a bit to not overload the system with unnecessary amount of data.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			break;
		}
		
		return true;
	}

}
