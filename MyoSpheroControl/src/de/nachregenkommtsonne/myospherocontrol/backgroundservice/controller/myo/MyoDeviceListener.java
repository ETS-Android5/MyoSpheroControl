package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

public class MyoDeviceListener extends AbstractDeviceListener
{
  private MyoController _myoController;
  private SettingsEditor _settingsEditor;

  public MyoDeviceListener(MyoController myoController, SettingsEditor settingsEditor)
  {
    _myoController = myoController;
    _settingsEditor = settingsEditor;
  }

  public void onConnect(Myo myo, long timestamp)
  {
    _myoController.onMyoStateChanged(MyoStatus.notSynced);
    _settingsEditor.saveMac(myo.getMacAddress());
  }

  public void onDisconnect(Myo myo, long timestamp)
  {
    if (_myoController.isRunning() && _myoController.isConnecting())
    {
      try
      {
        _myoController.connectToLinkedMyo(_settingsEditor.getMac());
      }
      catch (Exception ex)
      {
      }
    }
  }

  public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection)
  {
    _myoController.onMyoStateChanged(MyoStatus.connected);
  }

  public void onArmUnsync(Myo myo, long timestamp)
  {
    if (_myoController.isConnecting())
    {
      _myoController.onMyoStateChanged(MyoStatus.notSynced);
      try
      {
        _myoController.onMyoControlDeactivated();
      }
      catch (Exception ex)
      {
      }
    }
  }

  public void onUnlock(Myo myo, long timestamp)
  {
  }

  public void onPose(Myo myo, long timestamp, Pose pose)
  {
    switch (pose)
    {
    case FIST:
      myo.unlock(Myo.UnlockType.HOLD);
      _myoController.onMyoControlActivated();
      break;
    case FINGERS_SPREAD:
      _myoController.onMyoControlDeactivated();
      myo.lock();
      break;
    default:
      break;
    }
  }

  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation)
  {
    _myoController.onMyoOrientationDataCollected(myo, timestamp, rotation);
  }
}