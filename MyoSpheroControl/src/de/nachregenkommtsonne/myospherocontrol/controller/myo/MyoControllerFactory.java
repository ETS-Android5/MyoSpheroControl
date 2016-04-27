package de.nachregenkommtsonne.myospherocontrol.controller.myo;

import com.thalmic.myo.DeviceListener;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroMovementController;
import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class MyoControllerFactory {
	public MyoControllerFactory() {
	}

	public MyoController create(Context context, IServiceState serviceState,
			ISpheroMovementController spheroController) {
		SettingsEditor settingsEditor = new SettingsEditor();
		IMovementCalculator mMovementCalculator = new MovementCalculator();
		IMyoEvents myoEventHandler = new MyoEventHandler(mMovementCalculator, spheroController);
		MyoController myoController = new MyoController(context, myoEventHandler, settingsEditor, serviceState);

		DeviceListener deviceListener = new MyoDeviceListener(myoController, settingsEditor, myoEventHandler,
				serviceState);
		myoController.setDeviceListener(deviceListener);

		return myoController;
	}
}
