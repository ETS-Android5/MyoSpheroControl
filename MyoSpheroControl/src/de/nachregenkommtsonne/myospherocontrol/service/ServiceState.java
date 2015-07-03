package de.nachregenkommtsonne.myospherocontrol.service;

import orbotix.sphero.Sphero;
import android.bluetooth.BluetoothAdapter;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

import de.nachregenkommtsonne.myospherocontrol.gui.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

public class ServiceState
{
  private static ServiceState OB_instance = null;

  static
  {
    OB_instance = new ServiceState();

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    OB_instance.setBluetoothState(
        (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) ? BluetoothState.on : BluetoothState.off);

    OB_instance.setMyoStatus(MyoStatus.disconnected);
    OB_instance.setSpheroStatus(SpheroStatus.disconnected);
  }
  
  private MyoStatus _myoStatus;
  private SpheroStatus _spheroStatus;
  private BluetoothState _bluetoothState;
  private boolean _running;
  private Myo _myo;
  private Hub _hub;
  private Sphero _sphero;
  private boolean _controlMode;
  private GuiStateHinter _guGuiStateHinter;

  public ServiceState()
  {
    _myoStatus = MyoStatus.notLinked;
    _spheroStatus = SpheroStatus.disconnected;
    _running = false;
    _guGuiStateHinter = new GuiStateHinter();

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

  public static ServiceState OBgetInstance()
  {
    return OB_instance;
  }

  public int getHint()
  {
    return _guGuiStateHinter.getHint(this);
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
