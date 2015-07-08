package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class ServiceControllerStarter implements Runnable
{
  private IChangedNotifier _changedNotifier;
  private ServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public ServiceControllerStarter(
      IChangedNotifier changedNotifier,
      ServiceState serviceState,
      IMyoController myoController,
      ISpheroController spheroController)
  {
    _changedNotifier = changedNotifier;
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void run()
  {
    try
    {
      Thread.sleep(1400);
    }
    catch (InterruptedException e)
    {
    }

    _serviceState.setBluetoothState(BluetoothStatus.on);

    if (_serviceState.isRunning())
    {
      _myoController.startConnecting();
      _spheroController.start();
    }

    _changedNotifier.onChanged();
  }
}