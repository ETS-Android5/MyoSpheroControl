package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.os.Handler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerStarter;

public class BluetoothStateHandler implements IBluetoothStateHandler
{
  private IChangedNotifier _changedNotifier;
  private ServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public BluetoothStateHandler(
      IChangedNotifier changedNotifier, ServiceState serviceState,
      IMyoController myoController, ISpheroController spheroController)
	{
    _changedNotifier = changedNotifier;
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
	}
	
	public void activate()
	{
		//TODO: create Factory x2?
		Handler handler = new Handler();
		ServiceControllerStarter serviceControllerStarter = new ServiceControllerStarter(_changedNotifier, _serviceState, _myoController,
		    _spheroController);
		handler.post(serviceControllerStarter);
	}

	public void deactivate()
	{
		_serviceState.setControlMode(false);

		if (_serviceState.isRunning())
		{
		  _myoController.stopConnecting();
		  _spheroController.stopForBluetooth();
		}
	}

	public void updateBluetoothState(BluetoothState bluetoothState)
	{
		_serviceState.setBluetoothState(bluetoothState);
	}
}
