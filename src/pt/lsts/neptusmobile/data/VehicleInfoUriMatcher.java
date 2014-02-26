package pt.lsts.neptusmobile.data;

import android.content.UriMatcher;

public class VehicleInfoUriMatcher {
	// helper constants for use with the UriMatcher
	private static final int VEHICLE_LIST = 1;
	private static final int VEHICLE_IMC_ID = 2;
	private static final int POSITION_LIST = 5;
	private static final int POSITION_ID = 6;
	private static final int PLAN_ID = 11;
	private static final UriMatcher URI_MATCHER;

	// prepare the UriMatcher
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "vehicles", VEHICLE_LIST);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "vehicles/#", VEHICLE_IMC_ID);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "positions", POSITION_LIST);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "positions/#", POSITION_ID);
		URI_MATCHER.addURI(VehicleInfoContract.AUTHORITY, "plan", PLAN_ID);
	}
}
