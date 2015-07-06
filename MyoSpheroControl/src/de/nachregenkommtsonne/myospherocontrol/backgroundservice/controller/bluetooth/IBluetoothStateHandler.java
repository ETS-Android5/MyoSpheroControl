package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth;

public interface IBluetoothStateHandler
{
	public abstract void updateBluetoothState(BluetoothState bluetoothState);

	public abstract void deactivate();

	public abstract void activate();
}
