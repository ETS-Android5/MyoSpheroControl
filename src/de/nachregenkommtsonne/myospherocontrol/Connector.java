package de.nachregenkommtsonne.myospherocontrol;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.controller.GuiController;
import de.nachregenkommtsonne.myospherocontrol.controller.GuiHandler;
import de.nachregenkommtsonne.myospherocontrol.controller.MyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.MyoHandler;
import de.nachregenkommtsonne.myospherocontrol.controller.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.controller.SpheroHandler;

public class Connector {

	PlaceholderFragment _placeholderFragment;

	GuiController _guiController;
	MyoController _myoController;
	SpheroController _spheroController;

	GuiHandler _guiHandler;
	MyoHandler _myoHandler;
	SpheroHandler _spheroHandler;

	public Connector(PlaceholderFragment placeholderFragment) {
		_placeholderFragment = placeholderFragment;

		_guiController = new GuiController(_placeholderFragment);

		_spheroHandler = new SpheroHandler(_guiController);
		_spheroController = new SpheroController(_placeholderFragment, _spheroHandler);

		_myoHandler = new MyoHandler(_guiController, _spheroController);
		_myoController = new MyoController(_placeholderFragment, _myoHandler);


		_guiHandler = new GuiHandler(_myoController, _spheroController, _guiController);

	}

	public void initialize() {
		_myoController.initialize();
		_spheroController.initialize();
	}

	public void initialize(View rootView) {
		Button startButton = (Button) rootView.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				_guiHandler.startClicked();
			}
		});
	}
}
