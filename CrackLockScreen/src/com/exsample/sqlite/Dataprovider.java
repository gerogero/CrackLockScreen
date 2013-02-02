package com.exsample.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class Dataprovider extends ContentProvider {

	DatabaseHelper dbh;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db=dbh.getWritableDatabase();
		db.delete("eventTable", selection, selectionArgs);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db=dbh.getWritableDatabase();
		db.insert("eventTable", null, values);
	    return null;
	}

	@Override
	public boolean onCreate() {
		dbh=new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db=dbh.getReadableDatabase();
		SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
		qb.setTables("eventTable");
		
		Cursor c=qb.query(db, projection, selection, selectionArgs,
				null, null, null);
		
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
