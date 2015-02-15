package de.nachregenkommtsonne.myospherocontrol.controller;

import java.util.List;

import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroEvents;

public class SpheroController implements ISpheroCapabilities {

	ControlFragment _placeholderFragment;
	ISpheroEvents _eventListener;
	Sphero _sphero;
	boolean _running;

	public SpheroController(ControlFragment placeholderFragment, ISpheroEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
		_running = false;
	}

	public void move(float direction, float speed) {
		if (_sphero != null && _running)
			_sphero.drive(direction, speed);
	}

	public void start() {
		if (_running)
			return;

		RobotProvider.getDefaultProvider().addConnectionListener(_connectionListener);
		RobotProvider.getDefaultProvider().addDiscoveryListener(_discoveryListener);

		boolean success = RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		if (!success) {
			Toast.makeText(_placeholderFragment.getActivity(), "Unable To start Sphero Discovery!", Toast.LENGTH_LONG).show();
			_eventListener.spheroStateChanged(SpheroStatus.disconnected);
		}
		else {
			_eventListener.spheroStateChanged(SpheroStatus.discovering);
		}

		_running = true;
	}

	public void stop() {
		if (!_running)
			return;
		
		RobotProvider.getDefaultProvider().removeConnectionListener(_connectionListener);
		RobotProvider.getDefaultProvider().removeDiscoveryListener(_discoveryListener);

		try {
			_sphero.stop();
			_sphero = null;
			RobotProvider.getDefaultProvider().disconnectControlledRobots();
			RobotProvider.getDefaultProvider().endDiscovery();
			RobotProvider.getDefaultProvider().shutdown();
		} catch (Exception ex) {
		}

		_eventListener.spheroStateChanged(SpheroStatus.disconnected);
		_running = false;
	}

	public void changeColor() {
		if (_sphero != null && _running)
			_sphero.setColor(255, 0, 0);
	}

	ConnectionListener _connectionListener = new ConnectionListener() {

		public void onConnected(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.connected);
		}

		public void onConnectionFailed(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.discovering);

			_sphero = null;
			RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		}

		public void onDisconnected(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.discovering);
			_sphero = null;
			RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		}
	};

	DiscoveryListener _discoveryListener = new DiscoveryListener() {

		public void discoveryComplete(List<Sphero> arg0) {
		}

		public void onBluetoothDisabled() {
			_eventListener.bluetoothDisabled();
		}

		public void onFound(List<Sphero> sphero) {
			_sphero = sphero.iterator().next();
			RobotProvider.getDefaultProvider().connect(_sphero);
			_eventListener.spheroStateChanged(SpheroStatus.connecting);
		}
	};

	public void initialize() {
	}

	public void halt() {
		if (_sphero != null && _running)
			_sphero.stop();
	}
}
