package pt.lsts.neptusmobile;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

import pt.lsts.imc.EstimatedState;
import pt.lsts.imc.IMCMessage;
import pt.lsts.imc.PlanControlState;
import pt.lsts.imc.net.IMCProtocol;
import pt.lsts.neptus.messages.listener.MessageInfo;
import pt.lsts.neptus.messages.listener.MessageListener;
import pt.lsts.neptusmobile.data.VehicleInfoContract;
import pt.lsts.neptusmobile.imc.Sys;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AccuMainActivity extends FragmentActivity {
	public static final String NAME = "MainActivity";

	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;
	private ConcurrentHashMap<String, Sys> sysMarkers;
	private Sys selectedVehicle;

	// ImcManager imcManager;
	// Hook imcHook;
	@SuppressLint("HandlerLeak")
	private final Handler estStateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// Log.w(NAME, msg.toString());
			EstimatedState state = (EstimatedState) msg.obj;
			String sourceName = state.getSourceName();
			Sys system = sysMarkers.get(sourceName);
			if (system == null) {
				Marker marker = mMap.addMarker(new MarkerOptions()
						.position(new LatLng(0, 0)));
				system = new Sys(marker);
				sysMarkers.put(sourceName, system);
				Log.w(NAME, "Adding system " + sourceName);
			}
			system.update(state);
			if (selectedVehicle != null
					&& sourceName.equals(selectedVehicle.getName())) {
				updateLabels(system);
			}
			
			// Provider code
			// A "projection" defines the columns that will be returned for each row
			String[] mProjection =
			{
			    VehicleInfoContract.Maneuvers._ID,    // Contract class constant for the _ID column name
			    VehicleInfoContract.Maneuvers.IMC_ID_MAN_TYPE,   // Contract class constant for the IMC ID maneuver type
			    VehicleInfoContract.Maneuvers.ORDER  // Contract class constant for the order
			};
			// Defines a string to contain the selection clause
			String mSelectionClause = null;
			// Initializes an array to contain selection arguments
			String[] mSelectionArgs = {""};
			// Queries the vehicle info provider and returns results
			Cursor mCursor = getContentResolver().query(
			    VehicleInfoContract.MAN_CONTENT_URI,    // The content URI of the words table
			    mProjection,                        // The columns to return for each row
			    mSelectionClause,                    // Selection criteria
			    mSelectionArgs,                     // Selection criteria
			    VehicleInfoContract.Maneuvers.SORT_ORDERED_DEFAULT); // The sort order for the returned rows
		}
	};
	
	private final Handler planControlStateHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			PlanControlState planState = (PlanControlState) msg.obj;
			if(PlanControlState.STATE.EXECUTING == planState.getState()){
				// TODO
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		setContentView(R.layout.activity_main);
		// If we're being restored from a previous state,
		// then we don't need to do anytohing and should return or else
		// we could end up with overlapping fragments.
		if (savedInstanceState != null) {
			return;
		}

		setUpMapIfNeeded();

		IMCProtocol proto = new IMCProtocol(5000);
		proto.addMessageListener(
				new MessageListener<MessageInfo, IMCMessage>() {
					@Override
					public void onMessage(MessageInfo info, IMCMessage msg) {
						if (msg instanceof EstimatedState) {
							EstimatedState state = (EstimatedState) msg;
							estStateHandler.sendMessage(estStateHandler.obtainMessage(0,
									state));
							Log.w(NAME, "Got EstimatedState msg.");
						} else if (msg instanceof PlanControlState) {
							PlanControlState planState = (PlanControlState) msg;
							planControlStateHandler.sendMessage(planControlStateHandler.obtainMessage(0,
									planState));
							Log.w(NAME, "Got PlanControlState msg.");
						}
					}
				}, "EstimatedState", "PlanControlState");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_fragment)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// Padding for left hand info
		mMap.setPadding(150, 0, 0, 0);
		// Markers
		sysMarkers = new ConcurrentHashMap<String, Sys>();
		// Testing data
		fillTestSystems();
		// My location
		mMap.setMyLocationEnabled(true);
		// Camera
		// mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				Sys sys = sysMarkers.get(marker.getTitle());
				updateLabels(sys);
				// Update markers
				if (selectedVehicle != null) {
					selectedVehicle.setAsUnselectedVehicle();
				}
				sys.setAsSelectedVehicle();
				selectedVehicle = sys;
				return true;
			}

		});
	}

	private void updateLabels(Sys sys) {
		DecimalFormat df = new DecimalFormat("#.##");
		// Update text
		TextView textV = (TextView) findViewById(R.id.vehicle_name);
		textV.setText(sys.getName());
		textV = (TextView) findViewById(R.id.vehicle_height);
		textV.setText(df.format(sys.getHeight()) + "m");
		textV = (TextView) findViewById(R.id.vehicle_speed);
		textV.setText(df.format(sys.getSpeed()) + "m/s");
	}

	private void fillTestSystems() {
		// Test X8-00
		String title = "Test X8-00";
		LatLng coord = new LatLng(0, 0);
		int rotation = 90;
		float heightIn = 100f;
		float speedIn = 18.2f;
		Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(
				0, 0)));
		Sys sys = new Sys(marker);
		sys.fillTestData(title, coord, rotation, heightIn, speedIn);
		sysMarkers.put(title, sys);
		// Test X8-02
		title = "Test X8-02";
		coord = new LatLng(0, 20.5);
		rotation = 45;
		heightIn = 180f;
		speedIn = 15.3f;
		marker = mMap.addMarker(new MarkerOptions().position(coord));
		sys = new Sys(marker);
		sys.fillTestData(title, coord, rotation, heightIn, speedIn);
		sysMarkers.put(title, sys);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
