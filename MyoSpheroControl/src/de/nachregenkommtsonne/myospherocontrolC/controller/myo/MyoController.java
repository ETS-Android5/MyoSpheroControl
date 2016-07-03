package de.nachregenkommtsonne.myospherocontrolC.controller.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.scanner.ScanActivity;

import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public class MyoController implements IMyoController
{
  private IMyoEvents _eventListener;
  private Context _context;
  private DeviceListener _deviceListener;
  private SettingsEditor _settingsEditor;
  private IServiceState _state;

  private boolean _running;
  private boolean _connecting;

  public MyoController(Context context, IMyoEvents eventListener, SettingsEditor settingsEditor,
      IServiceState state)
  {
    _context = context;
    _eventListener = eventListener;
    _settingsEditor = settingsEditor;
    _state = state;
  }

  public void setDeviceListener(DeviceListener deviceListener)
  {
    _deviceListener = deviceListener;
  }

  public void onCreate()
  {
    Hub hub = getHub();

    hub.setSendUsageData(false);

    _settingsEditor.onCreate(_context);
    updateDisabledState();
  }

  public boolean isRunning()
  {
    return _running;
  }

  public boolean isConnecting()
  {
    return _connecting;
  }

  private Hub getHub()
  {
    return Hub.getInstance();
  }

  public void start()
  {
    Hub hub = getHub();

    if (!hub.init(_context, _context.getPackageName()))
    {
      throw new RuntimeException("Error initializing Myo hub");
    }
    hub.addListener(_deviceListener);
    _running = true;
  }

  public void stop()
  {
    _running = false;
    _connecting = false;

    Hub hub = getHub();

    hub.removeListener(_deviceListener);
    hub.shutdown();
    updateDisabledState();
  }

  public void updateDisabledState()
  {
    String myoMac = _settingsEditor.getMac();
    if (myoMac == null)
    {
      _state.setMyoStatus(MyoStatus.notLinked);
    } else
    {
      _state.setMyoStatus(MyoStatus.linked);
    }
  }

  public void startConnecting()
  {
    _connecting = true;

    Hub hub = getHub();
    String myoMac = _settingsEditor.getMac();
    if (myoMac == null)
    {
      _state.setMyoStatus(MyoStatus.notLinked);
      hub.attachToAdjacentMyo();
    } else
    {
      connectToLinkedMyo(myoMac);
    }
  }

  public void stopConnecting()
  {
    stop();
    start();

    updateDisabledState();
    _connecting = false;

    _eventListener.stopControlMode();
  }

  public void connectToLinkedMyo(String mac)
  {
    if (!_connecting)
      return;

    Hub hub = getHub();
    hub.attachByMacAddress(mac);

    _state.setMyoStatus(MyoStatus.linked);
  }

  public void connectAndUnlinkButtonClicked()
  {
    if (!_running)
    {
      _settingsEditor.deleteMac();
      _state.setMyoStatus(MyoStatus.notLinked);
    } else
    {
      Intent intent = new Intent(_context, ScanActivity.class);
      _context.startActivity(intent);
    }
  }
}