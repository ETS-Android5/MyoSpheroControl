package de.nachregenkommtsonne.myospherocontrol.interfaces;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;

public interface IMyoEvents {
	void myoConnected();
	void myoDisconnected();
	void myoUnlocked();
	void myoControlActivated();
	void myoControlDeactivated();
	void myoOrientationDataCollected(Quaternion rotation, Myo myo);
	void myoSynced();
	void myoUnsynced();
}
