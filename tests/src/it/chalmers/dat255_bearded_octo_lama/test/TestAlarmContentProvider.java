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
package it.chalmers.dat255_bearded_octo_lama.test;

import it.chalmers.dat255_bearded_octo_lama.Alarm;
import it.chalmers.dat255_bearded_octo_lama.AlarmContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.test.ProviderTestCase2;

/**
 * @author Emil Edholm
 * @date 22 okt 2012
 */
public class TestAlarmContentProvider extends
		ProviderTestCase2<AlarmContentProvider> {

	public TestAlarmContentProvider() {
		super(AlarmContentProvider.class, AlarmContentProvider.class.getName());
	}

	/**
	 * Test method for {@link it.chalmers.dat255_bearded_octo_lama.AlarmContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)}.
	 */
	public final void testQuery() {
		AlarmContentProvider cp = getProvider();
		ContentValues values = constructContentValues(10, 10, 1);
		insertRows(10, Alarm.Columns.CONTENT_URI, values);
		
		Uri uri = cp.insert(Alarm.Columns.CONTENT_URI, values);
		
		Cursor c = cp.query(uri, Alarm.Columns.ALL_COLUMN_NAMES, null, null, null);
		c.moveToFirst();
		
		int insertedID = Integer.parseInt(uri.getPathSegments().get(1));
		int fetchedID = c.getInt(Alarm.Columns.idOf(Alarm.Columns.ID));
		
		assertEquals(insertedID, fetchedID);
	}
	
	public final void testQueryWildcard() {
		AlarmContentProvider cp = getProvider();
		final int rowsInserted = 10;
		
		ContentValues enabledValues = constructContentValues(10, 20, 1);
		ContentValues disabledValues = constructContentValues(20, 10, 0);
		Uri uri = Alarm.Columns.CONTENT_URI;
		
		insertRows(rowsInserted, uri, enabledValues);
		insertRows(rowsInserted, uri, disabledValues);
		
		String selection = Alarm.Columns.ENABLED.getLeft() + "=?";
		String[] selectionArgs = new String[] {"1"};
		
		Cursor c = cp.query(uri, Alarm.Columns.ALL_COLUMN_NAMES, selection, selectionArgs, null);
		
		assertEquals(rowsInserted, c.getCount());
	}
	
	/** Tries to insert into a id, and should catch exception */
	public final void testInsertUriException() {
		AlarmContentProvider cp = getProvider();
		Uri uri = Alarm.Columns.CONTENT_URI.buildUpon().appendPath("10").build();
		
		// Test inserting with the wrong uri.
		Exception caught = null;
		try {
		   cp.insert(uri, new ContentValues());
		} catch (Exception e) {
		   caught = e;
		}
		assertNotNull(caught);
		assertSame(IllegalArgumentException.class, caught.getClass());
	}
	
	/** Test inserting wrong values into the db and expect SQLException  */
	public final void testInsertFailException() {
		AlarmContentProvider cp = getProvider();
		ContentValues cv = new ContentValues();
		cv.put("Non-existing_column", "bepa");
		
		// Test inserting with the expectation of failure.
		Exception caught = null;
		try {
		   cp.insert(Alarm.Columns.CONTENT_URI, cv);
		} catch (Exception e) {
		   caught = e;
		}
		
		assertNotNull(caught);
		assertSame(SQLException.class, caught.getClass());
	}

	/**
	 * Test method for {@link it.chalmers.dat255_bearded_octo_lama.AlarmContentProvider#insert(android.net.Uri, android.content.ContentValues)}.
	 */
	public final void testInsert() {
		AlarmContentProvider cp = getProvider();
		Uri uri = Alarm.Columns.CONTENT_URI;
		
		ContentValues values = constructContentValues(10, 20, 0);
		
		Exception caught = null;
		try {
			uri = cp.insert(uri, values);
		} catch(Exception e) {
			caught = e;
		}
		
		assertNull(caught);
		assertNotNull(uri);
	}

	/**
	 * Test method for {@link it.chalmers.dat255_bearded_octo_lama.AlarmContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])}.
	 */
	public final void testUpdateSingleAlarm() {
		AlarmContentProvider cp = getProvider();
		// First insert so we have something to update.
		ContentValues cv = constructContentValues(10, 20, 1);
		
		Uri alarmUri = cp.insert(Alarm.Columns.CONTENT_URI, cv);
		
		cv = constructContentValues(20, 10, 0);
		
		int rowsAffected = cp.update(alarmUri, cv, null, null);
		assertEquals(1, rowsAffected);
	}
	
	/** Test wildcard update */
	public final void testUpdateAll() {
		AlarmContentProvider cp = getProvider();
		Uri uri = Alarm.Columns.CONTENT_URI;
		ContentValues values = constructContentValues(0, 0, 1);
		final int numInserts = 12;
		insertRows(numInserts, uri, values);
		
		// Add an odd alarm out.
		cp.insert(uri, constructContentValues(10, 20, 0));
		
		values = constructContentValues(10, 20, 0);
		String enabledColName = Alarm.Columns.ENABLED.getLeft();
		int affectedRows = cp.update(uri, values, enabledColName + " = ?" , new String[]{"1"});
		
		assertEquals(numInserts, affectedRows);
	}

	/**
	 * Test method for {@link it.chalmers.dat255_bearded_octo_lama.AlarmContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])}.
	 */
	public final void testDelete() {
		AlarmContentProvider cp = getProvider();
		Uri uri = Alarm.Columns.CONTENT_URI;
		
		ContentValues values = constructContentValues(0, 0, 0);
		uri = cp.insert(uri, values);
		
		int affectedRows = cp.delete(uri, null, null);
		
		assertEquals(1, affectedRows);
	}
	
	/** Test row deletions using a wildcard */
	public final void testDeleteWildcard() {
		AlarmContentProvider cp = getProvider();
		Uri uri = Alarm.Columns.CONTENT_URI;
		final int numInserts = 12;
		insertRows(numInserts, uri, constructContentValues(0, 0, 1));
		insertRows(numInserts, uri, constructContentValues(0, 0, 0));
		
		// Now we have numInserts of enabled alarms and numInserts of disabled alarms.
		
		String selection = Alarm.Columns.ENABLED.getLeft() + "=?";
		int deletedRows = cp.delete(uri, selection, new String[] {"1"});
		assertEquals(numInserts, deletedRows);
	}
	
	private ContentValues constructContentValues(int hour, int min, int enabled) {
		ContentValues cv = new ContentValues();
		cv.put(Alarm.Columns.HOUR.getLeft(), hour);
		cv.put(Alarm.Columns.MINUTE.getLeft(), min);
		cv.put(Alarm.Columns.ENABLED.getLeft(), enabled);
		
		return cv;
	}
	
	private void insertRows(final int count, Uri uri, ContentValues values) {
		AlarmContentProvider cp = getProvider();

		for(int i = 0; i == count; i++) {
			cp.insert(uri, values);
		}
	}
}
