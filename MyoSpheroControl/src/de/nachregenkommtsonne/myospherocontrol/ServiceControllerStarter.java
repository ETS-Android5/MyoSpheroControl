package de.nachregenkommtsonne.myospherocontrol;

public class ServiceControllerStarter implements Runnable
{
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  private ServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public ServiceControllerStarter(IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler,
      ServiceState serviceState, IMyoController myoController, ISpheroController spheroController)
  {
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;
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

    _serviceState.setBluetoothState(BluetoothState.on);

    if (_serviceState.isRunning())
    {
      _myoController.startConnecting();
      _spheroController.start();
    }

    _serviceControllerStatusChangedHandler.onChanged();
  }
}