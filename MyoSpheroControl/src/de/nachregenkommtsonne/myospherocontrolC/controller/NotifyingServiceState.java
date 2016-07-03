package de.nachregenkommtsonne.myospherocontrolC.controller;

import de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroStatus;

public class NotifyingServiceState implements IServiceState
{
  private IServiceState _serviceState;
  private IChangedNotifier _changedNotifier;

  public NotifyingServiceState(IServiceState serviceState)
  {
    _serviceState = serviceState;
  }

  public void setChangedNotifier(IChangedNotifier changedNotifier)
  {
    _changedNotifier = changedNotifier;
  }

  public void setSpheroStatus(SpheroStatus spheroStatus)
  {
    _serviceState.setSpheroStatus(spheroStatus);
    _changedNotifier.onChanged();
  }

  public SpheroStatus getSpheroStatus()
  {
    return _serviceState.getSpheroStatus();
  }

  public void setMyoStatus(MyoStatus myoStatus)
  {
    _serviceState.setMyoStatus(myoStatus);
    _changedNotifier.onChanged();
  }

  public MyoStatus getMyoStatus()
  {
    return _serviceState.getMyoStatus();
  }

  public void setBluetoothState(BluetoothState bluetoothStatus)
  {
    _serviceState.setBluetoothState(bluetoothStatus);
    _changedNotifier.onChanged();
  }

  public BluetoothState getBluetoothState()
  {
    return _serviceState.getBluetoothState();
  }

  public void setRunning(boolean running)
  {
    _serviceState.setRunning(running);
    _changedNotifier.onChanged();
  }

  public boolean isRunning()
  {
    return _serviceState.isRunning();
  }
}
