package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

			Button startButton = (Button) rootView.findViewById(R.id.startButton);
			TextView textView2 = (TextView)rootView.findViewById(R.id.textView2);

			startButton.setText(ServiceState.OBgetInstance().isRunning() ? "Stop" : "Start");

			startButton.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					buttonClicked();
				}
			});
			
			textView2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					unlinkClicked();
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
			ImageView myoLinkedIcon = (ImageView) getView().findViewById(R.id.myoLinkedIcon);
			ImageView myoConnectedIcon = (ImageView) getView().findViewById(R.id.myoConnectedIcon);
			ImageView myoSyncronizedIcon = (ImageView) getView().findViewById(R.id.myoSyncronizedIcon);
			ImageView spheroDiscoveredIcon = (ImageView) getView().findViewById(R.id.spheroDiscoveredIcon);
			ImageView spheroConnectedIcon = (ImageView) getView().findViewById(R.id.spheroConnectedIcon);
			TextView hintText = (TextView) getView().findViewById(R.id.hintText);
			Button button = (Button) getView().findViewById(R.id.startButton);
			TextView textView2 = (TextView)getView().findViewById(R.id.textView2);

			MyoStatus myoStatus = serviceState.getMyoStatus();
			SpheroStatus spheroStatus = serviceState.getSpheroStatus();
			String hint = _guiStateHinter.getHint(serviceState);

			myoLinkedIcon.setImageResource((myoStatus == MyoStatus.linked || myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
			myoConnectedIcon.setImageResource((myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
			myoSyncronizedIcon.setImageResource((myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
			spheroDiscoveredIcon.setImageResource((spheroStatus == SpheroStatus.connecting || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
			spheroConnectedIcon.setImageResource((spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);

			button.setText(serviceState.isRunning() ? "Stop" : "Start");

			hintText.setText(hint);
			textView2.setVisibility(myoStatus == MyoStatus.notLinked || serviceState.isRunning() ? View.GONE : View.VISIBLE);
		}

		private MyBinder _myBinder;
		private MyServiceConnection _myServiceConnection;

		public class MyServiceConnection implements ServiceConnection {

			public void onServiceConnected(ComponentName name, IBinder service) {
				_myBinder = (MyBinder) service;
				_myBinder.setChangedListener(new IBinderEvents() {

					@Override
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
