package de.nachregenkommtsonne.myospherocontrol.service;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroController;

public class MyoEvents implements IMyoEvents {

	private ServiceState _state;
	private IMovementCalculator _mMovementCalculator;
	private ISpheroController _spheroController;
	private IServiceController _serviceController;
	
	public MyoEvents(
			ServiceState _state, 
			IMovementCalculator _mMovementCalculator, 
			ISpheroController _spheroController,
			IServiceController _serviceController) {
		this._state = _state;
		this._mMovementCalculator = _mMovementCalculator;
		this._spheroController = _spheroController;
		this._serviceController = _serviceController;
	}
	
	public ServiceState get_state() {
		return _state;
	}

	public IMovementCalculator get_mMovementCalculator() {
		return _mMovementCalculator;
	}

	public ISpheroController get_spheroController() {
		return _spheroController;
	}

	public IServiceController get_serviceController() {
		return _serviceController;
	}

	public void myoStateChanged(MyoStatus myoStatus) {
		_state.setMyoStatus(myoStatus);
		_serviceController.onChanged();
	}

	public void myoOrientationDataCollected(Quaternion rotation, Myo myo) {
		if (!_state.isControlMode())
			return;

		MovementResult movementResult = _mMovementCalculator.calculate(
				rotation, myo.getXDirection() == XDirection.TOWARD_ELBOW);
		
		_spheroController.move(movementResult.get_direction(),
				movementResult.get_speed());
		
		_spheroController.changeColor(
				movementResult.get_red(), 
				movementResult.get_green(), 
				movementResult.get_blue());
	}

	public void myoControlDeactivated() {
		_spheroController.changeColor(0, 0, 255);
		_state.setControlMode(false);
		_spheroController.halt();
	}

	public void myoControlActivated() {
		_mMovementCalculator.resetRoll();
		_state.setControlMode(true);
	}
}
