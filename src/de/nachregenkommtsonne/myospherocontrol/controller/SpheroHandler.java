package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroEvents;

public class SpheroHandler implements ISpheroEvents {
	
	IGuiCapabilities _guiController;

	public SpheroHandler(IGuiCapabilities guiController) {
		_guiController = guiController;
	}
	
	public void bluetoothDisabled() {
		_guiController.informBluetoothState(BluetoothState.off);
	}

	public void spheroStateChanged(SpheroStatus spheroStatus) {
		_guiController.informSpheroState(spheroStatus);
	}
}
