package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.IBinderEvents;

final class ServiceConnectionChangedListener implements IBinderEvents {

	private ControlFragment _controlFragment;

	ServiceConnectionChangedListener(ControlFragment controlFragment) {
		_controlFragment = controlFragment;
	}

	public void changed() {
		_controlFragment.updateUiOnUiThread();
	}
}