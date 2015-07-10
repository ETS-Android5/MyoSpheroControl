package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroStatus;

public class NotifyingServiceState implements IServiceState
{
	private IServiceState _serviceState;
	private ChangedNotifier _changedNotifier;

	public NotifyingServiceState(IServiceState serviceState)
	{
		_serviceState = serviceState;
	}

	public void setChangedNotifier(ChangedNotifier changedNotifier)
	{
		_changedNotifier = changedNotifier;
	}

	public void setSpheroStatus(SpheroStatus spheroStatus)
	{
		_serviceState.setSpheroStatus(spheroStatus);
		_changedNotifier.onChanged();
	}

	public SpheroStatus getSpheroStatus()
	{
		return _serviceState.getSpheroStatus();
	}

	public void setMyoStatus(MyoStatus myoStatus)
	{
		_serviceState.setMyoStatus(myoStatus);
		_changedNotifier.onChanged();
	}

	public MyoStatus getMyoStatus()
	{
		return _serviceState.getMyoStatus();
	}

	public void setBluetoothState(BluetoothStatus bluetoothStatus)
	{
		_serviceState.setBluetoothState(bluetoothStatus);
		_changedNotifier.onChanged();
	}

	public BluetoothStatus getBluetoothState()
	{
		return _serviceState.getBluetoothState();
	}

	public void setRunning(boolean running)
	{
		_serviceState.setRunning(running);
		_changedNotifier.onChanged();
	}

	public boolean isRunning()
	{
		return _serviceState.isRunning();
	}
}
