package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroEvents;

public class ServiceController implements IServiceController
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private ServiceState _state;
  private IChangedNotifier _changedNotifier;
  private BroadcastReceiver _serviceControllerBroadcastReceiver;
  private ServiceBinder _serviceBinder;
  private Context _context;
  
  public ServiceController(
      IMyoController myoController,
      ISpheroController spheroController,
      IChangedNotifier changedNotifier,
      ServiceState serviceState,
      ISpheroEvents eventListener,
      BroadcastReceiver serviceControllerBroadcastReceiver,
      ServiceBinder serviceBinder,
      Context context)
  {
    _myoController = myoController;
    _spheroController = spheroController;
    _changedNotifier = changedNotifier;
    _state = serviceState;
    _serviceControllerBroadcastReceiver = serviceControllerBroadcastReceiver;
    _serviceBinder = serviceBinder;
    _context = context;
    
    _spheroController.setEventListener(eventListener);
    
		_serviceBinder.setServiceController(this);
  }

  public ServiceBinder get_serviceBinder()
	{
		return _serviceBinder;
	}

	public void start()
  {
    _spheroController.onCreate();
    _myoController.onCreate();
    _state.onCreate();
    
    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    _context.registerReceiver(_serviceControllerBroadcastReceiver, filter);
  }

  public void buttonClicked()
  {
    if (_state.isRunning())
    {
      if (_state.getBluetoothState() == BluetoothState.on)
      {
        _spheroController.stop();
      }
      _myoController.stop();
    }
    else
    {
      _myoController.start();
      if (_state.getBluetoothState() == BluetoothState.on)
      {
        _myoController.startConnecting();
        _spheroController.start();
      }
    }

    _state.setRunning(!_state.isRunning());
    _changedNotifier.onChanged();
  }

  public void unlinkClicked()
  {
    _myoController.connectAndUnlinkButtonClicked();
  }

  public void stop()
  {
    _state.setRunning(false);
    _changedNotifier.onChanged();
  }
}