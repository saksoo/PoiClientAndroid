package poiservicesclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import com.poi.service.SpyService;
import poiservices.PoiBeatServiceImplementationService;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import configuration.DatabaseInformation;
import configuration.WebServiceInformation;

public class Application extends android.app.Application {
	public WebServiceInformation webserviceInformation;
	public DatabaseInformation dbInformation;
	public PoiBeatServiceImplementationService server;
	
	public String username;
	public String password;
	
	private int R = 3;
	
	public Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        username = null;
        
		try {
			Log.i("Application", "OnCreate");
			AssetManager assetManager = getAssets();
			InputStream inputStream = assetManager.open("config.properties");
			Properties p = new Properties();
			p.load(inputStream);
			inputStream.close();
			
			webserviceInformation = new WebServiceInformation(p);
			dbInformation = new DatabaseInformation(p);
			server = new PoiBeatServiceImplementationService(webserviceInformation);
			
			database = new Database(this.getBaseContext(), dbInformation);
			
			Log.i("Application:", webserviceInformation.toString());
			Log.i("Application:", dbInformation.toString());
		} catch (IOException e) {
			Log.e("Application:", e.getMessage());
		}
		
		
    }
    
    
    @Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.i("App", "OnTerminate!");
		database.helper.close();
	}


	public boolean isLoggedIn() {
    	return username != null;
    }
	
	public void logout() {
		database.Reset();
		username = "";
		password = "";
	}
    
  
    
	
	public boolean loginUser(String username, String password) {
		if (!Network.isNetworkAvailable(this)) {
			return false;
		} else {
			boolean result = server.loginUser(username+"#"+password);
			if (result) {
				this.username = username;
				this.password = password;
			}
			return result;
		}
	}

	public boolean registerUser(String username, String password1, String password2) {
		if (!Network.isNetworkAvailable(this)) {
			return false;
		} else {
			if (password1.equals(password2) == false) {
				return false;
			}
			boolean result = server.registerUser(username+"#"+password1+"#"+password2);
			if (result) {
				this.username = username;
				this.password = password1;
			}
			return result;
		}
	}


	public String setMonitorData(int location_x, int location_y, String type, String poi_name) {
		if (!Network.isNetworkAvailable(this)) {
			// show message offine
			boolean poi_exists = database.SelectPOIByNameAndLocation(poi_name, location_x, location_y).size() > 0;
			if (poi_exists) {
				return "This poi exists already";
			} else {
				try {
					database.InsertTempPoi(poi_name, type, location_x, location_y);
				} catch (Exception ex) {
					return ex.toString();
				}
				startService(new Intent(this, SpyService.class));
				
				return "Poi saved to temp table";
			}
		} else {
			// show message online
			String result = server.setMonitorData(username+"#"+password, location_x +"," + location_y +"#" + type +"#" + poi_name);
			if (result.equals("OK")) {
				try {
					Log.i("Application", "Insert to local db : " + poi_name + " " + type + " " + location_x + " " + location_y);
					database.InsertPoi(poi_name, type, location_x, location_y);
					database.DeleteTempPoi(location_x, location_y);
				} catch (Exception ex) {
					return ex.toString();
				}
				return "Poi added";
			} else {
				return "Poi failed";
			}
		}
	}

	public ArrayList<String> getMapData(String type, int x, int y) {
		if (!Network.isNetworkAvailable(this)) {
			// show message offline
			ArrayList<String> result = database.SelectAllPoiNear(x, y, 3);
			ArrayList<String> filtered_result = new ArrayList<String>(); 
			
			if (type == null || type.length() == 0) {
				return result;
			}
			
			for (String s : result) {
				String[] words = s.split("#");
				String poi_type = words[2];
				if (poi_type.equals(type)) {
					filtered_result.add(type);
				}
			}
			
			return filtered_result;
		} else {
			// show message online
			ArrayList<String> result = server.getMapData(username+"#"+password, x +"," + y +"," + R); 
			ArrayList<String> filtered_result = new ArrayList<String>();
			
			if (type == null || type.length() == 0) {
				return result;
			}
					
			for (String s : result) {
				if (s.equals("NONE")) {
					return filtered_result;
				}
				String[] words = s.split("#");
				String poi_type = words[2];
				if (poi_type.equals(type)) {
					filtered_result.add(s);
				}
			}
			
			return filtered_result;
		}
	}	
	
}





