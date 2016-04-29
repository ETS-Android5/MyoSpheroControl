package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public class ServiceState implements IServiceState
{
  private MyoStatus _myoStatus;
  private SpheroStatus _spheroStatus;
  private BluetoothState _bluetoothState;

  private boolean _running;

  public ServiceState()
  {
    _myoStatus = MyoStatus.notLinked;
    _spheroStatus = SpheroStatus.disconnected;
    _bluetoothState = BluetoothState.off;

    _running = false;
  }

  public void onCreate(boolean isBluetoothEnabled)
  {
    _bluetoothState = isBluetoothEnabled ? BluetoothState.on : BluetoothState.off;
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
  public BluetoothState getBluetoothState()
  {
    return _bluetoothState;
  }

  @Override
  public void setBluetoothState(BluetoothState bluetoothStatus)
  {
    _bluetoothState = bluetoothStatus;
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