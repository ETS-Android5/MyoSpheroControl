package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public interface IUiUpdaterFactory
{
	public abstract UiUpdater create(ServiceState state);
}
