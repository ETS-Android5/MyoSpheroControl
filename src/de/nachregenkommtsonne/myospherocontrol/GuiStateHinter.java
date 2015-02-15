package de.nachregenkommtsonne.myospherocontrol;

public class GuiStateHinter {
	public GuiStateHinter() {

	}

	public String getHint(GuiState guiState) {

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
				return "Searching for Sphero. Is your Sphero turned on?";
		}

		return "Everything is fine. Have fun.";
	}
}
