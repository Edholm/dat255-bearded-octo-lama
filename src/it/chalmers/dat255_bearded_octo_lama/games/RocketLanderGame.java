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

import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.games.anno.Game;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

@Game(name = "Rocket Lander")
public class RocketLanderGame extends AbstractGameView {
	
	//Set all physics constants
	private static final int GRAV_ACCEL        = 90;
	private static final int ENGINE_ACCEL      = 140;
	private static final int ENGINE_SIDE_ACCEL = 50;
	private static final int INIT_SPD          = 50;
	
	//Set goal constants
	private static final int MAX_VERT_SPD = 100;
	private static final int MAX_HORI_SPD = 50;
	
	private long lastTime;
	private double currentYSpd, currentXSpd;
	private double rocketX, rocketY;
	private boolean engineIsRunning;
	private double groundYLevel;
	private float pressX;
	
	private Bitmap rocketBitmap, backgroundBitmap;
	
	public RocketLanderGame(Context context) {
		super(context);
		
		initGame();
	}

	private void initGame() {
		int firstRunDelay = 100;
		lastTime = System.currentTimeMillis() + firstRunDelay;
		
		rocketBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rocket);
		backgroundBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.rocket_background);
		
		//Set painter color to orange for when painting the rocket flame.
		int alpha = 255;
		int red = 255;
		int green = 100;
		int blue = 0;
		getPainter().setARGB(alpha, red, green, blue);
		
		resetGame();
	}
	
	private void resetGame() {
		rocketX = getWidth()/2;
		rocketY = 0;
		
		engineIsRunning = false;
		currentYSpd = INIT_SPD;
		//Make so the initial x-axis speed is a bit random.
		Random random = new Random();
		int maxVariation = 10;
		double randomValue = random.nextInt(maxVariation)-maxVariation/2;
		currentXSpd = INIT_SPD*randomValue;
		
		//TESTCODE: Only use this if testing with Robotium. This will remove the x-speed.
		currentXSpd = 0;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//When the size of the view is changed we are going to reset the position of the
		//rocket and reset the ground level to avoid any issues with bad positioning.
		rocketX = w/2;
		rocketY = 0;
		
		groundYLevel = h/4.0 * 3.0; //Set groundlevel to three fourth of the view height.
		//Rezise the background
		backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, w, h, true);
	}

	@Override
	protected void updateGame() {
		long now = System.currentTimeMillis();
		
		if(lastTime > now) {
			return;
		}
		
		double msPerSec = 1000.0;
		double timeSinceLast = (now - lastTime)/msPerSec;
		
		//Set and calculate acceleration.
		double xAcceleration = 0;
		double yAcceleration = GRAV_ACCEL * timeSinceLast;
		
		//Calculate new speed of the aircraft.
		if(engineIsRunning) {
			//Add engine acceleration.
			yAcceleration -= (ENGINE_ACCEL * timeSinceLast);
			
			//Check if the player touches the screen on the left or right side of the rocket.
			if(pressX > rocketX) {
				xAcceleration -= ENGINE_SIDE_ACCEL * timeSinceLast;
			}
			else {
				xAcceleration += ENGINE_SIDE_ACCEL * timeSinceLast;
			}
		}
		
		double oldYAcc = currentYSpd;
		double oldXAcc = currentXSpd;
		
		currentXSpd += xAcceleration;
		currentYSpd += yAcceleration;
		
		rocketX += timeSinceLast * (currentXSpd + oldXAcc)/2.0;
		//If the rocket goes outside the view we make it appear on the other side
		rocketX = (rocketX < 0) ? getWidth() : rocketX % getWidth();
		rocketY += timeSinceLast * (currentYSpd + oldYAcc)/2.0;
		
		//Check if aircraft has landed or crashed.
		if(rocketY >= groundYLevel) {
			
			//Check if it's a crash.
			if(currentYSpd > MAX_VERT_SPD || currentXSpd > MAX_HORI_SPD
					|| currentXSpd < -MAX_HORI_SPD) {
				resetGame();
			}
			else {
				//If it's not a crash, end the game.
				endGame();
			}
			
		}
		
		lastTime = now;
	}

	@Override
	protected void updateGraphics(Canvas c) {	
		//Paint the background
		c.drawBitmap(backgroundBitmap, 0, 0, getPainter());
		
		//Draw flame from rocket if the engine is running.
		if(engineIsRunning) {
			int flameRadius = rocketBitmap.getWidth()/3;
			c.drawCircle((float)rocketX, (float)rocketY-flameRadius, flameRadius, getPainter());
		}
		
		//Draw the rocket
		c.drawBitmap(rocketBitmap, (float)(rocketX - rocketBitmap.getWidth()/2), 
				(float)(rocketY - rocketBitmap.getHeight()), getPainter());
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Check for input.
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			engineIsRunning = true;
			pressX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			pressX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			engineIsRunning = false;
			break;
		}
		
		return true;
	}

}