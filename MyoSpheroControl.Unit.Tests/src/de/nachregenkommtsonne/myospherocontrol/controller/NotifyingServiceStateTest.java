package de.nachregenkommtsonne.myospherocontrol.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;

public class NotifyingServiceStateTest
{
  NotifyingServiceState _notifyingServiceState;
  IServiceState _serviceState;
  IChangedNotifier _changedNotifier;
  
  @Before
  public void setUp()
  {
    _serviceState =  mock(IServiceState.class);
    _notifyingServiceState = new NotifyingServiceState(_serviceState);
    
    _changedNotifier = mock(IChangedNotifier.class);
    _notifyingServiceState.setChangedNotifier(_changedNotifier);
  }
  
  @Test
  public void getMyoStatus()
  {
    when(_serviceState.getMyoStatus()).thenReturn(MyoStatus.linked);
    
    MyoStatus myoStatus = _notifyingServiceState.getMyoStatus();

    assertEquals(MyoStatus.linked, myoStatus);
  }
  
  @Test
  public void setMyoStatus()
  {
    _notifyingServiceState.setMyoStatus(MyoStatus.linked);

    verify(_serviceState).setMyoStatus(MyoStatus.linked);
    verify(_changedNotifier).onChanged();
  }
  
  @Test
  public void getBluetoothState()
  {
    when(_serviceState.getBluetoothState()).thenReturn(BluetoothStatus.turningOff);
    
    BluetoothStatus bluetoothState = _notifyingServiceState.getBluetoothState();

    assertEquals(BluetoothStatus.turningOff, bluetoothState);
  }
  
  @Test
  public void setBluetoothState()
  {
    _notifyingServiceState.setBluetoothState(BluetoothStatus.turningOff);

    verify(_serviceState).setBluetoothState(BluetoothStatus.turningOff);
    verify(_changedNotifier).onChanged();
  }
  
  @Test
  public void isRunning()
  {
    when(_serviceState.isRunning()).thenReturn(true);
    
    boolean isRunning = _notifyingServiceState.isRunning();

    assertEquals(true, isRunning);
  }
  
  @Test
  public void setRunning()
  {
    _notifyingServiceState.setRunning(true);

    verify(_serviceState).setRunning(true);
    verify(_changedNotifier).onChanged();
  }
}
