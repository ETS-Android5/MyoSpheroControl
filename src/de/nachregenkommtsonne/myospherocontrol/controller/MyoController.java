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
import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoEvents;

public class MyoController implements IMyoCapabilities {

	PlaceholderFragment _placeholderFragment;
	IMyoEvents _eventListener;
	Myo _myo;
	SharedPreferences _sharedPref;
	Hub _hub;
	boolean _running;

	public MyoController(PlaceholderFragment placeholderFragment, IMyoEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
		_hub = Hub.getInstance();
		_running = false;
	}

	public void start() {
		if (_running)
			return;

		if (!_hub.init(_placeholderFragment.getActivity(), _placeholderFragment.getActivity().getPackageName())) {
			Toast.makeText(_placeholderFragment.getActivity(), "Couldn't initialize Hub", Toast.LENGTH_SHORT).show();
			return;
			// _placeholderFragment.getActivity().finish();
		}

		_hub.addListener(mListener);

		_sharedPref = _placeholderFragment.getActivity().getPreferences(
				Context.MODE_PRIVATE);

		String myomac = _sharedPref.getString("myomac", null);

		if (myomac == null) {
			_eventListener.myoStateChanged(MyoStatus.notLinked);
			_hub.attachToAdjacentMyo();
		}
		else {
			_eventListener.myoStateChanged(MyoStatus.connecting);
			_hub.attachByMacAddress(myomac);
		}

		_running = true;
	}

	public void stop() {
		if (!_running)
			return;

		_hub.removeListener(mListener);

		try {
			_hub.shutdown();
		} catch (Exception ex) {
		}

		_eventListener.myoStateChanged(MyoStatus.disconnected);
		_running = false;
	}

	public void initialize() {

	}

	private DeviceListener mListener = new AbstractDeviceListener() {
		public void onConnect(Myo myo, long timestamp) {
			_eventListener.myoStateChanged(MyoStatus.notSynced);
			_myo = myo;

			Editor edit = _sharedPref.edit();
			edit.putString("myomac", _myo.getMacAddress());
			edit.apply();
		}

		public void onDisconnect(Myo myo, long timestamp) {
			_eventListener.myoStateChanged(MyoStatus.connecting);
			_myo = null;

			String myomac = _sharedPref.getString("myomac", null);
			_hub.attachByMacAddress(myomac);
		}

		public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
			_eventListener.myoStateChanged(MyoStatus.connected);
		}

		public void onArmUnsync(Myo myo, long timestamp) {
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
