package poiservicesclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import configuration.DatabaseInformation;

public class DatabaseHelper extends SQLiteOpenHelper {
	final DatabaseInformation info;
	
	public DatabaseHelper(Context context, DatabaseInformation info) {
		super(context, info.database_name, null, info.database_version);
		this.info = info;
	}

	public void onCreate(SQLiteDatabase db) {
		Log.i("DatabaseHelper", "OnCreate");
        db.execSQL("CREATE TABLE PoiCache ( _id INTEGER PRIMARY KEY AUTOINCREMENT, poi_name TEXT, type TEXT, location_x INTEGER, location_y INTEGER)");
        db.execSQL("CREATE TABLE PoiCacheTemp ( _id INTEGER PRIMARY KEY AUTOINCREMENT, poi_name TEXT, type TEXT, location_x INTEGER, location_y INTEGER, action TEXT)");
        
        // String action : either DELETE OR INSERT
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
    	Log.i("DatabaseHelper", "onUpgrade");
        db.execSQL("DROP TABLE PoiCache");
        db.execSQL("DROP TABLE PoiCacheTemp");
        
        onCreate(db);
    }
}