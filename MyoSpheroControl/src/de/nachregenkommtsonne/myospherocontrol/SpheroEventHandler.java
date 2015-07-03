package de.nachregenkommtsonne.myospherocontrol;

public class SpheroEventHandler implements ISpheroEvents
{
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  private ISpheroController _spheroController;
  private ServiceState _state;
  
  public SpheroEventHandler(IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler, ISpheroController spheroController, ServiceState state)
  {
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;
    _spheroController = spheroController;
    _state = state;
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      _spheroController.changeColor(0, 0, 255);
    }

    _state.setSpheroStatus(spheroStatus);
    _serviceControllerStatusChangedHandler.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}