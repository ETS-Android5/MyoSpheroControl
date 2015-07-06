package de.nachregenkommtsonne.myospherocontrol.backgroundservice;


public interface IServiceBinderForUI
{

	public abstract ServiceState getState();

	public abstract void setChangedListener(IBinderEvents binderEvents);

	public abstract void linkUnlinkButtonClicked();

	public abstract void startStopButtonClicked();

}
