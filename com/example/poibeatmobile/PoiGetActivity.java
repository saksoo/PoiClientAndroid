package com.example.poibeatmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class PoiGetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi_get);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poi_get, menu);
		return true;
	}


}
