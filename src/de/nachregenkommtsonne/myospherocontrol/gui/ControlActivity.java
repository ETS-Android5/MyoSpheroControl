package de.nachregenkommtsonne.myospherocontrol.gui;

import com.thalmic.myo.scanner.ScanActivity;

import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService;
import de.nachregenkommtsonne.myospherocontrol.service.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.IBinderEvents;
import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.MyBinder;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ControlActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ControlFragment())
					.commit();
		}
	}

	public static class ControlFragment extends Fragment {

		GuiStateHinter _guiStateHinter;

		public ControlFragment() {
			_guiStateHinter = new GuiStateHinter();
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			Button startStopButton = (Button) rootView.findViewById(R.id.startStopButton);
			TextView linkUnlinkButton = (TextView)rootView.findViewById(R.id.linkUnlinkButton);

			startStopButton.setText(ServiceState.OBgetInstance().isRunning() ? "Stop" : "Start");

			startStopButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					buttonClicked();
				}
			});
			
			linkUnlinkButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					ServiceState state = _myBinder.getState();
					if (!state.isRunning())
						unlinkClicked();
					else{
				        Intent intent = new Intent(getActivity(), ScanActivity.class);
				        getActivity().startActivity(intent);
					}
				}
			});

			return rootView;
		}

		private void buttonClicked() {
			_myBinder.buttonClicked();
		}
		private void unlinkClicked() {
			_myBinder.unlinkClicked();
		}

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			Intent intent = new Intent(ControlFragment.this.getActivity(), BackgroundService.class);
			ControlFragment.this.getActivity().startService(intent);
		}

		public void onResume() {
			super.onResume();

			_myServiceConnection = new MyServiceConnection();
			Intent intent = new Intent(getActivity(), BackgroundService.class);
			getActivity().bindService(intent, _myServiceConnection, BIND_AUTO_CREATE);
		}

		public void onPause() {
			super.onPause();

			//TODO: stop service when not running here!
			getActivity().unbindService(_myServiceConnection);
		}

		public void onDestroy() {
			super.onDestroy();
		}
		
		private void updateUiOnUiThread() {
			final ServiceState state = _myBinder.getState();
			
			Activity activity = getActivity();
			
			if (activity == null)
				return;
			
			activity.runOnUiThread(new Runnable() {
				public void run() {
					updateUI(state);
				}
			});
		}

		private void updateUI(ServiceState serviceState) {
			TextView myoLinkedIcon = (TextView) getView().findViewById(R.id.myoLinkedIcon);
			TextView myoConnectedIcon = (TextView) getView().findViewById(R.id.myoConnectedIcon);
			TextView myoSyncronizedIcon = (TextView) getView().findViewById(R.id.myoSyncronizedIcon);
			TextView spheroDiscoveredIcon = (TextView) getView().findViewById(R.id.spheroDiscoveredIcon);
			TextView spheroConnectedIcon = (TextView) getView().findViewById(R.id.spheroConnectedIcon);
			TextView hintText = (TextView) getView().findViewById(R.id.hintText);
			Button startStopButton = (Button) getView().findViewById(R.id.startStopButton);
			TextView linkUnlinkButton = (TextView)getView().findViewById(R.id.linkUnlinkButton);

			MyoStatus myoStatus = serviceState.getMyoStatus();
			SpheroStatus spheroStatus = serviceState.getSpheroStatus();
			BluetoothState bluetoothStatus = serviceState.getBluetoothState();
			String hint = _guiStateHinter.getHint(serviceState);

			int myoLinkedDrawable = (myoStatus == MyoStatus.linked || myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			myoLinkedIcon.setCompoundDrawablesWithIntrinsicBounds(myoLinkedDrawable, 0, 0, 0);

			int myoConnectedDrawable = (myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			myoConnectedIcon.setCompoundDrawablesWithIntrinsicBounds(myoConnectedDrawable, 0, 0, 0);
			
			int myoSyncronizedDrawable = (myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			myoSyncronizedIcon.setCompoundDrawablesWithIntrinsicBounds(myoSyncronizedDrawable, 0, 0, 0);
			
			int spheroDiscoveredDrawable = (spheroStatus == SpheroStatus.connecting || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			spheroDiscoveredIcon.setCompoundDrawablesWithIntrinsicBounds(spheroDiscoveredDrawable, 0, 0, 0);
			
			int spheroConnectedDrawable = (spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			spheroConnectedIcon.setCompoundDrawablesWithIntrinsicBounds(spheroConnectedDrawable, 0, 0, 0);

			startStopButton.setText(serviceState.isRunning() ? "Stop" : "Start");

			hintText.setText(hint);
			
			if (myoStatus == MyoStatus.notLinked && serviceState.isRunning() && bluetoothStatus == BluetoothState.on){
				linkUnlinkButton.setText("Scan for Myo");
				linkUnlinkButton.setVisibility(View.VISIBLE);
			}
			else if (myoStatus != MyoStatus.notLinked && !serviceState.isRunning()){
				linkUnlinkButton.setText("Unlink");
				linkUnlinkButton.setVisibility(View.VISIBLE);
			}
			else{
				linkUnlinkButton.setVisibility(View.GONE);
			}
		}

		private MyBinder _myBinder;
		private MyServiceConnection _myServiceConnection;

		public class MyServiceConnection implements ServiceConnection {

			public void onServiceConnected(ComponentName name, IBinder service) {
				_myBinder = (MyBinder) service;
				_myBinder.setChangedListener(new IBinderEvents() {

					public void changed() {
						updateUiOnUiThread();
					}
				});

				updateUiOnUiThread();
			}

			public void onServiceDisconnected(ComponentName name) {
				_myBinder.setChangedListener(null);
			}
		}
	}
}
