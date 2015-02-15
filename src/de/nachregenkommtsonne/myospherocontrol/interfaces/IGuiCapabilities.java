package de.nachregenkommtsonne.myospherocontrol.interfaces;

import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;

public interface IGuiCapabilities {
	void setDisabled();
	void setEnabled();
	void informBluetoothState(BluetoothState bluetoothState);
	void informSpheroState(SpheroStatus spheroState);
	void informMyoState(MyoStatus myoStatus);
	void toast(String message);
}
