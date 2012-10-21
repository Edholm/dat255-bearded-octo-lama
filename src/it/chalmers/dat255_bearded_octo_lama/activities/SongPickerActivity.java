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
package it.chalmers.dat255_bearded_octo_lama.activities;

import it.chalmers.dat255_bearded_octo_lama.AlarmController;
import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.utilities.RingtoneFinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SongPickerActivity extends AbstractActivity {


	private RingtoneAdapter adapter;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_alarms);
		intent = getIntent();
		List<String> allTones = RingtoneFinder.getRingtonesTitle(this);
		adapter = new RingtoneAdapter(this, R.layout.row_ringtone, allTones, intent);

		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}


	/**
	 * This class adapts an Rington for use in a {@code ListView}.
	 * @author Emil Edholm
	 * @modified by Emil Johansson
	 * @date 21 okt 2012
	 */
	private static class RingtoneAdapter extends ArrayAdapter<String> {

		private final List<String>    titles;
		private final List<String> selectedTitles = new ArrayList<String>();
		private final int            viewID;
		private final LayoutInflater inflater;
		private SongCheckboxListener listener;
		private final Intent intent;
		
		/**
		 * @param context the context
		 * @param textViewResourceId id of the layout file for displaying each ListView item.
		 * @param items a list of all the items to adapt.
		 * @see AlarmController#getAllAlarms
		 */
		public RingtoneAdapter(Context context, int textViewResourceId, List<String> titles, Intent intent) {
			super(context, textViewResourceId, titles);

			this.viewID  = textViewResourceId;
			this.titles   = titles;
			this.inflater = LayoutInflater.from(context);
			this.intent = intent;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			RingtoneHolder holder = null;

			if(row == null) {
				row = inflater.inflate(viewID, parent, false);

				holder = new RingtoneHolder();
				holder.title  = (TextView)row.findViewById(R.id.titleTextView);
				holder.enabled = (CheckBox)row.findViewById(R.id.ringtoneEnabledCheckBox);

				row.setTag(holder);
			} else {
				holder = (RingtoneHolder)row.getTag();
			}

			String title = titles.get(position);
			holder.title.setText(title);
			holder.enabled.setChecked(false);

			// Make the checkbox change listenable.
			holder.enabled.setTag(title);
			listener = new SongCheckboxListener(holder.enabled, selectedTitles, intent);
			holder.enabled.setOnClickListener(listener);

			
			return row;
		}

		/**
		 * Utilizes the view holder pattern for updating a row.
		 */
		private static class RingtoneHolder {
			private TextView title;
			private CheckBox enabled;
		}
		
		private class SongCheckboxListener implements OnClickListener {
			private final List<String> selectedTitles;
			private final CheckBox checkbox;
			private final Intent intent;
			
			public SongCheckboxListener(CheckBox checkbox, List<String> selectedTitles, Intent intent){
				this.checkbox = checkbox;
				this.selectedTitles = selectedTitles;
				this.intent = intent;
			}
			
			public void onClick(View v) {
				String title = (String)v.getTag();
				if(checkbox.isChecked()){
					if(title == null){
						Log.d("SongPickerActivity", "title is null");
					}
					selectedTitles.add(title);
				} else if(!checkbox.isChecked()){
					selectedTitles.remove(title);
				}
				if(intent == null){
					Log.d("SongPickerActivity", "intent is null");
				} else if(selectedTitles == null){
					Log.d("SongPickerActivity", "selectedtitles is null");
				}
				if(selectedTitles.isEmpty()){
					Log.d("SongPickerActivity", "Empty list");
				}
				intent.putStringArrayListExtra("pickedSongs", new ArrayList<String>(selectedTitles));
				
			}
		}
	}

}
