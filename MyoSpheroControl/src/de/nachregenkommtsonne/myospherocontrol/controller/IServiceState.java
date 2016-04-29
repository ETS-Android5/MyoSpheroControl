package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public interface IServiceState
{
  public abstract void setSpheroStatus(SpheroStatus spheroStatus);

  public abstract SpheroStatus getSpheroStatus();

  public abstract void setMyoStatus(MyoStatus myoStatus);

  public abstract MyoStatus getMyoStatus();

  public abstract void setBluetoothState(BluetoothState bluetoothStatus);

  public abstract BluetoothState getBluetoothState();

  public abstract void setRunning(boolean running);

  public abstract boolean isRunning();
}
