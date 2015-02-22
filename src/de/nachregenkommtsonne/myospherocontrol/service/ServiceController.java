package de.nachregenkommtsonne.myospherocontrol.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.gui.ControlActivity;
import de.nachregenkommtsonne.myospherocontrol.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.MovementCalculator.MovementResult;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

public class ServiceController {

	private IMyoController _myoController;
	private ISpheroController _spheroController;
	private IServiceControllerEvents _serviceControllerEvents;
	private ServiceState _state;
	private Context _context;
	private MovementCalculator _mMovementCalculator;

	public ServiceController(IMyoController myoController, ISpheroController spheroController, Context context) {
		_context = context;
		_myoController = myoController;
		_spheroController = spheroController;

		_myoController.setEventListener(_myoEvents);
		_spheroController.setEventListener(_spheroEvents);
		_state = new ServiceState();
		_mMovementCalculator = new MovementCalculator();

		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		_context.registerReceiver(_bluetoothEvents, filter);
	}

	public void setEventListener(IServiceControllerEvents serviceControllerEvents) {
		_serviceControllerEvents = serviceControllerEvents;
	}

	private void onChanged() {
		if (_serviceControllerEvents != null)
			_serviceControllerEvents.changed();

		updateNotification();
	}

	public ServiceState getState() {
		return _state;
	}

	private IMyoEvents _myoEvents = new IMyoEvents() {

		public void myoStateChanged(MyoStatus myoStatus) {
			_state.setMyoStatus(myoStatus);
			onChanged();
		}

		public void myoOrientationDataCollected(Quaternion rotation, Myo myo) {
			if (!_state.isControlMode())
				return;

			MovementResult movementResult = _mMovementCalculator.calculate(rotation, myo.getXDirection() == XDirection.TOWARD_ELBOW);
			_spheroController.move(movementResult.get_direction(), movementResult.get_speed());
		}

		public void myoControlDeactivated() {
			_state.setControlMode(false);
			_spheroController.halt();
		}

		public void myoControlActivated() {
			_mMovementCalculator.resetRoll();
			_state.setControlMode(true);
		}
	};

	private ISpheroEvents _spheroEvents = new ISpheroEvents() {

		public void spheroStateChanged(SpheroStatus spheroStatus) {
			_state.setSpheroStatus(spheroStatus);
			onChanged();
		}

		public void bluetoothDisabled() {
		}
	};

	private final BroadcastReceiver _bluetoothEvents = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
						BluetoothAdapter.ERROR);
				switch (state) {
				case BluetoothAdapter.STATE_OFF:
					// TODO: fix bug when startet with bt turned off
					_state.setBluetoothEnabled(BluetoothState.off);
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					_state.setControlMode(false);
					_state.setBluetoothEnabled(BluetoothState.turningOff);

					if (_state.isRunning()) {
						_myoController.stop();
						_spheroController.stop();
					}

					break;
				case BluetoothAdapter.STATE_ON:
					// TODO: fix myo when bt turned on in background not
					// connecting
					_state.setBluetoothEnabled(BluetoothState.on);

					if (_state.isRunning()) {
						_myoController.start();
						_spheroController.start();
					}

					break;
				case BluetoothAdapter.STATE_TURNING_ON:
					_state.setBluetoothEnabled(BluetoothState.turningOn);
					break;
				}
				onChanged();
			}
		}
	};

	public void buttonClicked() {
		if (_state.isRunning()) {
			_myoController.stop();
			_spheroController.stop();
		}
		else {
			if (_state.getBluetoothState() == BluetoothState.on) {
				_myoController.start();
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
			PendingIntent pIntent = PendingIntent.getActivity(_context, 0, intent, 0);

			Notification n = new Notification.Builder(_context)
					.setContentTitle("Myo Sphero Control")
					.setContentText(_state.getHint())
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentIntent(pIntent)
					.setAutoCancel(false)
					.setWhen(0)
					.setOngoing(true)
					.build();

			NotificationManager notificationManager =
					(NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

			notificationManager.notify(0, n);
		}
		else {
			NotificationManager notificationManager =
					(NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

			notificationManager.cancel(0);
		}
	}
}
