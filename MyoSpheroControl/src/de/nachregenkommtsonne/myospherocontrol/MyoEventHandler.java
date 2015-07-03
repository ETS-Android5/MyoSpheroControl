package de.nachregenkommtsonne.myospherocontrol;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementResult;

public class MyoEventHandler implements IMyoEvents
{
  private ServiceState _state;
  private IMovementCalculator _mMovementCalculator;
  private ISpheroController _spheroController;
  private IServiceController _serviceController;

//TODO: Decompose
  public MyoEventHandler(ServiceState state, IMovementCalculator mMovementCalculator, ISpheroController spheroController,
      IServiceController serviceController)
  {
    _state = state;
    _mMovementCalculator = mMovementCalculator;
    _spheroController = spheroController;
    _serviceController = serviceController;
  }

  public ServiceState get_state()
  {
    return _state;
  }

  public IMovementCalculator get_mMovementCalculator()
  {
    return _mMovementCalculator;
  }

  public ISpheroController get_spheroController()
  {
    return _spheroController;
  }

  public IServiceController get_serviceController()
  {
    return _serviceController;
  }

  public void myoStateChanged(MyoStatus myoStatus)
  {
    _state.setMyoStatus(myoStatus);
    _serviceController.onChanged();
  }

  public void myoOrientationDataCollected(Quaternion rotation, Myo myo)
  {
    if (!_state.isControlMode())
      return;

    MovementResult movementResult = _mMovementCalculator.calculate(rotation,
        myo.getXDirection() == XDirection.TOWARD_ELBOW);

    _spheroController.move(movementResult.get_direction(), movementResult.get_speed());

    _spheroController.changeColor(movementResult.get_red(), movementResult.get_green(), movementResult.get_blue());
  }

  public void myoControlDeactivated()
  {
    _spheroController.changeColor(0, 0, 255);
    _state.setControlMode(false);
    _spheroController.halt();
  }

  public void myoControlActivated()
  {
    _mMovementCalculator.resetRoll();
    _state.setControlMode(true);
  }
}
