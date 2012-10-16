package it.chalmers.dat255_bearded_octo_lama.test;

import it.chalmers.dat255_bearded_octo_lama.Alarm;

import java.util.List;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.test.AndroidTestCase;

public class TestAlarm extends AndroidTestCase {
	
	private final int hour = 10, minute = 11;
	private final long timeInMS = 101010L;
	private final int enabled = 1; // Enabled == true
	private final int useSound = 1, useVibration = 0;
	private final int gameNot = 0;
	private final String gameName = "apa";
	private String ringtoneID = "bepa";
	private Cursor cursor;
	
	@Override
	public final void setUp() {
		// We need to test that the cursor adds the correct information to the correct place.
		// First, some mock-data.
		
		this.cursor = constructMockupCursor();
	}
	
	private final Cursor constructMockupCursor() {
		MatrixCursor cursor = new MatrixCursor(Alarm.Columns.ALL_COLUMNS);
		// Note that the order must be the same as Alarm.AlarmColumns.ALL_COLUMNS,
		cursor.newRow()
				.add(0)         // _ID
				.add(hour)      // HOUR
				.add(minute)    // MINUTE
				.add(timeInMS)  // TIME_IN_MS
				.add(enabled)   // ENABLED
			    .add(useSound)  // SOUND_NOTIFICATION
			    .add(useVibration) // VIBRATION_NOTIFICATION
			    .add(ringtoneID)   // RINGTONE
			    .add(gameNot)      // GAME_NOTIFICATION
			    .add(gameName);    // GAME_NAME
	
		cursor.moveToFirst();
		
		return cursor;
	}
	
	public final void testProperSetup() {
		// Ensure nothing wrong with cursor.
		assertEquals(Alarm.Columns.ALL_COLUMNS.length, cursor.getColumnCount());
		assertEquals(1, cursor.getCount());
		assertNotNull(cursor);
	}
	
	public final void testAlarm() {
		Alarm a = new Alarm(cursor);
		
		assertEquals(0, a.getId());
		assertEquals(hour, a.getHour());
		assertEquals(minute, a.getMinute());
		assertEquals(timeInMS, a.getTimeInMS());
		assertEquals(enabled != 0, a.isEnabled());
		
		Alarm.Extras e = a.getExtras();
		assertEquals(useSound != 0, e.hasSoundNotification());
		assertEquals(useVibration != 0, e.hasVibrationNotification());
		assertEquals(gameNot != 0, e.hasGameNotification());
		assertEquals(gameName, e.getGameName());
		
		assertNotNull(e.getRingtoneIDs());
		assertEquals(0, e.getRingtoneIDs().size());
	}
	
	public final void testRingtoneStorage() {
		ringtoneID = "1,2,3";
		Alarm a1 = new Alarm(constructMockupCursor());
		
		ringtoneID = "1, 2, 3";
		Alarm a2 = new Alarm(constructMockupCursor());
		
		List<Integer> ringtones1 = a1.getExtras().getRingtoneIDs();
		List<Integer> ringtones2 = a2.getExtras().getRingtoneIDs();
		
		// Check list size and content
		assertEquals(3, ringtones1.size());
		assertEquals(3, ringtones2.size());
		
		assertTrue(ringtones1.contains(1));
		assertTrue(ringtones1.contains(2));
		assertTrue(ringtones1.contains(3));
		
		assertTrue(ringtones2.contains(1));
		assertTrue(ringtones2.contains(2));
		assertTrue(ringtones2.contains(3));
	}
}
