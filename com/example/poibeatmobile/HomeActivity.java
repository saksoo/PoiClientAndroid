package com.example.poibeatmobile;

import poiservicesclient.Application;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class HomeActivity extends Activity {
	TabHost tabhost;
	Button buttonLogin;
	Button buttonRegister;
	EditText usernameText;
	EditText passwordText;
	
	EditText usernameText2;
	EditText passwordText2a;
	EditText passwordText2b;

	Application application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		tabhost = (TabHost) findViewById(R.id.tabHost);
		tabhost.setup();
		tabhost.newTabSpec("tab_creation");
		tabhost.addTab(tabhost.newTabSpec("tab_login_spec").setIndicator("Login",getResources().getDrawable(android.R.drawable.ic_menu_add)).setContent(R.id.tab1));		 
		tabhost.addTab(tabhost.newTabSpec("tab_register_spec").setIndicator("Register",getResources().getDrawable(android.R.drawable.ic_menu_add)).setContent(R.id.tab2));
		
		usernameText = (EditText) findViewById(R.id.editText1);
		passwordText = (EditText) findViewById(R.id.editText2);
		
		usernameText2 = (EditText) findViewById(R.id.editTextU);
		passwordText2a = (EditText) findViewById(R.id.editTextP1);
		passwordText2b = (EditText) findViewById(R.id.editTextP2);
		
		application = (Application)getApplication();

		buttonLogin = (Button) findViewById(R.id.buttonA);
		buttonLogin.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {				
				final String username = usernameText.getText().toString();
				final String password = passwordText.getText().toString();
				
				final Context context = v.getContext();
				
				if (username.trim().equals("") || password.trim().equals("")) {
					Toast.makeText(context, "Username and password are required fields",  Toast.LENGTH_LONG).show();
					return;
				}
				
				Thread t = new Thread(new Runnable() {					
					@Override
					public void run() {
						final boolean okay = application.loginUser(username, password); 
						
						runOnUiThread(new Runnable() {							
							@Override
							public void run() {
								if (okay) {
									Intent intent = new Intent(context, ControlPanelActivity.class);
									context.startActivity(intent);
								} else {
									Toast.makeText(context, "An error has occured. ",  Toast.LENGTH_LONG).show();				
								}	
							}
						});
					}
				});
				t.start();
			}
		});
		
		buttonRegister = (Button) findViewById(R.id.buttonB);
		buttonRegister.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				final String username = usernameText2.getText().toString();
				final String password1 = passwordText2a.getText().toString();
				final String password2 = passwordText2b.getText().toString();
				
				final Context context = v.getContext();
				
				if (username.trim().equals("") || password1.trim().equals("") ||  password2.trim().equals("")) {
					Toast.makeText(context, "Username and password are required fields",  Toast.LENGTH_LONG).show();
					return;
				}
				
				Thread t = new Thread(new Runnable() {					
					@Override
					public void run() {
						final boolean okay = application.registerUser(username, password1, password2); 
						
						runOnUiThread(new Runnable() {							
							@Override
							public void run() {
								if (okay) {
									Intent intent = new Intent(context, ControlPanelActivity.class);
									context.startActivity(intent);
								} else {
									Toast.makeText(context, "An error has occured. ",  Toast.LENGTH_LONG).show();				
								}	
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
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
}
