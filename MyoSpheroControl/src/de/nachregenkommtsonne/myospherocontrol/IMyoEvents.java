package de.nachregenkommtsonne.myospherocontrol;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;

public interface IMyoEvents
{
  void myoControlActivated();

  void myoControlDeactivated();

  void myoOrientationDataCollected(Quaternion rotation, Myo myo);

  void myoStateChanged(MyoStatus myoStatus);
}
