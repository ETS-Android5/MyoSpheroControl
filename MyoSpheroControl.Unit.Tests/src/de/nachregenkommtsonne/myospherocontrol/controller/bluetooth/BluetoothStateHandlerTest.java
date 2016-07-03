package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateHandlerTest
{
  private IServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private BluetoothStateHandler _bluetoothStateHandler;
  
  @Before
  public void setUp()
  {
    _serviceState = mock(IServiceState.class);
    _myoController = mock(IMyoController.class);
    _spheroController = mock(ISpheroController.class);
    
    _bluetoothStateHandler = new BluetoothStateHandler(_serviceState, _myoController, _spheroController);
  }
  
  @Test
  public void handleState_STATE_TURNING_OFF()
  {
    final int STATE_TURNING_OFF = 13;
    _bluetoothStateHandler.handleState(STATE_TURNING_OFF);
    
    verify(_serviceState).setBluetoothState(BluetoothState.turningOff);
    verifyZeroInteractions(_myoController);
    verifyZeroInteractions(_spheroController);
  }
  
  @Test
  public void handleState_STATE_TURNING_OFF_running()
  {
    when(_serviceState.isRunning()).thenReturn(true);
    
    final int STATE_TURNING_OFF = 13;
    _bluetoothStateHandler.handleState(STATE_TURNING_OFF);
    
    verify(_serviceState).setBluetoothState(BluetoothState.turningOff);
    verify(_myoController).stopConnecting();
    verify(_spheroController).stopForBluetooth();
  }
  
  @Test
  public void handleState_STATE_OFF()
  {
    final int STATE_OFF = 10;
    _bluetoothStateHandler.handleState(STATE_OFF);
    
    verify(_serviceState).setBluetoothState(BluetoothState.off);
    verifyZeroInteractions(_myoController);
    verifyZeroInteractions(_spheroController);
  }
  
  @Test
  public void handleState_STATE_TURNING_ON()
  {
    final int STATE_TURNING_ON = 11;
    _bluetoothStateHandler.handleState(STATE_TURNING_ON);
    
    verify(_serviceState).setBluetoothState(BluetoothState.turningOn);
    verifyZeroInteractions(_myoController);
    verifyZeroInteractions(_spheroController);
  }
  
  @Test
  public void handleState_STATE_ON()
  {
    final int STATE_ON = 12;
    _bluetoothStateHandler.handleState(STATE_ON);
    
    verify(_serviceState).setBluetoothState(BluetoothState.on);
    verifyZeroInteractions(_myoController);
    verifyZeroInteractions(_spheroController);
  }  
  
  @Test
  public void handleState_STATE_ON_runing()
  {
    when(_serviceState.isRunning()).thenReturn(true);

    final int STATE_ON = 12;
    _bluetoothStateHandler.handleState(STATE_ON);
    
    verify(_serviceState).setBluetoothState(BluetoothState.on);
    verify(_myoController).startConnecting();
    verify(_spheroController).start();
  }
}
