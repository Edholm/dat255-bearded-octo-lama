package it.chalmers.dat255_bearded_octo_lama.activities;

import it.chalmers.dat255_bearded_octo_lama.AlarmController;
import it.chalmers.dat255_bearded_octo_lama.R;
import it.chalmers.dat255_bearded_octo_lama.utilities.RingtoneFinder;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

		List<String> allTones = RingtoneFinder.getRingtonesTitle(this);
		adapter = new RingtoneAdapter(this, R.layout.row_alarm, allTones, intent);

		ListView lv = (ListView)findViewById(R.id.listView);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
		intent = this.getIntent();
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
					selectedTitles.add(title);
				} else if(!checkbox.isChecked()){
					selectedTitles.remove(title);
				}
				intent.putStringArrayListExtra("pickedSongs", new ArrayList<String>(selectedTitles));
				
			}
		}
	}

}
