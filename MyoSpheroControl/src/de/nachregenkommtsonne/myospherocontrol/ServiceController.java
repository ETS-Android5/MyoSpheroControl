package de.nachregenkommtsonne.myospherocontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.R;

public class ServiceController implements IServiceController
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private ServiceBinder _binder;
  private ServiceState _state;
  private Context _context;
  private IMovementCalculator _mMovementCalculator;
  private IMyoEvents _myoEvents;
  private ISpheroEvents _spheroEvents;
  private BroadcastReceiver _bluetoothEvents;

  public ServiceController(IMyoController myoController, ISpheroController spheroController, Context context,
      ServiceBinder binder)
  {
    _binder = binder;
    _context = context;
    _myoController = myoController;
    _spheroController = spheroController;

    _state = new ServiceState();
    _spheroEvents = new SpheroEventHandler(this);
    _bluetoothEvents = new ServiceControllerBroadcastReceiver(this);
    _mMovementCalculator = new MovementCalculator();

    _myoEvents = new MyoEventHandler(_state, _mMovementCalculator, _spheroController, this);

    _myoController.setEventListener(_myoEvents);
    _spheroController.setEventListener(_spheroEvents);

    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    _context.registerReceiver(_bluetoothEvents, filter);

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

  public void onChanged()
  {
    _binder.onChanged();

    updateNotification();
  }

  public ServiceState getState()
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
    onChanged();
  }

  public void updateNotification()
  {
    if (_state.isRunning())
    {

      Intent intent = new Intent(_context, ControlActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pIntent = PendingIntent.getActivity(_context, 0, intent, 0);

      Notification n = new Notification.Builder(_context).setContentTitle(_context.getString(R.string.app_name))
          .setContentText(_context.getString(_state.getHint())).setSmallIcon(R.drawable.ic_launcher)
          .setContentIntent(pIntent).setAutoCancel(false).setWhen(0).setOngoing(true).build();

      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.notify(0, n);
    }
    else
    {
      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.cancel(0);
    }
  }

  public void unlinkClicked()
  {
    _myoController.connectAndUnlinkButtonClicked();
  }
}
