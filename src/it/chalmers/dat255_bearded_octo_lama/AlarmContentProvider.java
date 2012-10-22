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

import it.chalmers.dat255_bearded_octo_lama.utilities.Tuple;

import java.util.Iterator;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

/**
 * Handles the contact with the database.
 * @author Emil Edholm
 * @date 1 okt 2012
 */
public final class AlarmContentProvider extends ContentProvider{

    private DatabaseHelper      dbHelper;
    private static final String DATABASE_NAME    = "alarms.db";
    private static final int    DATABASE_VERSION = 12;
    private static final String TABLE_NAME       = "Alarms";
    
    // For use in matching uri.
    private static final int ALARMS = 1;
    private static final int ALARMS_ID = 2;
    private static final UriMatcher alarmUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
    	alarmUriMatcher.addURI("it.chalmers.dat255-bearded-octo-lama", "alarm", ALARMS);
    	alarmUriMatcher.addURI("it.chalmers.dat255-bearded-octo-lama", "alarm/#", ALARMS_ID);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(buildCreateSQL());
        }
        
        private String buildCreateSQL() {
        	// Dynamically builds the SQL for creating the database using
        	// the columns defined in Alarms.Columns.
            List<Tuple<String, String>> columns = Alarm.Columns.ALL_COLUMNS;
            String idOptions = "PRIMARY KEY AUTOINCREMENT";
            
            StringBuilder sb = new StringBuilder();
            sb.append("Create table " + TABLE_NAME + " (");
            
            Iterator<Tuple<String, String>> it = columns.iterator();
            Tuple<String, String> col = null;
            while(it.hasNext()) {
            	col = it.next();
            	sb.append(col.getLeft() + " ");
            	sb.append(col.getRight());
            	
            	if(col.getLeft() == BaseColumns._ID) {
            		sb.append(" " + idOptions);
            	}
            	
            	if(it.hasNext()) {
            		sb.append(", ");
            	}
            }
            sb.append(");");
            
            return sb.toString();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	Log.d("AlarmContentProvider", "Updating database from " + oldVersion + " to " + newVersion);
        	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
	
	@Override
	public boolean onCreate() {
		// Create or update the database.
		dbHelper = new DatabaseHelper(getContext());
		return dbHelper == null ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
        // Decide whether or not to query all alarms or a single id.
        int matchID = alarmUriMatcher.match(uri);
        switch(matchID){
        	case ALARMS:
        		qb.setTables(TABLE_NAME);
        		break;
        	case ALARMS_ID:
        		qb.setTables(TABLE_NAME);
                qb.appendWhere("_id=");
                qb.appendWhere(uri.getPathSegments().get(1)); // Get id from end of uri and add to query builder.
                break;
            default:
            	throw new IllegalArgumentException("Unrecognizable uri: " + uri);
        }
        
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        // TODO: Handle failure from the query. (c == null)

        return c;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (alarmUriMatcher.match(uri) != ALARMS) {
            throw new IllegalArgumentException("Cannot insert into URI: " + uri);
        }
		
		// get database to insert records
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        long rowId = db.insert(TABLE_NAME, "", values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.withAppendedId(Alarm.Columns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        
        throw new SQLException("Failed to insert row into " + uri);
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		
        int matchID = alarmUriMatcher.match(uri);
        switch(matchID){
        	case ALARMS:
        		count = db.update(TABLE_NAME, values, selection, selectionArgs);
        		break;
        	case ALARMS_ID:
        		// Get the ID.
        		String id = uri.getPathSegments().get(1);
        		String where = BaseColumns._ID + "=" + id;
        		
        		count = db.update(TABLE_NAME, values, where, selectionArgs);
                break;
            default:
            	throw new IllegalArgumentException("Unrecognizable uri: " + uri);
        }
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		
        int matchID = alarmUriMatcher.match(uri);
        switch(matchID){
        	case ALARMS:
        		count = db.delete(TABLE_NAME, selection, selectionArgs);
        		break;
        	case ALARMS_ID:
        		// Get the ID.
        		String id = uri.getPathSegments().get(1);
        		String where = BaseColumns._ID + "=" + id + 
        				(!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
        		
        		count = db.delete(TABLE_NAME, where, selectionArgs);
                break;
            default:
            	throw new IllegalArgumentException("Unrecognizable uri: " + uri);
        }
        
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	@Override
	public String getType(Uri uri) { return null; }
}
