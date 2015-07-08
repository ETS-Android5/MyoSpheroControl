package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;

public interface IBluetoothStateHandler
{
  public abstract void updateBluetoothState(BluetoothStatus bluetoothState);

  public abstract void deactivate();

  public abstract void activate();

  void setChangedNotifier(ChangedNotifier changedNotifier);
}
