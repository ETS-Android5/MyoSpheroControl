package de.nachregenkommtsonne.myospherocontrol.gui;

import com.thalmic.myo.scanner.ScanActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import de.nachregenkommtsonne.myospherocontrol.service.MyBinder;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

final class LinkUnlinkClickListener implements OnClickListener {

	private final ControlFragment _controlFragment;
	private MyBinder _myBinder;

	LinkUnlinkClickListener(ControlFragment controlFragment, MyBinder myBinder) {
		_controlFragment = controlFragment;
		_myBinder = myBinder;
	}

	public void onClick(View v) {
		ServiceState state = _myBinder.getState();
		if (!state.isRunning())
			_controlFragment.unlinkClicked();
		else {
			Intent intent = new Intent(this._controlFragment.getActivity(), ScanActivity.class);
			_controlFragment.getActivity().startActivity(intent);
		}
	}
}