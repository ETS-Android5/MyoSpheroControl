package de.nachregenkommtsonne.myospherocontrol.controller;

import java.util.List;

import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.ConnectorState;
import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroEvents;

public class SpheroController implements ISpheroCapabilities {

	ControlFragment _placeholderFragment;
	ISpheroEvents _eventListener;
	boolean _running;

	public SpheroController(ControlFragment placeholderFragment, ISpheroEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
		_running = ConnectorState.getInstance().isRunning();
	}

	public void move(float direction, float speed) {
		if (ConnectorState.getInstance().getSphero() != null && _running)
			ConnectorState.getInstance().getSphero().drive(direction, speed);
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
			ConnectorState.getInstance().getSphero().stop();
			ConnectorState.getInstance().setSphero(null);
			
			RobotProvider.getDefaultProvider().disconnectControlledRobots();
			RobotProvider.getDefaultProvider().endDiscovery();
			RobotProvider.getDefaultProvider().shutdown();
		} catch (Exception ex) {
		}

		_eventListener.spheroStateChanged(SpheroStatus.disconnected);
		_running = false;
	}

	public void changeColor() {
		if (ConnectorState.getInstance().getSphero() != null && _running)
			ConnectorState.getInstance().getSphero().setColor(255, 0, 0);
	}

	ConnectionListener _connectionListener = new ConnectionListener() {

		public void onConnected(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.connected);
//			ConnectorState.getInstance().getSphero().getConfiguration().setPersistentFlag(PersistentOptionFlags.EnableVectorDrive, false);
	}

		public void onConnectionFailed(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.discovering);
			ConnectorState.getInstance().setSphero(null);

			RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		}

		public void onDisconnected(Robot arg0) {
			_eventListener.spheroStateChanged(SpheroStatus.discovering);
			ConnectorState.getInstance().setSphero(null);

			RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		}
	};

	DiscoveryListener _discoveryListener = new DiscoveryListener() {

		public void discoveryComplete(List<Sphero> arg0) {
		}

		public void onBluetoothDisabled() {
			_eventListener.bluetoothDisabled();
		}

		public void onFound(List<Sphero> spheros) {
			Sphero sphero = spheros.iterator().next();
			ConnectorState.getInstance().setSphero(sphero);
			RobotProvider.getDefaultProvider().connect(sphero);
			_eventListener.spheroStateChanged(SpheroStatus.connecting);
		}
	};

	public void halt() {
		if (ConnectorState.getInstance().getSphero() != null && _running)
			ConnectorState.getInstance().getSphero().stop();
	}
}
