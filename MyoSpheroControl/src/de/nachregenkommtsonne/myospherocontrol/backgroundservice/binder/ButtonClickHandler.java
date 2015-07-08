package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class ButtonClickHandler
{
  private ISpheroController _spheroController;
  private IMyoController _myoController;
  private IServiceState _state;

  public ButtonClickHandler(ISpheroController spheroController, IMyoController myoController, IServiceState serviceState)
  {
    _spheroController = spheroController;
    _myoController = myoController;
    _state = serviceState;
  }

  public void startStopButtonClicked()
  {
    if (_state.isRunning())
    {
      if (_state.getBluetoothState() == BluetoothStatus.on)
      {
        _spheroController.stop();
      }
      _myoController.stop();
    }
    else
    {
      _myoController.start();
      if (_state.getBluetoothState() == BluetoothStatus.on)
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
