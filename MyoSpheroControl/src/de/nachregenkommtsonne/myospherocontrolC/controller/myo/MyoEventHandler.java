package de.nachregenkommtsonne.myospherocontrolC.controller.myo;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.ISpheroMovementController;
import de.nachregenkommtsonne.myospherocontrolC.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrolC.movement.MovementResult;

public class MyoEventHandler implements IMyoEvents
{
  private IMovementCalculator _mMovementCalculator;
  private ISpheroMovementController _spheroController;
  private boolean _isControlmodeActive;

  public MyoEventHandler(IMovementCalculator mMovementCalculator, ISpheroMovementController spheroController)
  {
    _mMovementCalculator = mMovementCalculator;
    _spheroController = spheroController;

    _isControlmodeActive = false;
  }

  public void myoOrientationDataCollected(Quaternion rotation, Myo myo)
  {
    if (!_isControlmodeActive)
      return;

    MovementResult movementResult = _mMovementCalculator.calculate(rotation,
        myo.getXDirection() == XDirection.TOWARD_ELBOW);

    _spheroController.move(movementResult.getDirection(), movementResult.getSpeed());
    _spheroController.changeColor(movementResult.getRed(), movementResult.getGreen(), movementResult.getBlue());
  }

  public void myoControlDeactivated()
  {
    _isControlmodeActive = false;

    _spheroController.changeColor(0, 0, 1.0f);
    _spheroController.halt();
  }

  public void myoControlActivated()
  {
    _isControlmodeActive = true;

    _mMovementCalculator.resetRoll();
  }

  public void stopControlMode()
  {
    _isControlmodeActive = false;
  }
}
