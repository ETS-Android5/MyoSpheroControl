package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;

public interface IUiOnUiThreadUpdater
{
	public abstract void updateUiOnUiThread(ServiceState state);
}
