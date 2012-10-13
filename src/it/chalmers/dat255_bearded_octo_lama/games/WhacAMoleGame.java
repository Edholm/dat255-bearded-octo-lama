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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.chalmers.dat255_bearded_octo_lama.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class WhacAMoleGame extends AbstractGameView {
	private List<Integer>  btnsToHit;
	
	public WhacAMoleGame(Context context, LinearLayout dismissAlarmLayout) {
		super(context, dismissAlarmLayout);
		
		initUI();
		initGame();
	}

	private void initGame() {
		Random numberGen = new Random();
		btnsToHit = new ArrayList<Integer>();
		
		for(int i = 0; i<4; i++) {
			btnsToHit.add(numberGen.nextInt(9) + 1);
		}
		painter.setColor(getResources().getColor(R.color.red));
	}
	
	
	/**
	 * Since the method setBackground for buttons is API Level 16 we are using
	 * the older deprecated setBackgroundDrawable to make the buttons transparent.
	 */
	@SuppressWarnings("deprecation")
	private void initUI() {
		
		//Initiate ui components and add them to the view.
		LinearLayout uiLayout = new LinearLayout(context);
		
		uiLayout.setOrientation(LinearLayout.VERTICAL);
		uiLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		uiLayout.setWeightSum(3);
		
		//Using nested LinearLayout's instead of a GridLayout to make it work properly on low API Levels.
		LinearLayout horizontalLayout1 = new LinearLayout(context);
		horizontalLayout1.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT
				, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		horizontalLayout1.setWeightSum(3);
		LinearLayout horizontalLayout2 = new LinearLayout(context);
		horizontalLayout2.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT
				, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		horizontalLayout2.setWeightSum(3);
		LinearLayout horizontalLayout3 = new LinearLayout(context);
		horizontalLayout3.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT
				, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		horizontalLayout3.setWeightSum(3);
		
		uiLayout.addView(horizontalLayout1);
		uiLayout.addView(horizontalLayout2);
		uiLayout.addView(horizontalLayout3);
		
		OnClickListener btnListener = new OnClickListener() {
			
			public void onClick(View v) {
				onItemClick(v);
			}
		};
		
		//Here we add all the buttons that represent each colored square on the gameboard.
		int count = 1;
		for(int y = 1; y <= 3; y++) {
			for(int x = 1; x <= 3; x++) {
				Button btn = new Button(context);
				btn.setId(count);
				btn.setOnClickListener(btnListener);
				btn.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT
						, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
				//Using deprecated method instead of it's new equivalent since the new one requires API level 16.
				btn.setBackgroundDrawable(null);
				switch (y) {
				case 1:
					horizontalLayout1.addView(btn);
					break;
				case 2:
					horizontalLayout2.addView(btn);
					break;
				case 3:
					horizontalLayout3.addView(btn);
					break;
				}
				count++;
			}
		}
		
		uiList.add(uiLayout);
	}
	
	/**
	 * Method for handling all button clicks in this game.
	 * @param v The button which called the method.
	 */
	public void onItemClick(View v) {
		Toast.makeText(context, v.getId() + "", Toast.LENGTH_SHORT).show();
		btnsToHit.remove((Object)v.getId());
	}

	@Override
	protected void updateGame() {
		if(btnsToHit.isEmpty()) {
			endGame();
		}
	}

	@Override
	protected void updateGraphics(Canvas c) {
		//Draw graphics for the game.
		int currentWidth = getWidth()/3;
		int currentHeight = getHeight()/3;
		
		c.drawARGB(100, 61, 245, 0);
		
		for(Integer i : btnsToHit) {
			int x = (i-1)%3;
			int y = (i-1)/3;
			c.drawRect(new Rect(currentWidth*x, currentHeight*(y), currentWidth*(x+1), currentHeight*(y+1)), painter);
		}
	}
	
}
