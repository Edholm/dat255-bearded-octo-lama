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
package it.chalmers.dat255_bearded_octo_lama.games;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GameTestClass extends AbstractGameView {

	private int var1, var2;
	private float x, y;
	
	public GameTestClass(Context context, LinearLayout dismissAlarmLayout) {
		super(context, dismissAlarmLayout);
		
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
