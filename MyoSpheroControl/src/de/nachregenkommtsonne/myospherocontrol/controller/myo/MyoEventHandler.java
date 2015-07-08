package de.nachregenkommtsonne.myospherocontrol.controller.myo;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementResult;

public class MyoEventHandler implements IMyoEvents
{
  private IServiceState _serviceState;
  private IMovementCalculator _mMovementCalculator;
  private ISpheroController _spheroController;
  private boolean _controlmode;

  public MyoEventHandler(
      IServiceState state,
      IMovementCalculator mMovementCalculator,
      ISpheroController spheroController)
  {
    _serviceState = state;
    _mMovementCalculator = mMovementCalculator;
    _spheroController = spheroController;
    
    _controlmode = false;
  }

  public void myoStateChanged(MyoStatus myoStatus)
  {
    _serviceState.setMyoStatus(myoStatus);
  }

  public void myoOrientationDataCollected(Quaternion rotation, Myo myo)
  {
    if (!_controlmode)
      return;

    MovementResult movementResult = _mMovementCalculator
        .calculate(rotation, myo.getXDirection() == XDirection.TOWARD_ELBOW);

    _spheroController.move(movementResult.getDirection(), movementResult.getSpeed());
    _spheroController.changeColor(movementResult.getRed(), movementResult.getGreen(), movementResult.getBlue());
  }

  public void myoControlDeactivated()
  {
  	_controlmode = false;
    
    _spheroController.changeColor(0, 0, 255);
    _spheroController.halt();
  }

  public void myoControlActivated()
  {
  	_controlmode = true;

    _mMovementCalculator.resetRoll();
  }

	public void stopControlMode()
	{
		_controlmode = false;
	}
}
