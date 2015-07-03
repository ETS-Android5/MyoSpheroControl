package de.nachregenkommtsonne.myospherocontrol.gui;

import android.view.View;
import android.view.View.OnClickListener;

final class StartStopClickListener implements OnClickListener {

	private final ControlFragment controlFragment;

	StartStopClickListener(ControlFragment controlFragment) {
		this.controlFragment = controlFragment;
	}

	public void onClick(View arg0) {
		this.controlFragment.buttonClicked();
	}
}