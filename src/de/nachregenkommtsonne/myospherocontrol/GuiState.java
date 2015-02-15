package de.nachregenkommtsonne.myospherocontrol;

import android.bluetooth.BluetoothAdapter;

public class GuiState {

	private MyoStatus _myoStatus;
	private SpheroStatus _spheroStatus;
	private BluetoothState _bluetoothState;
	private boolean _running;

	public GuiState() {
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

	public BluetoothState isBluetoothEnabled() {
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
