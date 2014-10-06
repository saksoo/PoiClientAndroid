package com.example.poibeatmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class PoiDisplayActivity extends Activity {
	String data;
	TextView poi_x;
	TextView poi_y;
	TextView poi_name;
	TextView poi_type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi_display);
		
		data  = getIntent().getExtras().getString("POI");
		
		poi_x = (TextView)findViewById(R.id.textViewPoiX);
		poi_y = (TextView)findViewById(R.id.textViewPoiY);
		poi_name = (TextView)findViewById(R.id.textViewPoiName);
		poi_type = (TextView)findViewById(R.id.textViewPoiType);

		String[] words = data.split("#");
		
		poi_name.setText(words[1]);
		poi_type.setText(words[2]);
		poi_x.setText(words[3]);
		poi_y.setText(words[4]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poi_display, menu);
		return true;
	}

}
