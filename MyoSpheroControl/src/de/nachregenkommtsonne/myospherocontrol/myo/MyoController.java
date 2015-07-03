package de.nachregenkommtsonne.myospherocontrol.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.scanner.ScanActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//TODO: check if event listener is set
public class MyoController implements IMyoController
{
  private static final String MYOMAC = "MYO_MAC";

  private IMyoEvents _eventListener;
  private Context _context;
  private SharedPreferences _sharedPref;
  public boolean _running;
  public boolean _connecting;
  private DeviceListener _listenerDelegate;

  public MyoController(Context context)
  {
    _context = context;
    _sharedPref = _context.getSharedPreferences(_context.getPackageName(), Context.MODE_PRIVATE);
    _listenerDelegate = new MyoDeviceListener(this);
    
    Hub hub = getHub();

    hub.setSendUsageData(false);
  }

  private Hub getHub()
  {
    return Hub.getInstance();
  }

  public void setEventListener(IMyoEvents eventListener)
  {
    _eventListener = eventListener;
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
    String myoMac = getMac();
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
    connect(hub);
  }

  private void connect(Hub hub)
  {
    String myoMac = getMac();
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

  public void saveMac(String mac)
  {
    Editor edit = _sharedPref.edit();
    edit.putString(MYOMAC, mac);
    edit.apply();
  }

  private void deleteMac()
  {
    Editor edit = _sharedPref.edit();
    edit.remove(MYOMAC);
    edit.apply();
  }

  public String getMac()
  {
    return _sharedPref.getString(MYOMAC, null);
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
      deleteMac();
      updateDisabledState();
    }
  }
}
