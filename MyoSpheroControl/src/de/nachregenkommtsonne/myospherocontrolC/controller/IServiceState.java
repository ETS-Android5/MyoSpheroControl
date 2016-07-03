package de.nachregenkommtsonne.myospherocontrolC.controller;

import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroStatus;

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
