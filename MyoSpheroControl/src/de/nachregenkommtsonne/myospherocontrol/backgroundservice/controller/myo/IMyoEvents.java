package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;

public interface IMyoEvents
{
  void myoControlActivated();

  void myoControlDeactivated();

  void myoOrientationDataCollected(Quaternion rotation, Myo myo);

  void myoStateChanged(MyoStatus myoStatus);

  void setChangedNotifier(ChangedNotifier changedNotifier);
}
