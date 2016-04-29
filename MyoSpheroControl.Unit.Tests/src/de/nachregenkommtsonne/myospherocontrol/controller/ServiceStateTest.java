package de.nachregenkommtsonne.myospherocontrol.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public class ServiceStateTest
{
  @Test
  public void init()
  {
    ServiceState serviceState = new ServiceState();
    serviceState.onCreate(false);
    
    assertFalse(serviceState.isRunning());
    assertEquals(MyoStatus.notLinked, serviceState.getMyoStatus());
    assertEquals(SpheroStatus.disconnected, serviceState.getSpheroStatus());
    assertEquals(BluetoothState.off, serviceState.getBluetoothState());
  }
  
  @Test
  public void init_bluetoothOn()
  {
    ServiceState serviceState = new ServiceState();
    serviceState.onCreate(true);
    
    assertFalse(serviceState.isRunning());
    assertEquals(MyoStatus.notLinked, serviceState.getMyoStatus());
    assertEquals(SpheroStatus.disconnected, serviceState.getSpheroStatus());
    assertEquals(BluetoothState.on, serviceState.getBluetoothState());
  }
}