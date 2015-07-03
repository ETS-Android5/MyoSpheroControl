package de.nachregenkommtsonne.myospherocontrol.service;

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

    _serviceControllerBroadcastReceiver._serviceController._state.setBluetoothState(BluetoothState.on);

    if (_serviceControllerBroadcastReceiver._serviceController._state.isRunning())
    {
      _serviceControllerBroadcastReceiver._serviceController._myoController.startConnecting();
      _serviceControllerBroadcastReceiver._serviceController._spheroController.start();
    }

    _serviceControllerBroadcastReceiver._serviceController.onChanged();
  }
}