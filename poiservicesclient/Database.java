package poiservicesclient;

import java.util.ArrayList;

import configuration.DatabaseInformation;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Database {
	public DatabaseHelper helper;
	private final String sSelectAllPOI = "SELECT * FROM PoiCache";
	private final String sSelectAllPOIByLocation = "SELECT * FROM PoiCache where location_x = %d and location_y = %d";
	private final String sSelectAllPOIByNameAndLocation = "SELECT * FROM PoiCache where poi_name ='%s' and location_x = %d and location_y = %d";
	private final String sDeletePOI = "DELETE FROM PoiCache WHERE location_x = %d and location_y = %d";	
	private final String sInsertPOI = "INSERT INTO PoiCache(poi_name, type, location_x, location_y ) VALUES ('%s','%s', %d, %d)";
	
	private final String sSelectAllTempPOI = "SELECT * FROM PoiCacheTemp";
	private final String sDeleteTempPOI = "DELETE FROM PoiCacheTemp WHERE location_x = %d and location_y = %d";	
	private final String sDeleteAllTempPOI = "DELETE FROM PoiCacheTemp";
	private final String sInsertTempPOI = "INSERT INTO PoiCacheTemp(poi_name, type, location_x, location_y, action ) VALUES ('%s','%s', %d, %d, '%s')";
	
	public SQLiteDatabase con;
	//_id INTEGER, poi_name TEXT, type TEXT, location_x INTEGER, location_y INTEGER, ))");
	
	public Database(Context context, DatabaseInformation information) {
		helper = new DatabaseHelper(context, information);
		con = helper.getWritableDatabase();
	}
	
	public void Reset() {
		con.execSQL("DELETE FROM PoiCache");
		con.execSQL("DELETE FROM PoiCacheTemp");
	}
			
	 // --------------------------------------- Poi Cache -------------------------------------
	
	public ArrayList<String> SelectAllPOI() {
		try {
	    	//SQLiteDatabase con = helper.getReadableDatabase();
	    	
	    	ArrayList<String> data = new ArrayList<String>();
	    	Cursor c = con.rawQuery(sSelectAllPOI, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	return data;
		} catch (Exception ex) {
			Log.e("DB", ex.toString());
			return null;
		}
    }
	
	public ArrayList<String> SelectPOIByNameAndLocation(String name, int x, int y) {
		try {
	    	//SQLiteDatabase con = helper.getReadableDatabase();
	    	
	    	ArrayList<String> data = new ArrayList<String>();
	    	String query = String.format(sSelectAllPOIByNameAndLocation, name, x, y);
	    	Cursor c = con.rawQuery(query, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	return data;
		} catch (Exception ex) {
			Log.e("DB", ex.toString());
			return null;
		}
    }
	
	
	public ArrayList<String> SelectPOIByLocation(int x, int y) {
		try {
	    	//SQLiteDatabase con = helper.getReadableDatabase();
	    	
	    	ArrayList<String> data = new ArrayList<String>();
	    	String query = String.format(sSelectAllPOIByLocation, x, y);
	    	Cursor c = con.rawQuery(query, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	return data;
		} catch (Exception ex) {
			Log.e("DB", ex.toString());
			return null;
		}
    }
	
	 public ArrayList<String> SelectAllPoiNear(int x, int y, int R) {
		 try {
			 //SQLiteDatabase con = helper.getReadableDatabase();
	    	ArrayList<String> data = new ArrayList<String>();
	    	Cursor c = con.rawQuery(sSelectAllPOI, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		int poi_x = c.getInt(2);
	    		int poi_y = c.getInt(3);
	    		if ((x-poi_x)*(x-poi_x) + (poi_y-y)*(poi_y-y) <= R) {
	    			data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		}
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	
	    	return data;
		 } catch (Exception ex) {
			 Log.e("DB", ex.toString());
				return null;
		 }
    }
    
	
	public boolean InsertPoi(String poi_name, String type , int location_x, int location_y) {
		try {
	    	//SQLiteDatabase con = helper.getWritableDatabase();
	    	//if (SelectPOIByLocation(location_x, location_y).size() > 0) {
	    		//return false;
	    	//}
	    	
	    	String query = String.format(sInsertPOI, poi_name, type, location_x, location_y);
	    	con.execSQL(query);
	    	//con.
	    	//con.close();
	    	return true; // ###
		} catch (Exception ex) {
			Log.e("DB", ex.toString());
			return false;
		}
    }
	
	
    public void DeletePoi(int location_x, int location_y) {
    	try {
	    	//SQLiteDatabase con = helper.getWritableDatabase();
	    	String query = String.format(sDeletePOI, location_x, location_y);
	    	con.execSQL(query);    	
	    	//con.close();
    	} catch (Exception ex) {
    		Log.e("DB", ex.toString());
    	}
    }    
    
    // --------------------------------- TEMPORARY --------------------------------------
    public ArrayList<String> SelectAllTempPOI(String name, int x, int y) {
    	try {
	    	//SQLiteDatabase con = helper.getReadableDatabase();
	    	
	    	ArrayList<String> data = new ArrayList<String>();
	    	Cursor c = con.rawQuery(sSelectAllTempPOI, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	
	    	return data;
    	} catch (Exception ex) {
    		Log.e("DB", ex.toString());
    		return null;
    	}
    }
	
    
    public ArrayList<String> SelectAllTempPOI() {
    	try {
	    	//SQLiteDatabase con = helper.getReadableDatabase();
	    	
	    	ArrayList<String> data = new ArrayList<String>();
	    	Cursor c = con.rawQuery(sSelectAllTempPOI, null);
	    	c.moveToFirst();
	    	while (!c.isAfterLast()) {
	    		data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
	    		c.moveToNext();
	    	}
	    	//con.close();
	    	
	    	return data;
    	} catch (Exception ex) {
    		Log.e("DB", ex.toString());
    		return null;
    	}
    }
	
	 public ArrayList<String> SelectAllTempPoiNear(int x, int y, int R) {
		 try {
			 //SQLiteDatabase con = helper.getReadableDatabase();
		    	
		    	ArrayList<String> data = new ArrayList<String>();
		    	Cursor c = con.rawQuery(sSelectAllTempPOI, null);
		    	c.moveToFirst();
		    	while (!c.isAfterLast()) {
		    		int poi_x = c.getInt(2);
		    		int poi_y = c.getInt(3);
		    		if ((x-poi_x)*(x-poi_x) + (poi_y-y)*(poi_y-y) <= R) {
		    			data.add(c.getString(0) + "#" + c.getString(1) +"#" + c.getString(2) + "#" + c.getInt(3) +"#" + c.getInt(4));
		    		}
		    		c.moveToNext();
		    	}
		    	//con.close();
		    	
		    	return data;
		 } catch (Exception ex) {
			 Log.e("DB", ex.toString());
			 return null;
		 }
    }
    
	
	public boolean InsertTempPoi(String poi_name, String type , int location_x, int location_y) {
		try {
	    	//SQLiteDatabase con = helper.getWritableDatabase();
	    	String query = String.format(sInsertTempPOI, poi_name, type, location_x, location_y, "INSERT");
	    	con.execSQL(query);    	
	    	//con.close();
	    	return true;
		} catch (Exception ex) {
			Log.e("DB", ex.toString());
			return false;
		}
    }
	
    public void DeleteTempPoi(int location_x, int location_y) {
    	try  {
	    	//SQLiteDatabase con = helper.getWritableDatabase();
	    	String query = String.format(sDeleteTempPOI, location_x, location_y);
	    	con.execSQL(query);    	
	    	//con.close();
    	} catch (Exception ex) {
    		Log.e("DB", ex.toString());
    	}
    }
    
	
    public void DeleteAllTempPoi() {
    	try  {
	    	//SQLiteDatabase con = helper.getWritableDatabase();
	    	String query = String.format(sDeleteAllTempPOI);
	    	con.execSQL(query);    	
	    	//con.close();
    	} catch (Exception ex) {
    		Log.e("DB", ex.toString());
    	}
    }
    
}
