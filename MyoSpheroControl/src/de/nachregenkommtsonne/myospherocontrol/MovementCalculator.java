package de.nachregenkommtsonne.myospherocontrol;

import com.thalmic.myo.Quaternion;

public class MovementCalculator implements IMovementCalculator
{
  public boolean _resetRoll;
  public float _startRoll;
  public float _directionDelta;
  public float _speed;

  public MovementCalculator()
  {
    _resetRoll = true;
  }

  public void resetRoll()
  {
    _resetRoll = true;
  }

  public MovementResult calculate(Quaternion rotation, boolean towardElbow)
  {
    float roll = (float) Math.toDegrees(Quaternion.roll(rotation));
    float pitch = (float) Math.toDegrees(Quaternion.pitch(rotation));
    float yaw = (float) Math.toDegrees(Quaternion.yaw(rotation));

    if (towardElbow)
    {
      roll *= -1;
      pitch *= -1;
    }

    if (_resetRoll)
    {
      _startRoll = roll;
      _directionDelta = 0.0f;
      _resetRoll = false;
      _speed = 0.0f;
    }

    float rollDelta = roll - _startRoll;

    _directionDelta = rollDelta * 6.0f;

    float direction = -(yaw + 180.f) + _directionDelta;

    while (direction < 0.0f || direction >= 360.0f)
    {
      if (direction < 0.0f)
        direction += 360.0f;
      if (direction >= 360.0f)
        direction -= 360.0f;
    }

    if (pitch < 0.0f)
    {
      _speed = (pitch + 40.f) / 200.f;
    }
    else
    {
      _speed = 0.2f + pitch / 45.f * 0.8f;
    }

    if (_speed > 1.0f)
      _speed = 1.0f;

    if (_speed < 0.0f)
      _speed = 0.0f;

    return new MovementResult(direction, _speed, (int) (_speed * 255), (int) (255 - (_speed * 255)), 0);
  }
}
