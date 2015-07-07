package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

public class ServiceController implements IServiceController
{
  private Context _context;
  private ServiceState _serviceState;
  private ISpheroController _spheroController;
  private IMyoController _myoController;
  private BroadcastReceiver _bluetoothController;
  private ServiceBinder _serviceBinder;
  
  public ServiceController(
      Context context,
      ServiceState serviceState,
      ISpheroController spheroController,
      IMyoController myoController,
      BroadcastReceiver bluetoothController,
      ServiceBinder serviceBinder)
  {
    _context = context;
    _serviceState = serviceState;
    _spheroController = spheroController;
    _myoController = myoController;
    _bluetoothController = bluetoothController;
    _serviceBinder = serviceBinder;
  }

  public ServiceBinder get_serviceBinder()
	{
		return _serviceBinder;
	}

	public void start()
  {
    _spheroController.onCreate();
    _myoController.onCreate();
    _serviceState.onCreate();
    
    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    _context.registerReceiver(_bluetoothController, filter);
  }

  public void stop()
  {
    _serviceState.setRunning(false);
  }
}