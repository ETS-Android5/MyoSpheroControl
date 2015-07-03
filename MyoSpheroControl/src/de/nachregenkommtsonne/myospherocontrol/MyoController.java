package de.nachregenkommtsonne.myospherocontrol;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.scanner.ScanActivity;

import android.content.Context;
import android.content.Intent;

//TODO: check if event listener is set
//TODO: Decompose
public class MyoController implements IMyoController
{
  private IMyoEvents _eventListener;
  private Context _context;
  private boolean _running;
  private boolean _connecting;
  private DeviceListener _listenerDelegate;
  private SettingsEditor _settingsEditor;
  
  public MyoController(Context context)
  {
    _context = context;
    _settingsEditor = new SettingsEditor(context);
    _listenerDelegate = new MyoDeviceListener(this, _settingsEditor);
    
    Hub hub = get_hub();

    hub.setSendUsageData(false);
  }

  public boolean is_running()
  {
    return _running;
  }

  public boolean is_connecting()
  {
    return _connecting;
  }

  private Hub get_hub()
  {
    return Hub.getInstance();
  }

  public void setEventListener(IMyoEvents eventListener)
  {
    _eventListener = eventListener;
  }

  public void start()
  {
    Hub hub = get_hub();

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
    Hub hub = get_hub();

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

    Hub hub = get_hub();
    connect(hub);
  }

  private void connect(Hub hub)
  {
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

  public void connectViaDialog()
  {
    Intent intent = new Intent(_context, ScanActivity.class);
    _context.startActivity(intent);
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

    Hub hub = get_hub();
    hub.attachByMacAddress(mac);

    onMyoStateChanged(MyoStatus.linked);
  }

  public void connectAndUnlinkButtonClicked()
  {
    if (!_running)
    {
      _settingsEditor.deleteMac();
      updateDisabledState();
    }
  }
}
