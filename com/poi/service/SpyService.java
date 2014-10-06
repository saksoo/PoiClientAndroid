package com.poi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import configuration.DatabaseInformation;
import configuration.WebServiceInformation;
import poiservices.PoiBeatServiceImplementationService;
import poiservicesclient.Application;
import poiservicesclient.Database;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;


public class SpyService extends Service {	
	public WebServiceInformation webserviceInformation;
	public DatabaseInformation dbInformation;
	public PoiBeatServiceImplementationService server;
	
	public String username;
	public String password;
	
	Database database;
	
	private Thread workingThread = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		try {
			AssetManager assetManager = getAssets();
			InputStream inputStream = assetManager.open("config.properties");
			Properties p = new Properties();
			p.load(inputStream);
			inputStream.close();
			
			webserviceInformation = new WebServiceInformation(p);
			dbInformation = new DatabaseInformation(p);
			server = new PoiBeatServiceImplementationService(webserviceInformation);
			
			Application a = (Application)getApplication();
			username = a.username;
			password = a.password;
			
			database = new Database(this.getBaseContext(), dbInformation);
			
			Log.i("Service:", webserviceInformation.toString());
			Log.i("Service:", dbInformation.toString());
		} catch (IOException e) {
			Log.e("Service:", e.getMessage());
		}
	}



	@Override
	public void onStart(Intent intent, int startId) {
		workingThread = new Thread(new Runnable() {			
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(1);
						
						// ----------------- update code ---------------
						ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo ni = cm.getActiveNetworkInfo();
						if (ni == null) {
							continue;
						}
						ArrayList<String> data = database.SelectAllTempPOI();
						if (data == null || data.size()  == 0) {
							break;
						} else {
							for (String poi : data) {
								String[] words = poi.split("#");
								
								String poi_name = words[1];
								String poi_type = words[2];
								String poi_x = words[3];
								String poi_y = words[4];
								String newEntry = poi_x +","+poi_y+"#"+poi_name +"#"+poi_type;
								
								PoiBeatServiceImplementationService webService = new PoiBeatServiceImplementationService(webserviceInformation);
								webService.setMonitorData(username+"#"+password, newEntry);								
							}
							database.DeleteAllTempPoi();
						}
						
						
					} catch (InterruptedException e) {
						break;
					}
				}			
			}
		});
		workingThread.start();
	}

	@Override
	public void onDestroy() {
		try {
			if (workingThread != null) {
				workingThread.interrupt();
				if (workingThread != null)
					workingThread.join();
			}
			database.con.close();
			stopSelf();
		} catch (InterruptedException e) {
		}
		
	}
}
