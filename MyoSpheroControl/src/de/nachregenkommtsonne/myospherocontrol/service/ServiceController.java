package de.nachregenkommtsonne.myospherocontrol.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.gui.ControlActivity;
import de.nachregenkommtsonne.myospherocontrol.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;

public class ServiceController implements IServiceController {

	IMyoController _myoController;
	ISpheroController _spheroController;
	private IServiceControllerEvents _serviceControllerEvents;
	ServiceState _state;
	private Context _context;
	private IMovementCalculator _mMovementCalculator;

	public ServiceController(IMyoController myoController,
			ISpheroController spheroController, Context context) {
		_context = context;
		_myoController = myoController;
		_spheroController = spheroController;

		_myoController.setEventListener(_myoEvents);
		_spheroController.setEventListener(_spheroEvents);
		_state = new ServiceState();
		_myoController.updateDisabledState();
		
		_mMovementCalculator = new MovementCalculator();

		IntentFilter filter = new IntentFilter(
				BluetoothAdapter.ACTION_STATE_CHANGED);
		_context.registerReceiver(_bluetoothEvents, filter);
	}

	public void setEventListener(
			IServiceControllerEvents serviceControllerEvents) {
		_serviceControllerEvents = serviceControllerEvents;
	}

	public void onChanged() {
		if (_serviceControllerEvents != null)
			_serviceControllerEvents.changed();

		updateNotification();
	}

	public ServiceState getState() {
		return _state;
	}

	private IMyoEvents _myoEvents = new MyoEvents(_state, _mMovementCalculator, _spheroController, this);

	private ISpheroEvents _spheroEvents = new SpheroEvents(this);

	private final BroadcastReceiver _bluetoothEvents = new ServiceControllerBroadcastReceiver(this);

	public void buttonClicked() {
		if (_state.isRunning()) {
			if (_state.getBluetoothState() == BluetoothState.on) {
				_spheroController.stop();
			}
			_myoController.stop();
		} else {
			_myoController.start();
			if (_state.getBluetoothState() == BluetoothState.on) {
				_myoController.startConnecting();
				_spheroController.start();
			}
		}

		_state.setRunning(!_state.isRunning());
		onChanged();
	}

	public void updateNotification() {
		if (_state.isRunning()) {

			Intent intent = new Intent(_context, ControlActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			PendingIntent pIntent = PendingIntent.getActivity(_context, 0,
					intent, 0);

			Notification n = new Notification.Builder(_context)
					.setContentTitle(_context.getString(R.string.app_name))
					.setContentText(_context.getString(_state.getHint()))
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentIntent(pIntent).setAutoCancel(false).setWhen(0)
					.setOngoing(true).build();

			NotificationManager notificationManager = (NotificationManager) _context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			notificationManager.notify(0, n);
		} else {
			NotificationManager notificationManager = (NotificationManager) _context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			notificationManager.cancel(0);
		}
	}

	public void unlinkClicked() {
		_myoController.connectAndUnlinkButtonClicked();
	}
}
