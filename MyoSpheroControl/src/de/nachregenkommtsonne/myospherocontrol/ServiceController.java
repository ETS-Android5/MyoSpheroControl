package de.nachregenkommtsonne.myospherocontrol;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class ServiceController implements IServiceController
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private ServiceState _state;
  private Context _context;
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;

  public ServiceController(IMyoController myoController, ISpheroController spheroController, Context context,
      ServiceBinder binder, IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler,
      ServiceState serviceState)
  {
    _context = context;
    _myoController = myoController;
    _spheroController = spheroController;
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;

    _state = serviceState;

    MyoEventHandler myoEventHandler = new MyoEventHandler(_state, new MovementCalculator(), _spheroController,
        _serviceControllerStatusChangedHandler);

    _myoController.setEventListener(myoEventHandler);
    SpheroEventHandler eventListener = new SpheroEventHandler(_serviceControllerStatusChangedHandler, _spheroController,
        _state);
    _spheroController.setEventListener(eventListener);

    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver = new ServiceControllerBroadcastReceiver(
        _serviceControllerStatusChangedHandler, _state, _myoController, _spheroController);
    _context.registerReceiver(serviceControllerBroadcastReceiver, filter);

    _myoController.updateDisabledState();
  }

  public IMyoController get_myoController()
  {
    return _myoController;
  }

  public ISpheroController get_spheroController()
  {
    return _spheroController;
  }

  public ServiceState get_state()
  {
    return _state;
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
    _serviceControllerStatusChangedHandler.onChanged();
  }

  public void unlinkClicked()
  {
    _myoController.connectAndUnlinkButtonClicked();
  }
}
