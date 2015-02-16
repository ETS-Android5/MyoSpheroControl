package de.nachregenkommtsonne.myospherocontrol;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.controller.GuiController;
import de.nachregenkommtsonne.myospherocontrol.controller.GuiHandler;
import de.nachregenkommtsonne.myospherocontrol.controller.MyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.MyoHandler;
import de.nachregenkommtsonne.myospherocontrol.controller.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.controller.SpheroHandler;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiEvents;

public class Connector {
	ControlFragment _placeholderFragment;

	GuiController _guiController;
	MyoController _myoController;
	SpheroController _spheroController;

	IGuiEvents _guiHandler;
	MyoHandler _myoHandler;
	SpheroHandler _spheroHandler;

	public Connector(ControlFragment placeholderFragment) {
		_placeholderFragment = placeholderFragment;

		_guiController = new GuiController(_placeholderFragment);
		_spheroHandler = new SpheroHandler(_guiController);
		_spheroController = new SpheroController(_placeholderFragment, _spheroHandler);
		_myoHandler = new MyoHandler(_guiController, _spheroController);
		_myoController = new MyoController(_placeholderFragment, _myoHandler);
		_guiHandler = new GuiHandler(_myoController, _spheroController, _guiController);
	}

	// TODO: move this
	public void onCreateView(View rootView) {
		Button startButton = (Button) rootView.findViewById(R.id.startButton);

		startButton.setText(ConnectorState.getInstance().isRunning() ? "Stop" : "Start");

		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (!ConnectorState.getInstance().isRunning()) {
					_guiHandler.startClicked();
					((Button) arg0).setText(ConnectorState.getInstance().isRunning() ? "Stop" : "Start");
				}
				else {
					_guiHandler.stopClicked();
					((Button) arg0).setText(ConnectorState.getInstance().isRunning() ? "Stop" : "Start");
				}
			}
		});
	}

	// TODO: move this
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
						BluetoothAdapter.ERROR);
				switch (state) {
				case BluetoothAdapter.STATE_OFF:
					_guiHandler.bluetoothStateChanged(BluetoothState.off);
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					_guiHandler.bluetoothStateChanged(BluetoothState.turningOff);
					break;
				case BluetoothAdapter.STATE_ON:
					_guiHandler.bluetoothStateChanged(BluetoothState.on);
					break;
				case BluetoothAdapter.STATE_TURNING_ON:
					_guiHandler.bluetoothStateChanged(BluetoothState.turningOn);
					break;
				}
			}
		}
	};

	public void onCreate() {
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		_placeholderFragment.getActivity().registerReceiver(mReceiver, filter);
	}

	public void onDestroy() {
		_placeholderFragment.getActivity().unregisterReceiver(mReceiver);
	}

	boolean once = false;

	public void onResume() {
		_guiController.EnableView();
	}

	public void onPause() {
		_guiController.DisableView();
	}
}
