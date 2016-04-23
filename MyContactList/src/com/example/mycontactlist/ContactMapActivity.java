package com.example.mycontactlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactMapActivity extends FragmentActivity {
	
	GoogleMap gMap;
	SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    TextView textDirection;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_map);
		
		initLocationButton();
		initMapTypeButton();
		initListButton();
		initMapButton();
		initSettingsButton();
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		if (accelerometer != null && magnetometer != null) {
		    sensorManager.registerListener(mySensorEventListener, accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
		    sensorManager.registerListener(mySensorEventListener, magnetometer,SensorManager. SENSOR_DELAY_FASTEST);
		} else {
		    Toast.makeText(this, "Sensors not found",Toast.LENGTH_LONG).show();
		}
		
	    textDirection = (TextView) findViewById(R.id.textHeading);

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
		mapFragment.getMapAsync(new OnMapReadyCallback(){
			
			@Override
			public void onMapReady(GoogleMap map)
			{
				ContactMapActivity.this.gMap = map;
				gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			}
		});
        gMap = mapFragment.getMap();
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setTrafficEnabled(true);
        gMap.setMyLocationEnabled(true);
        gMap.setIndoorEnabled(true);
        gMap.setBuildingsEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        ArrayList<Contact> contacts = new ArrayList<Contact>();
		Contact currentContact = null;
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
		    ContactDataSource ds = new ContactDataSource(ContactMapActivity.this);
		    ds.open();
		    currentContact = ds.getSpecificContact(extras.getInt("contactid"));
		    ds.close();
		}
		else {
		    ContactDataSource ds = new ContactDataSource(ContactMapActivity.this);
		    ds.open();
		    contacts = ds.getContacts("contactname", "ASC");
		    ds.close();
		}

		int measuredWidth = 0;  
		int measuredHeight = 0;  
		Point size = new Point();
		WindowManager w = getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			w.getDefaultDisplay().getSize(size);
		    measuredWidth = size.x;
		    measuredHeight = size.y; 
		}else {
		    Display d = w.getDefaultDisplay(); 
		    measuredWidth = d.getWidth(); 
		    measuredHeight = d.getHeight()-180; 
		}
		LatLng currentLoc = null;
		if (contacts.size()>0) {
		    LatLngBounds.Builder builder = new LatLngBounds.Builder();
		    for (int i=0; i<contacts.size(); i++) {
		        currentContact = contacts.get(i);
						
		        Geocoder geo = new Geocoder(this);
		        List<Address> addresses = null;
						
		        String address = currentContact.getStreetAddress() + ", " +
		                            currentContact.getCity() + ", " +
		                            currentContact.getState() + " " + 
		                            currentContact.getZipCode();
			
		        try {
		            addresses = geo.getFromLocationName(address, 5);
		        	
		        } 
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        LatLng point = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
		        builder.include(point);
		        gMap.addMarker(new MarkerOptions().position(point).title(currentContact.getContactName()).snippet(address));
		        if(i == 0)
		        {
		        	currentLoc = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
		        }
		    }
		    gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),measuredWidth, measuredHeight, 80));
		    ///
		    try
		    {
		    LocationManager service1 = (LocationManager) ContactMapActivity.this.getSystemService(this.LOCATION_SERVICE);
		    Criteria criteria1 = new Criteria();
		    String provider1 = service1.getBestProvider(criteria1, false);
		    Location location1 = service1.getLastKnownLocation(provider1);
		    LatLng userLocation1 = new LatLng(location1.getLatitude(),location1.getLongitude());
		    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation1, 13));
		    }
		    catch(NullPointerException e)
		    {
		    	Log.w(null, "Location Manager was returned null. Skipping the part that gets current location.");
		    	gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 13));
		    }
		    ///
		}
		else {
		    if (currentContact != null) {
		        Geocoder geo = new Geocoder(this);
		        List<Address> addresses = null;
						
		        String address = currentContact.getStreetAddress() + ", " +
		                            currentContact.getCity() + ", " +
		                            currentContact.getState() + " " + 
		                            currentContact.getZipCode();
			
		        try {
		            addresses = geo.getFromLocationName(address, 1);
		        } 
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		        LatLng point = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
			
		        gMap.addMarker(new MarkerOptions().position(point).title(currentContact.getContactName()).snippet(address));
		        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
		    }	
		        else {
		            AlertDialog alertDialog = new AlertDialog.Builder(ContactMapActivity.this).create();
		            alertDialog.setTitle("No Data");
		            alertDialog.setMessage("No data is available for the mapping function.");
		            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            finish();
		        } });
		        alertDialog.show();
		    }
		}

	}

	private void initLocationButton() {
	    final Button locationbtn = (Button) findViewById(R.id.buttonShowMe);
	    locationbtn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            String currentSetting = locationbtn.getText().toString();
	            if (currentSetting.equalsIgnoreCase("Location On")) {
	                locationbtn.setText("Location Off");
	                gMap.setMyLocationEnabled(true);
	            }
	            else {
	                locationbtn.setText("Location On");
	                gMap.setMyLocationEnabled(false);
	            }   
	         }
	    }); 
	}  
	    
	private void initMapTypeButton() {
	    final Button satelitebtn = (Button) findViewById(R.id.buttonMapType);
	    satelitebtn.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            String currentSetting = satelitebtn.getText().toString();
	            if (currentSetting.equalsIgnoreCase("Satellite View")) {
	                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	                satelitebtn.setText("Normal View");
	            }
	            else {
	                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	                satelitebtn.setText("Satellite View");
	            }
	        }
	    }); 
	}  
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu){


    	return true;
    } 

    public void onPause() {
    	   super.onPause();
    	   finish();
    	}
    	    
    	@Override
    	public void onResume() {
    	    super.onResume();
    	    final String TAG_ERROR_DIALOG_FRAGMENT="errorDialog";

    	    int status=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

    	    if (status == ConnectionResult.SUCCESS) {
    		                  //no problems just work
    	    }
    	    else if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
    	        ErrorDialogFragment.newInstance(status).show(getSupportFragmentManager(),
    	                                            TAG_ERROR_DIALOG_FRAGMENT);
    	    }
    	    else {
    	        Toast.makeText(this, "Google Maps V2 is not available!", 
    	                             Toast.LENGTH_LONG).show();
    	        finish();
    	    }		
    	}
    		
    	public static class ErrorDialogFragment extends DialogFragment {
    	    static final String ARG_STATUS="status";

    	    static ErrorDialogFragment newInstance(int status) {
    	        Bundle args=new Bundle();
    	        args.putInt(ARG_STATUS, status);
    	        ErrorDialogFragment result=new ErrorDialogFragment();
    	        result.setArguments(args);
    	        return(result);
    	    }

    	    @Override
    	    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	        Bundle args=getArguments();
    	        return GooglePlayServicesUtil.getErrorDialog(args.getInt(ARG_STATUS),
    	                                                            getActivity(), 0);
    	    }

    	    @Override
    	    public void onDismiss(DialogInterface dlg) {
    	        if (getActivity() != null) {
    	            getActivity().finish();
    	        }
    	    }
    	}
    	private void initListButton() {
            ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
            list.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
        			Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class);
        			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
                }
            });
    	}
    	
    	private void initMapButton() {
            ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
            list.setEnabled(false);
    	}
    	
    	private void initSettingsButton() {
            ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
            list.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
        			Intent intent = new Intent(ContactMapActivity.this, ContactSettingsActivity.class);
        			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
                }
            });
    	}
    	
    	private SensorEventListener mySensorEventListener = new SensorEventListener() {

    	    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }
    	  
    	    float[] accelerometerValues;
    	    float[] magneticValues;
    	  
    	    public void onSensorChanged(SensorEvent event) {
    	    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
    	        accelerometerValues = event.values;
    	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
    	        magneticValues = event.values;
    	    if (accelerometerValues!= null && magneticValues!= null) {
    	        float R[] = new float[9];
    	        float I[] = new float[9];
    	        boolean success = SensorManager.getRotationMatrix(R, I,accelerometerValues, magneticValues);
    	        if (success) {
    	            float orientation[] = new float[3];
    	            SensorManager.getOrientation(R, orientation);
    	        
    	            float azimut = (float) Math.toDegrees(orientation[0]);
    	            if (azimut < 0.0f) { azimut+=360.0f;}
    	            String direction;
    	            if (azimut >= 315 || azimut < 45) { direction = "N"; }
    	            else if (azimut >= 225 && azimut < 315) { direction = "W"; }
    	            else if (azimut >= 135 && azimut < 225) { direction = "S"; }
    	            else { direction = "E"; }
    	            textDirection.setText(direction);	        
    	        }
    	       }
    	    }
    	};
}
