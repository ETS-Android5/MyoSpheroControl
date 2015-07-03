package de.nachregenkommtsonne.myospherocontrol;

public class ServiceControllerStarter implements Runnable
{
  private ServiceControllerBroadcastReceiver _serviceControllerBroadcastReceiver;
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;

  public ServiceControllerStarter(ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver,
      IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler)
  {
    _serviceControllerBroadcastReceiver = serviceControllerBroadcastReceiver;
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;
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

    _serviceControllerBroadcastReceiver.get_serviceController().get_state().setBluetoothState(BluetoothState.on);

    if (_serviceControllerBroadcastReceiver.get_serviceController().get_state().isRunning())
    {
      _serviceControllerBroadcastReceiver.get_serviceController().get_myoController().startConnecting();
      _serviceControllerBroadcastReceiver.get_serviceController().get_spheroController().start();
    }

    _serviceControllerStatusChangedHandler.onChanged();
  }
}