package poiservices;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import configuration.WebServiceInformation;

public class PoiBeatServiceImplementationService {
	WebServiceInformation info;


	public PoiBeatServiceImplementationService(WebServiceInformation info) {
		this.info = info;
	}

	public boolean loginUser(String username_password) {
		final String method_name = "loginUser";
		final String soap_action = "\"" + info.ws_namespace + method_name + "\"";
		try {
			SoapObject request = new SoapObject(info.ws_namespace, method_name);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			
			PropertyInfo propInfo = new PropertyInfo();
			propInfo.name = "arg0";
			propInfo.setValue(username_password);
			request.addProperty(propInfo);
			
			HttpTransportSE ht = new HttpTransportSE(info.ws_url);
			ht.call(soap_action, envelope);
			SoapPrimitive res = (SoapPrimitive) envelope.getResponse(); 
			Log.i("PoiBeatServiceImplementationService", "Res: " + res.toString());
			return res.toString().equalsIgnoreCase("true");
		} catch (IOException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
		}
		return false;
	}

	public boolean registerUser(String username_password1_password2) {
		final String method_name = "registerUser";
		final String soap_action = "\"" + info.ws_namespace + method_name + "\"";
		
		try {
			SoapObject request = new SoapObject( info.ws_namespace, method_name);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			
			PropertyInfo propInfo = new PropertyInfo();
			propInfo.name = "arg0";
			propInfo.setValue(username_password1_password2);
			request.addProperty(propInfo);

			HttpTransportSE ht = new HttpTransportSE(info.ws_url);
			ht.call(soap_action, envelope);
			SoapPrimitive res = (SoapPrimitive) envelope.getResponse(); 
			Log.i("PoiBeatServiceImplementationService", "Res: " + res.toString());
			return res.toString().equalsIgnoreCase("OK");
		} catch (IOException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
		}
		return false;
	}

	public String setMonitorData(String username_password, String newEntry) {
		final String method_name = "setMonitorData";
		final String soap_action = "\"" + info.ws_namespace + method_name + "\"";
		if (username_password == null || newEntry == null) {
			return "";
		}
		try {
			SoapObject request = new SoapObject(info.ws_namespace, method_name);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			
			HttpTransportSE ht = new HttpTransportSE(info.ws_url);
			
			PropertyInfo p1 = new PropertyInfo();
			p1.setName("arg0");
			p1.setValue(username_password);
			p1.setType(String.class);
			request.addProperty(p1);
			
			PropertyInfo p2 = new PropertyInfo();
			p2.setName("arg1");
			p2.setValue(newEntry);
			p2.setType(String.class);
			request.addProperty(p2);
			
			envelope.setOutputSoapObject(request);
			
			ht.getServiceConnection().setRequestProperty("Connection", "close");
			ht.call(soap_action, envelope);
			SoapPrimitive res = (SoapPrimitive) envelope.getResponse(); 
			return res.toString();
		} catch (IOException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
		}
		return "FAIL";
	}

	public  ArrayList<String> getMapData(String username_password, String position) {
		final String method_name = "getMapData";
		final String soap_action = "\"" + info.ws_namespace + method_name + "\"";
		try {
			SoapObject request = new SoapObject(info.ws_namespace, method_name);
			
			PropertyInfo p1 = new PropertyInfo();
			p1.setName("arg0");
			p1.setValue(username_password);
			p1.setType(String.class);
			request.addProperty(p1);
			
			PropertyInfo p2 = new PropertyInfo();
			p2.setName("arg1");
			p2.setValue(position);
			p2.setType(String.class);
			request.addProperty(p2);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);

			HttpTransportSE ht = new HttpTransportSE(info.ws_url);
			ht.call(soap_action, envelope);
			SoapPrimitive res = (SoapPrimitive) envelope.getResponse();
			String data = res.toString();
			String[] pois = data.split("[$]");
			ArrayList<String> list = new ArrayList<String>();
			for (String p : pois) {
				list.add(p);
			}
			return list;
		} catch (IOException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.e("PoiBeatServiceImplementationService", e.toString());
		}
		return null;
	}


}
