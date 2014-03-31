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
	public static final Uri AUTHORITY_URI = Uri.parse("content://"
			+ AUTHORITY);

	// MIME type of a directory of items
	public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ AUTHORITY + "/";
	// MIME type of a single item
	public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ AUTHORITY + "/" ;
	
	
	public static final class Maneuvers implements BaseColumns {
		public static final String tableName = "MANEUVERS";
		public static final String CONTENT_TYPE_DIR = VehicleInfoContract.CONTENT_TYPE_DIR + tableName;
		public static final String CONTENT_TYPE_ITEM = VehicleInfoContract.CONTENT_TYPE_ITEM + tableName;
		public enum Columns{
			IMC_ID_MAN_TYPE, POSITION, ORDER;
		}
		// Default order for queries containing IMC_ID fields
		public static final String SORT_ORDERED_DEFAULT = "ORDER BY " + Columns.ORDER.name()
				+ " ASC";
	}

	public static final class Positions implements BaseColumns {
		public static final String tableName = "POSITIONS";
		public static final String CONTENT_TYPE_DIR = VehicleInfoContract.CONTENT_TYPE_DIR + tableName;
		public static final String CONTENT_TYPE_ITEM = VehicleInfoContract.CONTENT_TYPE_ITEM + tableName;
		public enum Columns{
			LATITUDE, LONGITUDE, TIMESTAMP, HEIGHT
		}
		// Default order for queries containing IMC_ID fields
		public static final String SORT_ORDERED_DEFAULT = "ORDER BY " + Columns.TIMESTAMP.name()
				+ " ASC";
	}

	public static final class Vehicle implements BaseColumns {
		public static final String tableName = "VEHICLES";
		public static final String CONTENT_TYPE_DIR = VehicleInfoContract.CONTENT_TYPE_DIR + tableName;
		public static final String CONTENT_TYPE_ITEM = VehicleInfoContract.CONTENT_TYPE_ITEM + tableName;
		public enum Columns{
			IMC_ID, POSITION_ID, PLAN_ID, SPEED, ENTITIES_STATE_ID, CURR_MAN_ID
		}
		// Default order for queries containing IMC_ID fields
		public static final String SORT_ORDERED_DEFAULT = "ORDER BY " + Columns.PLAN_ID.name()
				+ " ASC";
	}

}
