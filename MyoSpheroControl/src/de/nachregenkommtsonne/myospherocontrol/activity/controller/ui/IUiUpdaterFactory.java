package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public interface IUiUpdaterFactory
{
	public abstract UiUpdater create(ServiceState state);
}
