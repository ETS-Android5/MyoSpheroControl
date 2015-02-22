package de.nachregenkommtsonne.myospherocontrol.myo;

import com.thalmic.myo.Hub;

public interface IMyoController {
	void start();
	void stop();
	public abstract void setEventListener(IMyoEvents eventListener);
	public abstract void stopConnecting();
	public abstract void startConnecting();
}
