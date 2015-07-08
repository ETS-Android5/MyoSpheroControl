package de.nachregenkommtsonne.myospherocontrol.controller.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.scanner.ScanActivity;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;
import android.content.Context;
import android.content.Intent;

//TODO: check if event listener is set
//TODO: Decompose
public class MyoController implements IMyoController
{
  private IMyoEvents _eventListener;
  private Context _context;
  private DeviceListener _listenerDelegate;
  private SettingsEditor _settingsEditor;
  private ServiceState _state;

  private boolean _running;
  private boolean _connecting;

  public MyoController(Context context, IMyoEvents eventListener, SettingsEditor settingsEditor, ServiceState state)
  {
    _context = context;
    _eventListener = eventListener;
    _settingsEditor = settingsEditor;
    _state = state;

    _listenerDelegate = new MyoDeviceListener(this, _settingsEditor);
  }

  public void setChangedNotifier(ChangedNotifier changedNotifier)
  {
    _eventListener.setChangedNotifier(changedNotifier);
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
    hub.addListener(_listenerDelegate);
    _running = true;
  }

  public void stop()
  {
    _running = false;
    _connecting = false;

    Hub hub = getHub();

    hub.removeListener(_listenerDelegate);
    hub.shutdown();
    updateDisabledState();
  }

  public void updateDisabledState()
  {
    String myoMac = _settingsEditor.getMac();
    if (myoMac == null)
    {
      onMyoStateChanged(MyoStatus.notLinked);
    }
    else
    {
      onMyoStateChanged(MyoStatus.linked);
    }
  }

  public void startConnecting()
  {
    _connecting = true;

    Hub hub = getHub();
    String myoMac = _settingsEditor.getMac();
    if (myoMac == null)
    {
      onMyoStateChanged(MyoStatus.notLinked);
      hub.attachToAdjacentMyo();
    }
    else
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
  }

  public void onMyoStateChanged(MyoStatus myoStatus)
  {
    _eventListener.myoStateChanged(myoStatus);
  }

  public void onMyoControlActivated()
  {
    _eventListener.myoControlActivated();
  }

  public void onMyoControlDeactivated()
  {
    _eventListener.myoControlDeactivated();
  }

  public void onMyoOrientationDataCollected(Myo myo, long timestamp, Quaternion rotation)
  {
    _eventListener.myoOrientationDataCollected(rotation, myo);
  }

  public void connectToLinkedMyo(String mac)
  {
    if (!_connecting)
      return;

    Hub hub = getHub();
    hub.attachByMacAddress(mac);

    onMyoStateChanged(MyoStatus.linked);
  }

  public void connectAndUnlinkButtonClicked()
  {
    if (!_running)
    {
      _settingsEditor.deleteMac();
      _state.setMyoStatus(MyoStatus.notLinked);
    }
    else
    {
      Intent intent = new Intent(_context, ScanActivity.class);
      _context.startActivity(intent);
    }
  }
}
