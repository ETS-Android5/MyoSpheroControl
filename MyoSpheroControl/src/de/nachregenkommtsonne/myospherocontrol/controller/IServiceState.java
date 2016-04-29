package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public interface IServiceState
{
  public abstract void setSpheroStatus(SpheroStatus spheroStatus);

  public abstract SpheroStatus getSpheroStatus();

  public abstract void setMyoStatus(MyoStatus myoStatus);

  public abstract MyoStatus getMyoStatus();

  public abstract void setBluetoothState(BluetoothStatus bluetoothStatus);

  public abstract BluetoothStatus getBluetoothState();

  public abstract void setRunning(boolean running);

  public abstract boolean isRunning();
}
