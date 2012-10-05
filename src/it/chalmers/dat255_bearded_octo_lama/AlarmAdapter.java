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
package it.chalmers.dat255_bearded_octo_lama;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * This class adapts an {@code Alarm} for use in a {@code ListView}.
 * @author Emil Edholm
 * @date 5 okt 2012
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {
	
	private final List<Alarm> items;
	private final int         viewID;
	private final Context     context;
	
	/**
	 * @param context the context
	 * @param textViewResourceId id of the layout file for displaying each ListView item.
	 * @param items a list of all the items to adapt.
	 * @see AlarmController#getAllAlarms
	 */
	public AlarmAdapter(Context context, int textViewResourceId,
			List<Alarm> items) {
		super(context, textViewResourceId, items);
		
		this.context = context;
		this.viewID  = textViewResourceId;
		this.items   = new ArrayList<Alarm>(items);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AlarmHolder holder = null;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(viewID, parent, false);
            
            holder = new AlarmHolder();
            holder.clock  = (TextView)row.findViewById(R.id.clockTextView);
            holder.enabled = (CheckBox)row.findViewById(R.id.alarmEnabledCheckBox);
            
            row.setTag(holder);
        } else {
            holder = (AlarmHolder)row.getTag();
        }
        
        Alarm alarm = items.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        holder.clock.setText(sdf.format(new Date(alarm.getTimeInMS())));
        holder.enabled.setChecked(alarm.isEnabled());
        
        return row;
	}
	
	/**
	 * Utilizes the view holder pattern for updating a row.
	 */
	private static class AlarmHolder {
		private TextView clock;
		private CheckBox enabled;
	}
	
}
