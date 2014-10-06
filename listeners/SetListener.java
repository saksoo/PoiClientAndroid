package listeners;

import com.example.poibeatmobile.ControlPanelActivity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SetListener implements View.OnClickListener{
	ControlPanelActivity activity;
	
	public SetListener(ControlPanelActivity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			final String type = activity.spinnerSet.getItemAtPosition(activity.spinnerSet.getSelectedItemPosition()).toString();
			final String name = activity.nameSet.getText().toString();
			final int x = (int)activity.location.getLatitude();
			//final int x = 10;
			final int y = (int)activity.location.getLongitude();
			//final int y = 10;
			final Context context = v.getContext();					
			
			Log.i("Add poi" , "Name: " + name + " " + "Type: " + type + " X: " + x + " Y: " + y);
			
			if (name.equals("") || type.equals("")) {
				Toast.makeText(context, "You have to type a name (doh?) ",  Toast.LENGTH_LONG).show();
				return;
			}
			Thread t = new Thread(new Runnable() {					
				@Override
				public void run() {
					final String r = activity.application.setMonitorData(x, y, type, name);
					
					activity.runOnUiThread(new Runnable() {							
						@Override
						public void run() {
							Toast.makeText(context, r,  Toast.LENGTH_LONG).show();
						}
					});
				}
			});
			t.start();
		} catch (Exception x) {
			Toast.makeText(v.getContext(), "An error has occured: " + x.toString(),  Toast.LENGTH_LONG).show();		
		}
	}

}
