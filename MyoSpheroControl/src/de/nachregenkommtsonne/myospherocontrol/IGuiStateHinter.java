package de.nachregenkommtsonne.myospherocontrol;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public interface IGuiStateHinter
{
  int getHint(ServiceState guiState);
}
