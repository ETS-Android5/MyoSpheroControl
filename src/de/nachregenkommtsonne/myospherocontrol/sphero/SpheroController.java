package de.nachregenkommtsonne.myospherocontrol.sphero;

import java.util.List;

import android.content.Context;
import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

public class SpheroController implements ISpheroController {

	Context _context;
	ISpheroEvents _eventListener;
	Sphero _sphero;
	boolean _running;
	boolean _connected;

	public SpheroController(Context context) {
		_context = context;

		RobotProvider robotProvider = getRobotProvider();

		robotProvider.addConnectionListener(_connectionListener);
		robotProvider.addDiscoveryListener(_discoveryListener);
	}

	@Override
	public void setEventListener(ISpheroEvents eventListener) {
		_eventListener = eventListener;
	}

	private RobotProvider getRobotProvider() {
		return RobotProvider.getDefaultProvider();
	}

	private void onSpheroStateChanged(SpheroStatus spheroStatus) {
		_eventListener.spheroStateChanged(spheroStatus);
	}

	public void move(float direction, float speed) {
		if (_connected)
			_sphero.drive(direction, speed);
	}

	public void changeColor() {
		if (_connected)
			_sphero.setColor(255, 0, 0);
	}

	public void halt() {
		if (_connected)
			_sphero.stop();
	}

	public void start() {
		startDiscovery();
		_running = true;
	}

	private void startDiscovery() {
		RobotProvider robotProvider = getRobotProvider();
		boolean success = robotProvider.startDiscovery(_context);
		if (!success) {
			//throw new RuntimeException();
		}

		onSpheroStateChanged(SpheroStatus.discovering);
	}

	private void startConnecting() {
		RobotProvider robotProvider = getRobotProvider();
		robotProvider.connect(_sphero);
		onSpheroStateChanged(SpheroStatus.connecting);
	}
	
	public void stop() {
		_running = false;
		RobotProvider robotProvider = getRobotProvider();

		robotProvider.shutdown();
		onSpheroStateChanged(SpheroStatus.disconnected);
	}

	ConnectionListener _connectionListener = new ConnectionListener() {

		public void onDisconnected(Robot arg0) {
			_connected = false;
			if (!_running)
				return;
			
			if (_sphero == null)
				startDiscovery();
			else {
				startConnecting();
			}
		}

		public void onConnectionFailed(Robot sphero) {
			_connected = false;
			if (!_running)
				return;

			if (_sphero == null)
				startDiscovery();
			else {
				startConnecting();
			}
		}

		public void onConnected(Robot arg0) {
			onSpheroStateChanged(SpheroStatus.connected);
			_connected = true;
		}
	};

	DiscoveryListener _discoveryListener = new DiscoveryListener() {

		public void onFound(List<Sphero> spheros) {
			_sphero = spheros.iterator().next();

			startConnecting();
		}

		public void onBluetoothDisabled() {
			_connected = false;
		}

		public void discoveryComplete(List<Sphero> spheros) {
		}
	};
}