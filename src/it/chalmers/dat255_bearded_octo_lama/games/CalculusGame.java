package it.chalmers.dat255_bearded_octo_lama.games;

import java.util.Random;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

public class CalculusGame extends AbstractGameView {
	private String exerciseText;
	private int var1, var2;
	
	public CalculusGame(Context context, RelativeLayout parentView,
			LinearLayout btnHolder) {
		super(context, parentView, btnHolder);
		
		initUI();
		initGame();
	}
	
	private void initGame() {
		Random randomGenerator = new Random();
		
		var1 = randomGenerator.nextInt(100) + 1;
		var2 = randomGenerator.nextInt(100) + 1;
		
		exerciseText = "What is: " + var1 + " + " + var2;
	}
	
	private void initUI() {
		RelativeLayout uiHolder = new RelativeLayout(context);
		uiHolder.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		uiHolder.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		EditText answerTextField = new EditText(context);
		answerTextField.setBackgroundColor(getResources().getColor(R.color.black));
		answerTextField.setTextColor(getResources().getColor(R.color.white));
		answerTextField.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		answerTextField.setWidth(260);
		answerTextField.setHeight(60);
		answerTextField.setInputType(InputType.TYPE_CLASS_NUMBER);
		answerTextField.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int givenAnswer = Integer.parseInt(s.toString());
				if(givenAnswer == var1 + var2) {
					endGame();
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		uiHolder.addView(answerTextField);
		uiList.add(uiHolder);
	}

	@Override
	protected void updateGame() {
		//This game has no logic that needs to be continuously updated.
	}

	@Override
	protected void updateGraphics(Canvas c) {
		int textSize = 40;
		c.drawARGB(100, 51, 204, 255);
		painter.setTextSize(textSize);
		painter.setTextAlign(Align.CENTER);
		c.drawText(exerciseText, getWidth()/2, textSize*2, painter);
	}
}
