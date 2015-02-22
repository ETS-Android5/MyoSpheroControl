package de.nachregenkommtsonne.myospherocontrol.myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyoController implements IMyoController {
	private static final String MYOMAC = "MYO_MAC";

	private IMyoEvents _eventListener;
	private Context _context;
	private SharedPreferences _sharedPref;
	private boolean _running;
	private boolean _connecting;

	public MyoController(Context context) {
		_context = context;
		_sharedPref = _context.getSharedPreferences(_context.getPackageName(),
				Context.MODE_PRIVATE);

		Hub hub = getHub();

		hub.setSendUsageData(false);
	}

	private Hub getHub() {
		return Hub.getInstance();
	}

	public void setEventListener(IMyoEvents eventListener) {
		_eventListener = eventListener;
	}

	public void start() {
		Hub hub = getHub();

		if (!hub.init(_context, _context.getPackageName())) {
			throw new RuntimeException("Error initializing Myo hub");
		}
		hub.addListener(_listenerDelegate);
		_running = true;
	}

	public void stop() {
		_running = false;
		_connecting = false;
		Hub hub = getHub();
		hub.removeListener(_listenerDelegate);
		hub.shutdown();
		updateDisabledState();
	}

	public void updateDisabledState() {
		String myoMac = getMac();
		if (myoMac == null) {
			onMyoStateChanged(MyoStatus.notLinked);
		}
		else {
			onMyoStateChanged(MyoStatus.linked);
		}
	}

	public void startConnecting() {
		_connecting = true;

		Hub hub = getHub();
		connect(hub);
	}

	private void connect(Hub hub) {
		String myoMac = getMac();
		if (myoMac == null) {
			onMyoStateChanged(MyoStatus.notLinked);
			hub.attachToAdjacentMyo();
		} else {
			connectToLinkedMyo(myoMac);
		}
	}

	public void stopConnecting() {
		stop();
		start();

		updateDisabledState();
		_connecting = false;
	}

	void onMyoStateChanged(MyoStatus myoStatus) {
		_eventListener.myoStateChanged(myoStatus);
	}

	void onMyoControlActivated() {
		_eventListener.myoControlActivated();
	}

	void onMyoControlDeactivated() {
		_eventListener.myoControlDeactivated();
	}

	void onMyoOrientationDataCollected(Myo myo, long timestamp,
			Quaternion rotation) {
		_eventListener.myoOrientationDataCollected(rotation, myo);
	}

	private void saveMac(String mac) {
		Editor edit = _sharedPref.edit();
		edit.putString(MYOMAC, mac);
		edit.apply();
	}

	private void deleteMac() {
		Editor edit = _sharedPref.edit();
		edit.remove(MYOMAC);
		edit.apply();
	}

	private String getMac() {
		return _sharedPref.getString(MYOMAC, null);
	}

	private void connectToLinkedMyo(String mac) {
		if (!_connecting)
			return;

		Hub hub = getHub();
		hub.attachByMacAddress(mac);

		onMyoStateChanged(MyoStatus.linked);
	}

	private DeviceListener _listenerDelegate = new AbstractDeviceListener() {
		public void onConnect(Myo myo, long timestamp) {
			onMyoStateChanged(MyoStatus.notSynced);
			saveMac(myo.getMacAddress());
		}

		public void onDisconnect(Myo myo, long timestamp) {
			if (_running && _connecting) {
				try {
					connectToLinkedMyo(getMac());
				}
				catch (Exception ex) {
				}
			}
		}

		public void onArmSync(Myo myo, long timestamp, Arm arm,
				XDirection xDirection) {
			onMyoStateChanged(MyoStatus.connected);
		}

		public void onArmUnsync(Myo myo, long timestamp) {
			if (_connecting) {
				onMyoStateChanged(MyoStatus.notSynced);
				try {
					onMyoControlDeactivated();
				}
				catch (Exception ex) {
				}
			}
		}

		public void onUnlock(Myo myo, long timestamp) {
		}

		public void onPose(Myo myo, long timestamp, Pose pose) {
			switch (pose) {
			case FIST:
				myo.unlock(Myo.UnlockType.HOLD);
				onMyoControlActivated();
				break;
			case FINGERS_SPREAD:
				onMyoControlDeactivated();
				myo.lock();
				break;
			default:
				break;
			}
		}

		public void onOrientationData(Myo myo, long timestamp,
				Quaternion rotation) {
			onMyoOrientationDataCollected(myo, timestamp, rotation);
		}
	};

	public void unlink() {
		deleteMac();
		updateDisabledState();
	}
}
