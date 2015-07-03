package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.R;
import android.app.Activity;
import android.os.Bundle;

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
}
