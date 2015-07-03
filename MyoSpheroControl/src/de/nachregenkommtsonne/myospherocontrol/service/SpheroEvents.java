package de.nachregenkommtsonne.myospherocontrol.service;

import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

final class SpheroEvents implements ISpheroEvents {

	private final ServiceController serviceController;

	SpheroEvents(ServiceController serviceController) {
		this.serviceController = serviceController;
	}

	public void spheroStateChanged(SpheroStatus spheroStatus) {
		if (spheroStatus == SpheroStatus.connected){
			this.serviceController._spheroController.changeColor(0, 0, 255);
		}
		
		this.serviceController._state.setSpheroStatus(spheroStatus);
		this.serviceController.onChanged();
	}

	public void bluetoothDisabled() {
	}
}