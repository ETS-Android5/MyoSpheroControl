package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

public class ButtonClickHandler
{
	private IMyoController _myoController;
	private ISpheroController _spheroController;
	private ServiceState _state;
	private IChangedNotifier _changedNotifier;

  public ButtonClickHandler(
      IMyoController myoController,
      ISpheroController spheroController,
      IChangedNotifier changedNotifier,
      ServiceState serviceState)
  {
    _myoController = myoController;
    _spheroController = spheroController;
    _changedNotifier = changedNotifier;
    _state = serviceState;
  }
	
	public void buttonClicked()
	{
		if (_state.isRunning())
		{
			if (_state.getBluetoothState() == BluetoothState.on)
			{
				_spheroController.stop();
			}
			_myoController.stop();
		} else
		{
			_myoController.start();
			if (_state.getBluetoothState() == BluetoothState.on)
			{
				_myoController.startConnecting();
				_spheroController.start();
			}
		}

		_state.setRunning(!_state.isRunning());
		_changedNotifier.onChanged();
	}

	public void unlinkClicked()
	{
		_myoController.connectAndUnlinkButtonClicked();
	}
}
