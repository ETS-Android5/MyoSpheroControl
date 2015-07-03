package de.nachregenkommtsonne.myospherocontrol.gui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.service.MyBinder;

public class MyServiceConnection implements ServiceConnection {

	final ControlFragment controlFragment;

	MyServiceConnection(ControlFragment controlFragment) {
		this.controlFragment = controlFragment;
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
		this.controlFragment._myBinder = (MyBinder) service;
		this.controlFragment._myBinder.setChangedListener(new ServiceConnectionChangedListener(this));

		this.controlFragment.updateUiOnUiThread();
	}

	public void onServiceDisconnected(ComponentName name) {
		this.controlFragment._myBinder.setChangedListener(null);
	}
}