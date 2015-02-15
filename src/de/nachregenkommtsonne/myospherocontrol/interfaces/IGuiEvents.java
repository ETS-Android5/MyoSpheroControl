package de.nachregenkommtsonne.myospherocontrol.interfaces;

import de.nachregenkommtsonne.myospherocontrol.BluetoothState;

public interface IGuiEvents {
	void startClicked();
	void stopClicked();
	void bluetoothStateChanged(BluetoothState bluetoothState);
}
