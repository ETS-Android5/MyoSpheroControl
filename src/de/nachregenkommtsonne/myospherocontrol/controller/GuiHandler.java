package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiEvents;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;

public class GuiHandler implements IGuiEvents {

	IMyoCapabilities _myoController;
	ISpheroCapabilities _spheroController;
	IGuiCapabilities _guiController;

	public GuiHandler(IMyoCapabilities myoController, ISpheroCapabilities spheroController, IGuiCapabilities guiController) {
		_myoController = myoController;
		_spheroController = spheroController;
		_guiController = guiController;
	}

	public void startClicked() {
		_myoController.showMyoConnect();
		
	}

	public void stopClicked() {
	}
}
