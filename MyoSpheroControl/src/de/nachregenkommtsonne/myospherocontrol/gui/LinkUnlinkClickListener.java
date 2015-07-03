package de.nachregenkommtsonne.myospherocontrol.gui;

import com.thalmic.myo.scanner.ScanActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

final class LinkUnlinkClickListener implements OnClickListener {

	private final ControlFragment controlFragment;

	LinkUnlinkClickListener(ControlFragment controlFragment) {
		this.controlFragment = controlFragment;
	}

	public void onClick(View v) {
		ServiceState state = this.controlFragment._myBinder.getState();
		if (!state.isRunning())
			this.controlFragment.unlinkClicked();
		else {
			Intent intent = new Intent(this.controlFragment.getActivity(), ScanActivity.class);
			this.controlFragment.getActivity().startActivity(intent);
		}
	}
}