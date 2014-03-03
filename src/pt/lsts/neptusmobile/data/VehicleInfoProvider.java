package pt.lsts.neptusmobile.data;

import pt.lsts.neptusmobile.data.VehicleInfoContract.Maneuvers;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class VehicleInfoProvider extends ContentProvider {
	private static final String TAG = "VehicleInfoProvider";
	private static final String DATABASE_NAME = "vehicles.db";
	private static final int DATABASE_VERSION = 1;

	// helper constants for use with the UriMatcher
	private static final int VEHICLE_LIST = 1;
	private static final int VEHICLE_IMC_ID = 2;
	private static final int MANEUVER_LIST = 3;
	private static final int POSITION_LIST = 5;
	private static final int POSITION_ID = 6;
	private static final int PLAN_ID = 11;
	private static final UriMatcher URI_MATCHER;

	// prepare the UriMatcher
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "vehicles",
				VEHICLE_LIST);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "maneuver",
				MANEUVER_LIST);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "vehicles/#",
				VEHICLE_IMC_ID);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "positions",
				POSITION_LIST);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "positions/#",
				POSITION_ID);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "plan", PLAN_ID);
	}
	
	
	private VehicleInfoOpenHelper mHelper = null;


	@Override
	// deletes records
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	// Return the MIM type for this URI
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
			case VEHICLE_LIST:
				return Maneuvers.CONTENT_TYPE;
			case VEHICLE_IMC_ID:
				return Maneuvers.CONTENT_MANEUVER_TYPE;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
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
		mHelper = new LentItemsOpenHelper(getContext());
		return true;
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
