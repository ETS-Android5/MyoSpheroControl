package de.nachregenkommtsonne.myospherocontrol.sphero;

import java.util.List;

import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

final class SpheroDiscoveryListener implements DiscoveryListener {

	private final SpheroController spheroController;

	SpheroDiscoveryListener(SpheroController spheroController) {
		this.spheroController = spheroController;
	}

	public void onFound(List<Sphero> spheros) {
		this.spheroController._sphero = spheros.iterator().next();
		
		this.spheroController.startConnecting();
	}

	public void onBluetoothDisabled() {
		this.spheroController._connected = false;
	}

	public void discoveryComplete(List<Sphero> spheros) {
	}
}