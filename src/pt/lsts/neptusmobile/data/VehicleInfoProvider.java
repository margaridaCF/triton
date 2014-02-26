package pt.lsts.neptusmobile.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class VehicleInfoProvider extends ContentProvider {

	@Override
	// deletes records
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	// Return the MIM type for this URI
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// Add records
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// Prepare the content provider
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	// Returns records based on selection criteria
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// Modifies data
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
