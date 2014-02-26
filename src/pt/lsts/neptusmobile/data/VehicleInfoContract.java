package pt.lsts.neptusmobile.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class VehicleInfoContract {

		/**
		 * The authority of the lentitems provider.
		 */
		public static final String AUTHORITY = 
		      "pt.triton.vehicleinfo";
		/**
		 * The content URI for the top-level 
		 * lentitems authority.
		 */
		public static final Uri CONTENT_URI = 
		      Uri.parse("content://" + AUTHORITY);
		
		public static final class Vehicle implements CommonColumns{
			public static final Uri CONTENT_URI = Uri.withAppendedPath(VehicleInfoContract.CONTENT_URI, "vehicles");
			// MIME type of a directory of items
			public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "pt.triton.vehicleinfo_vehicles";
			// MIME type of a single item
			public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "pt.triton.vehicleinfo_vehicle";
			// Projection of all the columns in the vehicles table
			public static final String[] PROJECTION_ALL = {IMC_ID, POSITION_ID, PLAN_IMC_ID, SPEED, HEIGHT, ENTITIES_STATE};
			// Default order for queries containing IMC_ID fields
			public static final String SORT_ORDERED_DEFAULT = IMC_ID + " ASC";
		}
		
		/**
		 * Constants for the Photos table of the 
		 * lentitems provider. For each item there 
		 * is exactly one photo. You can 
		 * safely call insert with the an already 
		 * existing ITEMS_ID. You won't get constraint 
		 * violations. The content provider takes care 
		 * of this.<br> 
		 * Note: The _ID of the new record in this case
		 * differs from the _ID of the old record.
		 */
		public static final class Photos 
		      implements BaseColumns { ... }

		/**
		 * Constants for a joined view of Items and 
		 * Photos. The _id of this joined view is 
		 * the _id of the Items table.
		 */
		public static final class ItemEntities 
		      implements CommonColumns { ...}
		
		/**
		 * This interface defines common columns 
		 * found in multiple tables.
		 */
		public static interface CommonColumns 
		      extends BaseColumns { ... }
}
