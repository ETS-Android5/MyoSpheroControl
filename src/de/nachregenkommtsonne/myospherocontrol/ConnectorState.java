package de.nachregenkommtsonne.myospherocontrol;

import orbotix.sphero.Sphero;
import android.bluetooth.BluetoothAdapter;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

public class ConnectorState {

	private MyoStatus _myoStatus;
	private SpheroStatus _spheroStatus;
	private BluetoothState _bluetoothState;
	private boolean _running;
	private Myo _myo;
	private Hub _hub;
	private Sphero _sphero;

	public Myo getMyo() {
		return _myo;
	}

	public void setMyo(Myo myo) {
		_myo = myo;
	}

	public Hub getHub() {
		return _hub;
	}

	public void setHub(Hub hub) {
		_hub = hub;
	}

	public Sphero getSphero() {
		return _sphero;
	}

	public void setSphero(Sphero sphero) {
		_sphero = sphero;
	}

	private static ConnectorState _instance = null;

	static {
		_instance = new ConnectorState();

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		_instance.setBluetoothEnabled((mBluetoothAdapter != null && mBluetoothAdapter.isEnabled())
						? BluetoothState.on
						: BluetoothState.off);

		_instance.setMyoStatus(MyoStatus.disconnected);
		_instance.setSpheroStatus(SpheroStatus.disconnected);
	}

	public static ConnectorState getInstance() {
		return _instance;
	}

	private ConnectorState() {
		_myoStatus = MyoStatus.notLinked;
		_spheroStatus = SpheroStatus.disconnected;
		_running = false;
		_bluetoothState = BluetoothState.off;
	}

	public boolean isRunning() {
		return _running;
	}

	public void setRunning(boolean _running) {
		this._running = _running;
	}

	public BluetoothState getBluetoothState() {
		return _bluetoothState;
	}

	public void setBluetoothEnabled(BluetoothState bluetoothStatus) {
		_bluetoothState = bluetoothStatus;
	}

	public void setMyoStatus(MyoStatus myoStatus) {
		_myoStatus = myoStatus;
	}

	public void setSpheroStatus(SpheroStatus spheroStatus) {
		_spheroStatus = spheroStatus;
	}

	public MyoStatus getMyoStatus() {
		return _myoStatus;
	}

	public SpheroStatus getSpheroStatus() {
		return _spheroStatus;
	}
}
