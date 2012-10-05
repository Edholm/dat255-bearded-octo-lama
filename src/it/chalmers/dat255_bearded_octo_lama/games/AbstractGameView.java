package it.chalmers.dat255_bearded_octo_lama.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class AbstractGameView extends SurfaceView implements Runnable {
	protected LinearLayout parentView;
	protected Thread t;
	private SurfaceHolder holder;
	private boolean gameIsActive;
	protected Paint painter;
	
	public AbstractGameView(Context context, LinearLayout parentView) {
		super(context);
		
		this.parentView = parentView;
		holder = getHolder();
		gameIsActive = false;
		painter = new Paint();
		
		setSurfaceSize();
	}

	private void setSurfaceSize() {
		//Set size of the SurfaceView to fill the parent
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    setLayoutParams(params);
	}
	
	protected void endGame() {
		//TODO: Fix a working endGame implementation
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
	
	protected abstract void updateGame();
	
	protected abstract void updateGraphics(Canvas c);

}
