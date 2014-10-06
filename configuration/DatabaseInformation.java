package configuration;

import java.util.Properties;


public class DatabaseInformation {
	public String database_name;
	public int database_version;
	
	public DatabaseInformation(Properties p) {
		try {
			database_name = p.getProperty("database_name");
			database_version = Integer.parseInt(p.getProperty("database_version"));
		} catch (Exception e) {
			database_name = "PoiBeat";
			database_version = 1;
		}
	}

	@Override
	public String toString() {
		return "DatabaseInformation [database_name=" + database_name
				+ ", database_version=" + database_version + "]";
	}
}
