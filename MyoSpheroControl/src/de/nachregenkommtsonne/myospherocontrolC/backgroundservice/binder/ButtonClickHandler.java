package de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.ISpheroController;

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
  }

  public void linkUnlinkButtonClicked()
  {
    _myoController.connectAndUnlinkButtonClicked();
  }
}
