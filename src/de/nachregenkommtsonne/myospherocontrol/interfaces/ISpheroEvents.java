package de.nachregenkommtsonne.myospherocontrol.interfaces;

import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;

public interface ISpheroEvents {
	void bluetoothDisabled();
	void spheroStateChanged(SpheroStatus spheroStatus);
}
