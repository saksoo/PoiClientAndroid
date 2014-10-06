package listeners;

import com.example.poibeatmobile.ControlPanelActivity;

import android.view.View;

public class EntertainmentSetListener implements View.OnClickListener {
	ControlPanelActivity activity;
	
	public EntertainmentSetListener(ControlPanelActivity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.spinnerSet.setAdapter(activity.entertainmentAdapter);
	}
}
