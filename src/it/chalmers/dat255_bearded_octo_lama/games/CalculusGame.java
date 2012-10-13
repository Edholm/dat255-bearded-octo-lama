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

import java.util.Random;

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
	
	public CalculusGame(Context context, LinearLayout dismissAlarmLayout) {
		super(context, dismissAlarmLayout);
		
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
		
		//Adding a custom TextWatcher to handle the input as we want it to.
		answerTextField.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int givenAnswer = Integer.parseInt(s.toString());
				if(givenAnswer == var1 + var2) {
					endGame();
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
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
