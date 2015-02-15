package de.nachregenkommtsonne.myospherocontrol.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.ConnectorState;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoEvents;

public class MyoController implements IMyoCapabilities {

	ControlFragment _placeholderFragment;
	IMyoEvents _eventListener;
	SharedPreferences _sharedPref;
	boolean _running;
	boolean _synced;

	public MyoController(ControlFragment placeholderFragment, IMyoEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
		ConnectorState.getInstance().setHub(Hub.getInstance());
		_running = ConnectorState.getInstance().isRunning();
		_synced = ConnectorState.getInstance().getMyoStatus() == MyoStatus.connected;
	}

	public void start() {
		if (_running)
			return;

		if (!ConnectorState.getInstance().getHub().init(_placeholderFragment.getActivity(), _placeholderFragment.getActivity().getPackageName())) {
			Toast.makeText(_placeholderFragment.getActivity(), "Couldn't initialize Myo Hub", Toast.LENGTH_SHORT).show();
			return;
		}

		ConnectorState.getInstance().getHub().setSendUsageData(false);
		ConnectorState.getInstance().getHub().addListener(mListener);

		_sharedPref = _placeholderFragment.getActivity().getPreferences(
				Context.MODE_PRIVATE);

		String myomac = _sharedPref.getString("myomac", null);

		if (myomac == null) {
			_eventListener.myoStateChanged(MyoStatus.notLinked);
			ConnectorState.getInstance().getHub().attachToAdjacentMyo();
		}
		else {
			_eventListener.myoStateChanged(MyoStatus.connecting);
			ConnectorState.getInstance().getHub().attachByMacAddress(myomac);
		}

		_running = true;
	}

	public void stop() {
		if (!_running)
			return;

		ConnectorState.getInstance().getHub().removeListener(mListener);

		try {
			ConnectorState.getInstance().getHub().shutdown();
		} catch (Exception ex) {
		}

		_eventListener.myoStateChanged(MyoStatus.disconnected);
		_running = false;
	}

	private DeviceListener mListener = new AbstractDeviceListener() {
		public void onConnect(Myo myo, long timestamp) {
			_eventListener.myoStateChanged(MyoStatus.notSynced);
			ConnectorState.getInstance().setMyo(myo);

			Editor edit = _sharedPref.edit();
			edit.putString("myomac", ConnectorState.getInstance().getMyo().getMacAddress());
			edit.apply();
		}

		public void onDisconnect(Myo myo, long timestamp) {
			_eventListener.myoStateChanged(MyoStatus.connecting);
			ConnectorState.getInstance().setMyo(null);
			
			String myomac = _sharedPref.getString("myomac", null);
			ConnectorState.getInstance().getHub().attachByMacAddress(myomac);
		}

		public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
			_synced = true;
			_eventListener.myoStateChanged(MyoStatus.connected);
		}

		public void onArmUnsync(Myo myo, long timestamp) {
			_synced = false;
			_eventListener.myoControlDeactivated();
			_eventListener.myoStateChanged(MyoStatus.notSynced);
		}

		public void onUnlock(Myo myo, long timestamp) {
		}

		public void onPose(Myo myo, long timestamp, Pose pose) {
			switch (pose) {
			case FIST:
				myo.unlock(Myo.UnlockType.HOLD);
				_eventListener.myoControlActivated();
				break;
			case REST:
				break;
			case DOUBLE_TAP:
				break;
			case FINGERS_SPREAD:
				_eventListener.myoControlDeactivated();
				myo.lock();
				break;
			case UNKNOWN:
				break;
			case WAVE_IN:

				break;
			case WAVE_OUT:
				break;
			default:
				break;
			}
		}

		public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
			_eventListener.myoOrientationDataCollected(rotation, myo);
		}
	};
}
