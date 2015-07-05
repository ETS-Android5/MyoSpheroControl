package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public interface IServiceBinderForUI
{

	public abstract ServiceState getState();

	public abstract void setChangedListener(IBinderEvents binderEvents);

	public abstract void unlinkClicked();

	public abstract void buttonClicked();

}
