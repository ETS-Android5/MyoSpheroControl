package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

public class GuiStateHinter {
	public GuiStateHinter() {

	}

	public String getHint(ServiceState guiState) {

		if (!guiState.isRunning()) {
			return "Press Start to connect the devices.";
		}

		if (guiState.getBluetoothState() != BluetoothState.on) {
			if (guiState.getBluetoothState() == BluetoothState.turningOn)
				return "Bluetooth is turning on. Please wait...";

			return "Please enable Bluetooth.";
		}

		if (guiState.getMyoStatus() != MyoStatus.connected) {
			if (guiState.getMyoStatus() == MyoStatus.notSynced)
				return "Myo is not synced. Please perform the sync gesture.";

			if (guiState.getMyoStatus() == MyoStatus.connecting)
				return "Connecting to Myo. Is your Myo turned on?";

			if (guiState.getMyoStatus() == MyoStatus.notLinked)
				return "Please touch your Myo with your Phone to link.";
		}

		if (guiState.getSpheroStatus() != SpheroStatus.connected) {
			if (guiState.getSpheroStatus() == SpheroStatus.connecting)
				return "Sphero found. Connecting...";

			if (guiState.getSpheroStatus() == SpheroStatus.discovering)
				return "Searching for Sphero. Is your Sphero turned on and paired with your phone?";
		}

		if (guiState.getSpheroStatus() == SpheroStatus.connected &&
				guiState.getMyoStatus() == MyoStatus.connected)
			return "Everything is fine. Have Fun!";
		
		return "Woops! Something is wrong. Please restart the App and if you experience connection problems reboot your phone, your myo and your sphero or have a lot of patience.";
	}
}
