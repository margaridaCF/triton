package pt.lsts.neptusmobile.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class VehicleInfoContract {

	/**
	 * The authority of the vehicles info provider.
	 */
	public static final String AUTHORITY = "pt.triton.vehicleinfo";
	/**
	 * The content URI for the top-level vehicles info authority.
	 */
	public static final Uri MAN_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY);

	public static final class Maneuvers implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(
				VehicleInfoContract.MAN_CONTENT_URI, "maneuver");
		// MIME type of a directory of items
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
				+ "/maneuvers";
		// MIME type of a single item
		public static final String CONTENT_MANEUVER_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
				+ "/maneuver";
		// Column names
		public static final String IMC_ID_MAN_TYPE = "IMC_ID_MAN_TYPE";
		public static final String ORDER = "ORDER";
		public static final String POSITION = "POSITION";
		// Projection of all the columns in the vehicles table
		public static final String[] PROJECTION_ALL = { _ID,
				IMC_ID_MAN_TYPE, POSITION, ORDER };
		// Default order for queries containing IMC_ID fields
		public static final String SORT_ORDERED_DEFAULT = "ORDER BY " + ORDER
				+ " ASC";
	}

	public static final class Positions implements BaseColumns {

	}

	public static final class Vehicle implements BaseColumns {

	}

}
