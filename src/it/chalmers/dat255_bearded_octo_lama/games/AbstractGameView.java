package it.chalmers.dat255_bearded_octo_lama.games;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public abstract class AbstractGameView extends SurfaceView implements Runnable {
	protected RelativeLayout parentView;
	protected LinearLayout btnHolder;
	protected Thread t;
	protected Paint painter;
	protected ArrayList<View> uiList;
	protected Context context;
	private SurfaceHolder holder;
	private boolean gameIsActive;
	private AbstractGameView myself;
	private Handler uiHandler;
	
	public AbstractGameView(Context context, RelativeLayout parentView, LinearLayout btnHolder) {
		super(context);
		
		myself = this;
		this.btnHolder = btnHolder;
		this.parentView = parentView;
		this.context = context;
		holder = getHolder();
		gameIsActive = false;
		painter = new Paint();
		uiList = new ArrayList<View>();
		
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
				//This will set the dismiss controls to visible again while removing the views used by the game.
				btnHolder.setVisibility(View.VISIBLE);
				parentView.removeView(myself);
				if(getUIComponents() != null) {
					for(View v : getUIComponents()) {
						parentView.removeView(v);
					}
				}
            }
		};
	}

	private void setSurfaceSize() {
		//Set size of the SurfaceView to fill the parent
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    setLayoutParams(params);
	}
	
	protected void endGame() {
		uiHandler.sendMessage(new Message());
	}
	
	public void run() {
		while(gameIsActive) {
			//Check that the surface is valid before trying to draw anything
			if(!holder.getSurface().isValid()) {
				continue;
			}
			
			Canvas c = null;
			try {
				updateGame();
				c = holder.lockCanvas(null);
				updateGraphics(c);
			} 
			//Have this code in the finally brackets in case an exception is thrown.
			//We don't want to leave the surface in an inconsistent state.
			finally {
				if(c != null) {
					holder.unlockCanvasAndPost(c);
				}
			}
		}
	}
	
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
	
	public void resume() {
		gameIsActive = true;
		t = new Thread(this);
		t.start();
	}
	
	public ArrayList<View> getUIComponents() {
		if(uiList != null) {
			return uiList;
		}
		return null;
	}

	protected abstract void updateGame();
	
	protected abstract void updateGraphics(Canvas c);

}
