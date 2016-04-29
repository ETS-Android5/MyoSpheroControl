package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public class ServiceState implements IServiceState
{
  private MyoStatus _myoStatus;
  private SpheroStatus _spheroStatus;
  private BluetoothStatus _bluetoothStatus;

  private boolean _running;

  public ServiceState()
  {
    _myoStatus = MyoStatus.notLinked;
    _spheroStatus = SpheroStatus.disconnected;
    _bluetoothStatus = BluetoothStatus.off;

    _running = false;
  }

  public void onCreate(boolean isBluetoothEnabled)
  {
    _bluetoothStatus = isBluetoothEnabled ? BluetoothStatus.on : BluetoothStatus.off;
  }

  public boolean isRunning()
  {
    return _running;
  }

  public void setRunning(boolean running)
  {
    _running = running;
  }

  @Override
  public BluetoothStatus getBluetoothState()
  {
    return _bluetoothStatus;
  }

  @Override
  public void setBluetoothState(BluetoothStatus bluetoothStatus)
  {
    _bluetoothStatus = bluetoothStatus;
  }

  @Override
  public void setMyoStatus(MyoStatus myoStatus)
  {
    _myoStatus = myoStatus;
  }

  @Override
  public void setSpheroStatus(SpheroStatus spheroStatus)
  {
    _spheroStatus = spheroStatus;
  }

  @Override
  public MyoStatus getMyoStatus()
  {
    return _myoStatus;
  }

  @Override
  public SpheroStatus getSpheroStatus()
  {
    return _spheroStatus;
  }
}