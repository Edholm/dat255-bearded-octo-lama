package it.chalmers.dat255_bearded_octo_lama.games;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WhacAMoleGame extends AbstractGameView {

	public WhacAMoleGame(Context context, RelativeLayout parentView,
			LinearLayout btnHolder) {
		super(context, parentView, btnHolder);
		// TODO Auto-generated constructor stub
		
		initUI();
	}

	private void initUI() {
		//Initiate ui components and add them to the view.
		LinearLayout verticalLayout = new LinearLayout(context);
		verticalLayout.setOrientation(LinearLayout.VERTICAL);
		verticalLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		LinearLayout horizontalLayout1 = new LinearLayout(context);
		LinearLayout horizontalLayout2 = new LinearLayout(context);
		LinearLayout horizontalLayout3 = new LinearLayout(context);
		
		verticalLayout.addView(horizontalLayout1);
		verticalLayout.addView(horizontalLayout2);
		verticalLayout.addView(horizontalLayout3);
		
		OnClickListener btnListener = new OnClickListener() {
			
			public void onClick(View v) {
				btnWasClicked(v.getId());
			}
		};
		
		int count = 1;
		for(int y = 1; y <= 3; y++) {
			for(int x = 1; x <= 3; x++) {
				Button btn = new Button(context);
				btn.setId(count);
				btn.setOnClickListener(btnListener);
				count++;
			}
		}
		
		uiList.add(verticalLayout);
	}
	
	private void btnWasClicked(int id) {
		//TODO: Add implementation
	}

	@Override
	protected void updateGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateGraphics(Canvas c) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
