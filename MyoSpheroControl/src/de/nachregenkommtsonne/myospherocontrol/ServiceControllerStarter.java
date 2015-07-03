package de.nachregenkommtsonne.myospherocontrol;

class ServiceControllerStarter implements Runnable
{
  private ServiceControllerBroadcastReceiver _serviceControllerBroadcastReceiver;

  public ServiceControllerStarter(ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver)
  {
    _serviceControllerBroadcastReceiver = serviceControllerBroadcastReceiver;
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

    _serviceControllerBroadcastReceiver.get_serviceController().onChanged();
  }
}