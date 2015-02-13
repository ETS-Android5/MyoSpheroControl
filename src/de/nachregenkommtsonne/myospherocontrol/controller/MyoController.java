package de.nachregenkommtsonne.myospherocontrol.controller;

import android.content.Intent;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;
import com.thalmic.myo.scanner.ScanActivity;

import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoEvents;

public class MyoController implements IMyoCapabilities {

	PlaceholderFragment _placeholderFragment;
	IMyoEvents _eventListener;
	Myo _myo;

	public MyoController(PlaceholderFragment placeholderFragment, IMyoEvents eventListener) {
		_placeholderFragment = placeholderFragment;
		_eventListener = eventListener;
	}

	public void stayUnlocked() {
		_myo.unlock(Myo.UnlockType.HOLD);
	}

	public void lock() {
		_myo.notifyUserAction();
	}

	public void initialize() {
		Hub hub = Hub.getInstance();

		if (!hub.init(_placeholderFragment.getActivity(), _placeholderFragment.getActivity().getPackageName())) {
			Toast.makeText(_placeholderFragment.getActivity(), "Couldn't initialize Hub", Toast.LENGTH_SHORT).show();
			_placeholderFragment.getActivity().finish();
		}

		hub.addListener(mListener);
	}
	
	public void showMyoConnect() {
		Intent intent = new Intent(_placeholderFragment.getActivity(), ScanActivity.class);
		_placeholderFragment.getActivity().startActivity(intent);
	}
	
	private DeviceListener mListener = new AbstractDeviceListener() {
		public void onConnect(Myo myo, long timestamp) {
			_eventListener.myoConnected();
			_myo = myo;
		}

		public void onDisconnect(Myo myo, long timestamp) {
			_eventListener.myoDisconnected();
			_myo = null;
		}

		public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
			_eventListener.myoSynced();
		}

		public void onArmUnsync(Myo myo, long timestamp) {
			_eventListener.myoUnsynced();
		}

		public void onUnlock(Myo myo, long timestamp) {
			_eventListener.myoUnlocked();
		}

		public void onPose(Myo myo, long timestamp, Pose pose) {
			switch (pose) {
			case FIST:
				myo.unlock(Myo.UnlockType.HOLD);
				//_eventListener.myoPoseFist();
				_eventListener.myoControlActivated();
				break;
			case REST:
				//_eventListener.myoPoseRest();
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
