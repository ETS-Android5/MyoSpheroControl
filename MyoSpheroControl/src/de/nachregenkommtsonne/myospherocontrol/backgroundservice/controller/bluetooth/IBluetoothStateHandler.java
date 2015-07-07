package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;

public interface IBluetoothStateHandler
{
	public abstract void updateBluetoothState(BluetoothState bluetoothState);

	public abstract void deactivate();

	public abstract void activate();

  void setChangedNotifier(ChangedNotifier changedNotifier);
}
