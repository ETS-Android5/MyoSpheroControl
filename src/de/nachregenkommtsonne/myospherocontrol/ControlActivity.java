package de.nachregenkommtsonne.myospherocontrol;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		Connector _connector;
		
		public ControlFragment() {
			_connector = new Connector(this);
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);

			_connector.onCreateView(rootView);
			return rootView;
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			_connector.onActivityCreated();
		}
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			_connector.onCreate();
		}
		
		public void onResume() {
			super.onResume();
			_connector.onResume();
		}
		
		@Override
		public void onPause() {
			super.onPause();
			_connector.onPause();
		}
		
		public void onDestroy() {
			super.onDestroy();
			_connector.onDestroy();
		}
	}
}
