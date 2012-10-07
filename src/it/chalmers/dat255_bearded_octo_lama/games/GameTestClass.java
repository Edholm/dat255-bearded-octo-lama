package it.chalmers.dat255_bearded_octo_lama.games;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

public class GameTestClass extends AbstractGameView {

	private int var1, var2;
	private float x, y;
	
	public GameTestClass(Context context, RelativeLayout parentView, LinearLayout btnHolder) {
		super(context, parentView, btnHolder);
		
		x = y = 0;
	}



	@Override
	protected void updateGraphics(Canvas c) {
		//Test the canvas by drawing some text, a circle and a nice background.
		String text = var1 + " + " + var2;
		
		c.drawARGB(100, 51, 204, 255);
		c.drawText(text, this.getWidth()/2, 0, painter);
		c.drawCircle(x, y, 20, painter);
	}


	@Override
	protected void updateGame() {
		//No logic to test at the moment
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//Sleep a bit to not overload the system with unnecessary amount of data.
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Test input by updating the x and y values on each action.
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
