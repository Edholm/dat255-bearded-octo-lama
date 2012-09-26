package it.chalmers.dat255_bearded_octo_lama.utilities;

import it.chalmers.dat255_bearded_octo_lama.AlarmReceiver;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmSetterFragment extends DialogFragment implements OnTimeSetListener {
	private Context con;
	
	public AlarmSetterFragment(Context context) {
		con = context;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		
		return new TimePickerDialog(getActivity(), this, hour, minutes, true);
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// This will actually be called twice because of a bug in the Jelly Bean SDK. Hopefully this will be fixed soon.
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		
		AlarmManager am = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pIntent = PendingIntent.getBroadcast(view.getContext(),
                0, new Intent(view.getContext(), AlarmReceiver.class), 0);
		
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
		
		Toast.makeText(getActivity(), "Alarm set at:" + hourOfDay + minute, Toast.LENGTH_LONG).show();
	}
}
