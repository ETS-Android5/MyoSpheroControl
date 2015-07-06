package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

public class ButtonClickHandler
{
	private IMyoController _myoController;
	private ISpheroController _spheroController;
	private ServiceState _state;

  public ButtonClickHandler(
      IMyoController myoController,
      ISpheroController spheroController,
      ServiceState serviceState)
  {
    _myoController = myoController;
    _spheroController = spheroController;
    _state = serviceState;
  }
	
	public void startStopButtonClicked()
	{
		if (_state.isRunning())
		{
			if (_state.getBluetoothState() == BluetoothState.on)
			{
				_spheroController.stop();
			}
			_myoController.stop();
		} 
		else
		{
			_myoController.start();
			if (_state.getBluetoothState() == BluetoothState.on)
			{
				_myoController.startConnecting();
				_spheroController.start();
			}
		}

		_state.setRunning(!_state.isRunning());
	}

	public void linkUnlinkButtonClicked()
	{
		_myoController.connectAndUnlinkButtonClicked();
	}
}
