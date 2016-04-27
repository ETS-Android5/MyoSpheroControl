package de.nachregenkommtsonne.myospherocontrol.controller.myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class MyoDeviceListener extends AbstractDeviceListener
{
  private MyoController _myoController;
  private SettingsEditor _settingsEditor;
  private IMyoEvents _eventListener;
  private IServiceState _state;

  public MyoDeviceListener(MyoController myoController, SettingsEditor settingsEditor, IMyoEvents eventListener, IServiceState state)
  {
    _myoController = myoController;
    _settingsEditor = settingsEditor;
    _eventListener = eventListener;
    _state = state;
  }

  public void onConnect(Myo myo, long timestamp)
  {
  	_state.setMyoStatus(MyoStatus.notSynced);
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
  	_state.setMyoStatus(MyoStatus.connected);
  }

  public void onArmUnsync(Myo myo, long timestamp)
  {
    if (_myoController.isConnecting())
    {
    	_state.setMyoStatus(MyoStatus.notSynced);
      try
      {
      	_eventListener.myoControlDeactivated();
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
      _eventListener.myoControlActivated();
      break;
    case FINGERS_SPREAD:
    	_eventListener.myoControlDeactivated();
      myo.lock();
      break;
    default:
      break;
    }
  }

  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation)
  {
  	_eventListener.myoOrientationDataCollected(rotation, myo);
  }
}