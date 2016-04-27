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
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
			TextView linkUnlinkButton = (TextView) rootView.findViewById(R.id.linkUnlinkButton);

			String startLabel = getString(R.string.startLabel);
			String stopLabel = getString(R.string.stopLabel);
			
			startStopButton.setText(ServiceState.OBgetInstance().isRunning() ? stopLabel : startLabel);

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
					else {
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

			// TODO: stop service when not running here!
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

		@SuppressWarnings("deprecation")
		private void updateUI(ServiceState serviceState) {
			ImageView myoLinkedIcon = (ImageView) getView().findViewById(R.id.myoLinkedIcon);
			ImageView myoConnectedIcon = (ImageView) getView().findViewById(R.id.myoConnectedIcon);
			ImageView myoSyncronizedIcon = (ImageView) getView().findViewById(R.id.myoSyncronizedIcon);
			ImageView spheroDiscoveredIcon = (ImageView) getView().findViewById(R.id.spheroDiscoveredIcon);
			ImageView spheroConnectedIcon = (ImageView) getView().findViewById(R.id.spheroConnectedIcon);
			TextView hintText = (TextView) getView().findViewById(R.id.hintText);
			Button startStopButton = (Button) getView().findViewById(R.id.startStopButton);
			TextView linkUnlinkButton = (TextView) getView().findViewById(R.id.linkUnlinkButton);

			MyoStatus myoStatus = serviceState.getMyoStatus();
			SpheroStatus spheroStatus = serviceState.getSpheroStatus();
			BluetoothState bluetoothStatus = serviceState.getBluetoothState();

			int hintResource = _guiStateHinter.getHint(serviceState);
			String hint = getActivity().getString(hintResource);
			Resources resources = getActivity().getResources();

			int myoLinkedDrawableResource = (myoStatus == MyoStatus.linked || myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			Drawable myoLinkedDrawable = resources.getDrawable(myoLinkedDrawableResource);
			myoLinkedIcon.setImageDrawable(myoLinkedDrawable);

			int myoConnectedDrawableResource = (myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			Drawable myoConnectedDrawable = resources.getDrawable(myoConnectedDrawableResource);
			myoConnectedIcon.setImageDrawable(myoConnectedDrawable);

			int myoSyncronizedDrawableResource = (myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			Drawable myoSyncronizedDrawable = resources.getDrawable(myoSyncronizedDrawableResource);
			myoSyncronizedIcon.setImageDrawable(myoSyncronizedDrawable);

			int spheroDiscoveredDrawableResource = (spheroStatus == SpheroStatus.connecting || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			Drawable spheroDiscoveredDrawable = resources.getDrawable(spheroDiscoveredDrawableResource);
			spheroDiscoveredIcon.setImageDrawable(spheroDiscoveredDrawable);

			int spheroConnectedDrawableResource = (spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
			Drawable spheroConnectedDrawable = resources.getDrawable(spheroConnectedDrawableResource);
			spheroConnectedIcon.setImageDrawable(spheroConnectedDrawable);

			String startLabel = getString(R.string.startLabel);
			String stopLabel = getString(R.string.stopLabel);
			startStopButton.setText(serviceState.isRunning() ? stopLabel : startLabel);

			hintText.setText(hint);

			if (myoStatus == MyoStatus.notLinked && serviceState.isRunning() && bluetoothStatus == BluetoothState.on) {
				String linkLabel = getString(R.string.clickToLink);
				linkUnlinkButton.setText(linkLabel);
				linkUnlinkButton.setVisibility(View.VISIBLE);
			}
			else if (myoStatus != MyoStatus.notLinked && !serviceState.isRunning()) {
				String unlinkLabel = getString(R.string.clickToUnlink);
				linkUnlinkButton.setText(unlinkLabel);
				linkUnlinkButton.setVisibility(View.VISIBLE);
			}
			else {
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
