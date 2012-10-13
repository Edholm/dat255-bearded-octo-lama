package it.chalmers.dat255_bearded_octo_lama.test;

import it.chalmers.dat255_bearded_octo_lama.Alarm;
import android.database.MatrixCursor;
import android.test.AndroidTestCase;

public class TestAlarm extends AndroidTestCase {
	
	private final int hour = 10, minute = 11;
	private final long timeInMS = 101010L;
	private final int enabled = 1; // Enabled == true
	private MatrixCursor cursor;
	
	@Override
	public final void setUp() {
		// We need to test that the cursor adds the correct information to the correct place.
		// First, some mock-data.
		
		cursor = new MatrixCursor(Alarm.AlarmColumns.ALL_COLUMNS);
		// Note that the order must be the same as Alarm.AlarmColumns.ALL_COLUMNS,
		cursor.newRow()
				.add(0)        // _ID
				.add(hour)     // HOUR
				.add(minute)   // MINUTE
				.add(timeInMS) // TIME_IN_MS
				.add(enabled); // ENABLED
	
		// Ensure nothing wrong with cursor.
		assertEquals(Alarm.AlarmColumns.ALL_COLUMNS.length, cursor.getColumnCount());
		assertEquals(1, cursor.getCount());
		cursor.moveToFirst();
	}
	
	public final void testAlarm() {
		Alarm a = new Alarm(cursor);
		
		assertEquals(hour, a.getHour());
		assertEquals(minute, a.getMinute());
		assertEquals(timeInMS, a.getTimeInMS());
		assertEquals(enabled == 1, a.isEnabled());
	}
}
