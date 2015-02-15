package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.GuiState;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiEvents;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;

public class GuiHandler implements IGuiEvents {

	IMyoCapabilities _myoController;
	ISpheroCapabilities _spheroController;
	IGuiCapabilities _guiController;
	GuiState _guiState;

	public GuiHandler(IMyoCapabilities myoController, ISpheroCapabilities spheroController, IGuiCapabilities guiController, GuiState guiState) {
		_myoController = myoController;
		_spheroController = spheroController;
		_guiController = guiController;
		_guiState = guiState;
	}

	public void startClicked() {
		_guiController.setEnabled();
		if (_guiState.getBluetoothState() == BluetoothState.on) {
			_myoController.start();
			_spheroController.start();
		}
	}

	public void stopClicked() {
		_myoController.stop();
		_spheroController.stop();
		_guiController.setDisabled();
	}

	public void bluetoothStateChanged(BluetoothState bluetoothState) {

		if (bluetoothState != BluetoothState.on && _guiState.isRunning()) {
			try {
				_myoController.stop();
			} catch (Exception ex) {
			}
			try {
				_spheroController.stop();
			} catch (Exception ex) {
			}
		}
		if (bluetoothState == BluetoothState.on && _guiState.isRunning()) {
			_myoController.start();
			_spheroController.start();
		}

		_guiController.informBluetoothState(bluetoothState);
	}
}
