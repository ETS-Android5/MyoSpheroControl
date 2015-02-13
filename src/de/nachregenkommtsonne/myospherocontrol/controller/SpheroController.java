package de.nachregenkommtsonne.myospherocontrol.controller;

import java.util.List;

import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroEvents;

public class SpheroController implements ISpheroCapabilities {

	PlaceholderFragment _placeholderFragment;
	ISpheroEvents _eventListener;
	Sphero _sphero;

	public SpheroController(PlaceholderFragment placeholderFragment, ISpheroEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
	}

	public void move(float direction, float speed) {
		_sphero.drive(direction, speed);
	}
	
	public void stop(){
		_sphero.stop();
	}

	public void changeColor() {
		_sphero.setColor(255, 0, 0);
	}

	public void initialize() {
		RobotProvider.getDefaultProvider().addConnectionListener(new ConnectionListener() {

			public void onConnected(Robot arg0) {
				_eventListener.spheroConnected();
			}

			public void onConnectionFailed(Robot arg0) {
				_sphero = null;
			}

			public void onDisconnected(Robot arg0) {
				_eventListener.spheroDisconnected();
				_sphero = null;
			}
		});

		RobotProvider.getDefaultProvider().addDiscoveryListener(new DiscoveryListener() {

			public void discoveryComplete(List<Sphero> arg0) {
			}

			public void onBluetoothDisabled() {
			}

			public void onFound(List<Sphero> sphero) {
				_sphero = sphero.iterator().next();
				RobotProvider.getDefaultProvider().connect(_sphero);
			}
		});

		boolean success = RobotProvider.getDefaultProvider().startDiscovery(_placeholderFragment.getActivity());
		if (!success) {
			Toast.makeText(_placeholderFragment.getActivity(), "Unable To start Discovery!", Toast.LENGTH_LONG).show();
		}
	}
}
