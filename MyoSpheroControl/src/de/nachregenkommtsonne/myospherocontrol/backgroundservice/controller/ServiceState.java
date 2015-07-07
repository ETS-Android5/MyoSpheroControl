package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller;

import orbotix.sphero.Sphero;
import android.bluetooth.BluetoothAdapter;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroStatus;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

public class ServiceState
{
  private MyoStatus _myoStatus;
  private SpheroStatus _spheroStatus;
  private BluetoothStatus _bluetoothStatus;
  
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
    _bluetoothStatus = BluetoothStatus.off;
    
    _running = false;
    _guiStateHinter = guiStateHinter;
  }

  public void onCreate()
  {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    _bluetoothStatus = (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) ? BluetoothStatus.on
        : BluetoothStatus.off;
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

  public void setRunning(boolean running)
  {
    _running = running;
  }

  public BluetoothStatus getBluetoothState()
  {
    return _bluetoothStatus;
  }

  public void setBluetoothState(BluetoothStatus bluetoothStatus)
  {
    _bluetoothStatus = bluetoothStatus;
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