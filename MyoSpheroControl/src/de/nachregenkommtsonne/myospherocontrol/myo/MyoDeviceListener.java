package de.nachregenkommtsonne.myospherocontrol.myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

public class MyoDeviceListener extends AbstractDeviceListener
{
  private MyoController myoController;

  public MyoDeviceListener(MyoController myoController)
  {
    this.myoController = myoController;
  }

  public void onConnect(Myo myo, long timestamp)
  {
    this.myoController.onMyoStateChanged(MyoStatus.notSynced);
    this.myoController.saveMac(myo.getMacAddress());
  }

  public void onDisconnect(Myo myo, long timestamp)
  {
    if (this.myoController._running && this.myoController._connecting)
    {
      try
      {
        this.myoController.connectToLinkedMyo(this.myoController.getMac());
      } catch (Exception ex)
      {
      }
    }
  }

  public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection)
  {
    this.myoController.onMyoStateChanged(MyoStatus.connected);
  }

  public void onArmUnsync(Myo myo, long timestamp)
  {
    if (this.myoController._connecting)
    {
      this.myoController.onMyoStateChanged(MyoStatus.notSynced);
      try
      {
        this.myoController.onMyoControlDeactivated();
      } catch (Exception ex)
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
      this.myoController.onMyoControlActivated();
      break;
    case FINGERS_SPREAD:
      this.myoController.onMyoControlDeactivated();
      myo.lock();
      break;
    default:
      break;
    }
  }

  public void onOrientationData(Myo myo, long timestamp, Quaternion rotation)
  {
    this.myoController.onMyoOrientationDataCollected(myo, timestamp, rotation);
  }
}