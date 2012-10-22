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

import it.chalmers.dat255_bearded_octo_lama.activities.NotificationActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/**
 * This is a abstract game implementation that holds all abstract logic for
 * running games. This includes the game thread, graphics and communication with UI thread.
 * @author Johan Gustafsson
 * @date 22 okt 2012
 */
public abstract class AbstractGameView extends SurfaceView implements Runnable {
	private Thread t;
	private Paint painter;
	private List<View> uiList;
	private final Context context;
	private boolean gameIsActive;
	private SurfaceHolder surfaceHolder;
	private AbstractGameView myself;
	private Handler uiHandler;
	/**
	 * Constructor for the AbstractGameView.
	 * @param context 
	 */
	public AbstractGameView(Context context) {
		super(context);
		
		this.context = context;
		initGameView();
	}
	
	private void initGameView() {
		myself        = this;
		surfaceHolder = getHolder();
		gameIsActive  = false;
		painter       = new Paint();
		uiList        = new ArrayList<View>();
		
		initHandler();
		setSurfaceSize();
	}

	private void initHandler() {
		//Since the thread run in the game wont be able to touch the views
		//or anything outside itself we are using a handler to relay a message
		//to the Android thread when the game is over and the GameView needs to be removed.
		uiHandler = new Handler() {
			@Override
            public void handleMessage(Message m) {
				while(true) {
					try {
						t.join();
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				NotificationActivity activity = (NotificationActivity) context;
				activity.endGame(myself);
            }
		};
	}
	
	
	/**
	 * Set the size of the surface view to match the parent.
	 */
	private void setSurfaceSize() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    setLayoutParams(params);
	}
	
	/**
	 * This method will initiate termination of the game and the view.
	 * Since the UI needs to be reconfigured a {@code Handler} will be used relay the message to the UI thread.
	 */
	protected void endGame() {
		gameIsActive = false;
		uiHandler.sendMessage(new Message());
	}
	
	/**
	 * The run method of the active game which contains the logic updates and graphical drawing.
	 * This will be continuously called while the game thread is running.
	 */
	public void run() {
		while(gameIsActive) {
			//Check that the surface is valid before trying to draw anything
			if(!surfaceHolder.getSurface().isValid()) {
				continue;
			}
			
			Canvas c = null;
			try {
				updateGame();
				c = surfaceHolder.lockCanvas(null);
				if(c != null) {
					updateGraphics(c);
				}
			} 
			//Have this code in the finally brackets in case an exception is thrown.
			//We don't want to leave the surface in an inconsistent state.
			finally {
				if(c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	
	/**
	 * This method will stop the game thread.
	 */
	public void pause() {
		gameIsActive = false;
		while(true) {
			try {
				t.join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}
	
	/**
	 * This will start the thread of the game if it's not currently running.
	 */
	public void resume() {
		if(!gameIsActive) { 
			gameIsActive = true;
			t = new Thread(this);
			t.start();
		}
	}
	
	/**
	 * Accessor method for subclasses to access the painter.
	 */
	protected Paint getPainter() {
		return painter;
	}
	
	/**
	 * Accessor method for subclasses to access the UI list.
	 */
	protected List<View> getUiList() {
		return uiList;
	}
	
	/**
	 * Calling this method will return all the UI views that needs to be added above the surface view.
	 * @return List of View's if the list is populated or <code>null</code> if there are no UI elements in the game.
	 */
	public List<View> getUIComponents() {
		return new ArrayList<View>(uiList);
	}
	
	/**
	 * All logic that needs to be continuously updated are placed in this method and
	 * will be called every loop of the game thread.
	 */
	protected abstract void updateGame();
	
	/**
	 * All graphics updates should be placed in this method and
	 * will be drawn every loop of the game thread.
	 */
	protected abstract void updateGraphics(Canvas c);
}
