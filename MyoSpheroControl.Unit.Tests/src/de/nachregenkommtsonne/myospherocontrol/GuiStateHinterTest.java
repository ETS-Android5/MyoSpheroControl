package de.nachregenkommtsonne.myospherocontrol;

import org.junit.Test;

import de.nachregenkommtsonne.myospherocontrolC.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrolC.R;
import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroStatus;

import static org.junit.Assert.*;

import org.junit.Before;

public class GuiStateHinterTest
{
  private GuiStateHinter _guiStateHinter;
  private IServiceState _serviceState;

  @Before
  public void setUp()
  {
    _guiStateHinter = new GuiStateHinter();
    _serviceState = new ServiceState();
  }

  @Test
  public void getHint_NotRunning()
  {
    _serviceState.setRunning(false);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.pressStart, hint);
  }

  @Test
  public void getHint_BluetoothOff()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.off);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.pleaseEnableBluetooth, hint);
  }

  @Test
  public void getHint_BluetoothTurningOff()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.turningOff);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.pleaseEnableBluetooth, hint);
  }

  @Test
  public void getHint_BluetoothTurningOn()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.turningOn);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.bluetoothTurningOn, hint);
  }

  @Test
  public void getHint_MyoNotLinked()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.notLinked);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.linkMyo, hint);
  }

  @Test
  public void getHint_MyoConnecting()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.linked);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.connectingToMyo, hint);
  }

  @Test
  public void getHint_MyoNotSynced()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.notSynced);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.myoNotSynced, hint);
  }

  @Test
  public void getHint_SpheroDiscovering()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.connected);
    _serviceState.setSpheroStatus(SpheroStatus.discovering);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.searchingSphero, hint);
  }

  @Test
  public void getHint_SpheroConnecting()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.connected);
    _serviceState.setSpheroStatus(SpheroStatus.connecting);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.connectingToSphero, hint);
  }

  @Test
  public void getHint()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.connected);
    _serviceState.setSpheroStatus(SpheroStatus.connected);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.controllerActive, hint);
  }

  @Test
  public void getHint_Error()
  {
    _serviceState.setRunning(true);
    _serviceState.setBluetoothState(BluetoothState.on);
    _serviceState.setMyoStatus(MyoStatus.connected);
    _serviceState.setSpheroStatus(SpheroStatus.disconnected);

    int hint = _guiStateHinter.getHint(_serviceState);

    assertEquals(R.string.heavyError, hint);
  }
}
