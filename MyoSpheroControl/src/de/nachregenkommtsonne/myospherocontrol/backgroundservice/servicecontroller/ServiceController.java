package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

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

  public void stop()
  {
    _state.setRunning(false);
    _changedNotifier.onChanged();
  }
}