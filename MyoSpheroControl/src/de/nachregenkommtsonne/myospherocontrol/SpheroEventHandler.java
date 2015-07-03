package de.nachregenkommtsonne.myospherocontrol;

public class SpheroEventHandler implements ISpheroEvents
{
  private ServiceController _serviceController;

  public SpheroEventHandler(ServiceController serviceController)
  {
    _serviceController = serviceController;
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      _serviceController.get_spheroController().changeColor(0, 0, 255);
    }

    _serviceController.get_state().setSpheroStatus(spheroStatus);
    _serviceController.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}