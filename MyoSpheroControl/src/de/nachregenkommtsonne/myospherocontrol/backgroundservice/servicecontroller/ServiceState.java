package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import orbotix.sphero.Sphero;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroStatus;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

public class ServiceState
{
  private MyoStatus _myoStatus;
  private SpheroStatus _spheroStatus;
  private BluetoothState _bluetoothState;
  private boolean _running;
  private Myo _myo;
  private Hub _hub;
  private Sphero _sphero;
  private boolean _controlMode;
  private IGuiStateHinter _guiStateHinter;

  public ServiceState(IGuiStateHinter guiStateHinter)
  {
    _myoStatus = MyoStatus.notLinked;
    _spheroStatus = SpheroStatus.disconnected;
    _running = false;
    _guiStateHinter = guiStateHinter;
    _bluetoothState = BluetoothState.off;
  }

  public void onCreate(Context context)
  {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    _bluetoothState = (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) ? BluetoothState.on
        : BluetoothState.off;
  }

  public boolean isControlMode()
  {
    return _controlMode;
  }

  public void setControlMode(boolean controlMode)
  {
    _controlMode = controlMode;
  }

  public Myo getMyo()
  {
    return _myo;
  }

  public void setMyo(Myo myo)
  {
    _myo = myo;
  }

  public Hub getHub()
  {
    return _hub;
  }

  public void setHub(Hub hub)
  {
    _hub = hub;
  }

  public Sphero getSphero()
  {
    return _sphero;
  }

  public void setSphero(Sphero sphero)
  {
    _sphero = sphero;
  }

  public int getHint()
  {
    return _guiStateHinter.getHint(this);
  }

  public boolean isRunning()
  {
    return _running;
  }

  public void setRunning(boolean _running)
  {
    this._running = _running;
  }

  public BluetoothState getBluetoothState()
  {
    return _bluetoothState;
  }

  public void setBluetoothState(BluetoothState bluetoothStatus)
  {
    _bluetoothState = bluetoothStatus;
  }

  public void setMyoStatus(MyoStatus myoStatus)
  {
    _myoStatus = myoStatus;
  }

  public void setSpheroStatus(SpheroStatus spheroStatus)
  {
    _spheroStatus = spheroStatus;
  }

  public MyoStatus getMyoStatus()
  {
    return _myoStatus;
  }

  public SpheroStatus getSpheroStatus()
  {
    return _spheroStatus;
  }
}
