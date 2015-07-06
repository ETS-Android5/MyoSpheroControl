package de.nachregenkommtsonne.myospherocontrol;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public interface IGuiStateHinter
{
  int getHint(ServiceState guiState);
}
