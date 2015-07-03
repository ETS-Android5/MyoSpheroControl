package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.IBinderEvents;

final class ServiceConnectionChangedListener implements IBinderEvents {

	private final MyServiceConnection myServiceConnection;

	ServiceConnectionChangedListener(MyServiceConnection myServiceConnection) {
		this.myServiceConnection = myServiceConnection;
	}

	public void changed() {
		this.myServiceConnection.controlFragment.updateUiOnUiThread();
	}
}