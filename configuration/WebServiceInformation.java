package configuration;

import java.util.Properties;

public class WebServiceInformation {
	public String url;
	public String http;
	public String name;
	public String ws_namespace;
	public String ws_url;
	
	public WebServiceInformation(Properties p) {
		try {
			url = p.getProperty("ws_url");
			http = p.getProperty("ws_http");
			name = p.getProperty("ws_name");
			ws_namespace = p.getProperty("ws_namespace");
			ws_url = p.getProperty("ws_url");
		} catch (Exception e) {
			url = "http://nikos-pc:10000/PoiBeat/PoiBeat/";
			http = "http://nikos-pc";
			name = "PoiBeat";
			ws_namespace = "http://PoiServices/";
			ws_url = "ws_url=http://nikos-pc:10000/PoiBeat/?wsdl";
		}
	}

	@Override
	public String toString() {
		return "WebServiceInformation [url=" + url + ", http=" + http
				+ ", name=" + name + ", ws_namespace=" + ws_namespace
				+ ", ws_url=" + ws_url + "]";
	}
}
