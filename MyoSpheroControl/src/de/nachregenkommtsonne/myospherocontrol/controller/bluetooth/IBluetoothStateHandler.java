package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

public interface IBluetoothStateHandler
{
  public abstract void updateBluetoothState(BluetoothStatus bluetoothState);

  public abstract void deactivate();

  public abstract void activate();
}
