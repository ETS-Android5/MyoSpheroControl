package de.nachregenkommtsonne.myospherocontrol;

public class SpheroEventHandler implements ISpheroEvents
{
  private ServiceController _serviceController;
  IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;

  public SpheroEventHandler(ServiceController serviceController,
      IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler)
  {
    _serviceController = serviceController;
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      _serviceController.get_spheroController().changeColor(0, 0, 255);
    }

    _serviceController.get_state().setSpheroStatus(spheroStatus);
    _serviceControllerStatusChangedHandler.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}