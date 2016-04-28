package de.nachregenkommtsonne.myospherocontrol.controller.myo;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroMovementController;
import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementResult;

public class MyoEventHandler implements IMyoEvents {
	private IMovementCalculator _mMovementCalculator;
	private ISpheroMovementController _spheroController;
	private boolean _controlmode;

	public MyoEventHandler(IMovementCalculator mMovementCalculator, ISpheroMovementController spheroController) {
		_mMovementCalculator = mMovementCalculator;
		_spheroController = spheroController;

		_controlmode = false;
	}

	public void myoOrientationDataCollected(Quaternion rotation, Myo myo) {
		if (!_controlmode)
			return;

		MovementResult movementResult = _mMovementCalculator.calculate(rotation,
				myo.getXDirection() == XDirection.TOWARD_ELBOW);

		_spheroController.move(movementResult.getDirection(), movementResult.getSpeed());
		_spheroController.changeColor(movementResult.getRed(), movementResult.getGreen(), movementResult.getBlue());
	}

	public void myoControlDeactivated() {
		_controlmode = false;

		_spheroController.changeColor(0, 0, 1.0f);
		_spheroController.halt();
	}

	public void myoControlActivated() {
		_controlmode = true;

		_mMovementCalculator.resetRoll();
	}

	public void stopControlMode() {
		_controlmode = false;
	}
}
