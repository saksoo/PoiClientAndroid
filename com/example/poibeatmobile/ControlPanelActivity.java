package com.example.poibeatmobile;

import java.util.ArrayList;

import listeners.EntertainmentSetListener;
import listeners.SetListener;
import poiservicesclient.Application;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ControlPanelActivity extends Activity {
	public TabHost tabhost;
	public ControlPanelActivity a;
	public TextView textViewUsername;
	
	public Button buttonLogout;
	// ------------------------- TAB 1
	public Button buttonSet;
	public EditText nameSet;
	public RadioButton buttonEducationSet;
	public RadioButton buttonEntertainmentSet;
	public RadioButton buttonFoodSet;
	
	// ------------------------- TAB 2	
	public Button buttonGet;
	public RadioButton buttonEducationGet;
	public RadioButton buttonEntertainmentGet;
	public RadioButton buttonFoodGet;
	public Spinner spinnerSet;
	public Spinner spinnerGet;
	
	// ------------------------- TAB 3	
	public Button buttonUpdateMyPOI;
	public ListView listViewMyPOI;
	public ArrayAdapter<String> mypoiAdapter;
	
	public Application application;
	
	public final String[] entertainmentArray = new String[] { "Drink", "Cinema", "Site seeing" };
	public final String[] educationArray = new String[] { "Library", "University" };
	public final String[] foodArray = new String[] { "Fast food restaurant", "Take away restaurant", "Typical restaurant" };
	public ArrayAdapter<String> entertainmentAdapter;
	public ArrayAdapter<String> educationAdapter;
	public ArrayAdapter<String> foodAdapter;
	
	public LocationManager locationManager;
	public Criteria criteria;
	public String bestProvider;
	public Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);
		a= this;
		application = (Application)getApplication();
		
		tabhost = (TabHost) findViewById(R.id.tabHostControlPanel);
		tabhost.setup();
		tabhost.newTabSpec("tab_creation2");
		tabhost.addTab(tabhost.newTabSpec("tab_set_spec").setIndicator("Commit",getResources().getDrawable(android.R.drawable.ic_menu_add)).setContent(R.id.tabControlPanel1));		 
		tabhost.addTab(tabhost.newTabSpec("tab_get_spec").setIndicator("Search",getResources().getDrawable(android.R.drawable.ic_menu_add)).setContent(R.id.tabControlPanel2));
		tabhost.addTab(tabhost.newTabSpec("tab_map_spec").setIndicator("My POI",getResources().getDrawable(android.R.drawable.ic_menu_add)).setContent(R.id.tabControlPanel3));

		textViewUsername = (TextView) findViewById(R.id.textViewUsername);
		
		textViewUsername.setText("You are:" + application.username) ;
		
		spinnerSet =  (Spinner) findViewById(R.id.spinnerSubtypesSet);
		spinnerGet =  (Spinner) findViewById(R.id.spinnerSubtypesGet);
		buttonSet =  (Button) findViewById(R.id.buttonCommitPOI);
		nameSet = (EditText) findViewById(R.id.editNameSet);
		buttonEducationSet =(RadioButton) findViewById(R.id.radioEducationSet);
		buttonEntertainmentSet =(RadioButton) findViewById(R.id.radioEntertainmentSet);
		buttonFoodSet =(RadioButton) findViewById(R.id.radioFoodSet);
		
		buttonGet =  (Button) findViewById(R.id.buttonSearchPOI);
		buttonEducationGet =(RadioButton) findViewById(R.id.radioEducationGet);
		buttonEntertainmentGet =(RadioButton) findViewById(R.id.radioEntertainmentGet);
		buttonFoodGet =(RadioButton) findViewById(R.id.radioFoodGet);

		buttonUpdateMyPOI = (Button) findViewById(R.id.buttonUpdateMyPOI);
		listViewMyPOI = (ListView) findViewById(R.id.listViewMyPOI);
		
		buttonLogout =  (Button) findViewById(R.id.buttonLogout);
		
		entertainmentAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, entertainmentArray);
		educationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, educationArray);
		foodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, foodArray);
		mypoiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		spinnerSet.setAdapter(educationAdapter);
		spinnerGet.setAdapter(educationAdapter);
		listViewMyPOI.setAdapter(mypoiAdapter);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    criteria = new Criteria();
	    bestProvider = locationManager.getBestProvider(criteria, false);
	    location = locationManager.getLastKnownLocation(bestProvider);
		
	    
		buttonEntertainmentSet.setOnClickListener(new EntertainmentSetListener(this));
		
		buttonEducationSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinnerSet.setAdapter(educationAdapter);			
			}
		});
		
		buttonFoodSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinnerSet.setAdapter(foodAdapter);				
			}
		});
		
		buttonEntertainmentGet.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				spinnerGet.setAdapter(entertainmentAdapter);
			}
		});
		
		buttonEducationGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinnerGet.setAdapter(educationAdapter);			
			}
		});
		
		buttonFoodGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinnerGet.setAdapter(foodAdapter);				
			}
		});
		
		buttonUpdateMyPOI.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mypoiAdapter.clear();
				ArrayList<String> temp = application.database.SelectAllPOI();
				mypoiAdapter.addAll(temp);
			}
		});
		
		buttonLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				application.logout();
				Intent intent = new Intent(a, HomeActivity.class);
				a.startActivity(intent);
				
				//startService(new Intent(ControlPanelActivity.this, SpyService.class));
			}
		});
		
		
		listViewMyPOI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String s = listViewMyPOI.getItemAtPosition(position).toString();
				Context context = view.getContext();
				Intent intent = new Intent(context, PoiDisplayActivity.class);
				intent.putExtra("POI", s);
				context.startActivity(intent);
			}
		});
		
		buttonSet.setOnClickListener(new SetListener(this));
		buttonGet.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				final String type = spinnerGet.getItemAtPosition(spinnerGet.getSelectedItemPosition()).toString();
				final int x = (int)location.getLatitude();
				//final int x = 10;
				final int y = (int)location.getLongitude();
				//final int y = 10;
				
				final Context context = v.getContext();
				
				Thread t = new Thread(new Runnable() {					
					@Override
					public void run() {
						final ArrayList<String> data = application.getMapData(type, x, y);  // filtro ana subtype
						
						runOnUiThread(new Runnable() {							
							@Override
							public void run() {
								Toast.makeText(context, data.toString(),  Toast.LENGTH_LONG).show();
							}
						});
					}
				});
				t.start();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control_panel, menu);
		return true;
	}
}
