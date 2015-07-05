package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;

public interface IBluetoothStateHandler
{
	public abstract void updateBluetoothState(BluetoothState bluetoothState);

	public abstract void deactivate();

	public abstract void activate();
}
