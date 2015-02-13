package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroEvents;

public class SpheroHandler implements ISpheroEvents {
	
	IGuiCapabilities _guiController;

	public SpheroHandler(IGuiCapabilities guiController) {
		_guiController = guiController;
	}

	public void spheroConnected() {
		_guiController.toast("Sphero Connected!");
	}

	public void spheroDisconnected() {
	}
}
