package de.nachregenkommtsonne.myospherocontrol.myo;

public interface IMyoController {
	void start();
	void stop();
	public abstract void setEventListener(IMyoEvents eventListener);
}
