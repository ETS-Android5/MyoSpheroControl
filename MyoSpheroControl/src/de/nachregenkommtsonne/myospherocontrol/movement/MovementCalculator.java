package de.nachregenkommtsonne.myospherocontrol.movement;

import com.thalmic.myo.Quaternion;

public class MovementCalculator implements IMovementCalculator
{
  private static final float _rollFactor = 6.0f;

  private boolean _resetRoll;
  private float _startRoll;

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
      _resetRoll = false;
    }

    float relativeRoll = roll - _startRoll;

    float direction = calculateDirection(relativeRoll, yaw);
    float speed = calculateSpeed(pitch);

    float normalizedDirection = normalizeAngle(direction);
    float clampedSpeed = clamp(speed, 0.f, 1.f);

    return new MovementResult(normalizedDirection, clampedSpeed, clampedSpeed, 1.f - clampedSpeed, 0.f);
  }

  private float calculateDirection(float roll, float yaw)
  {
    // TODO: remove -180.f constant. irrelevant
    return roll * _rollFactor - yaw - 180.f;
  }

  private float normalizeAngle(float val)
  {
    while (val >= 360.0f)
      val -= 360.0f;

    while (val < 0.0f)
      val += 360.0f;

    return val;
  }

  private float calculateSpeed(float pitch)
  {
    if (pitch < 0.0f)
      return pitch / 200.f + 0.2f;
    else
      return pitch / 56.25f + 0.2f;
  }

  private float clamp(float val, float min, float max)
  {
    return Math.max(min, Math.min(max, val));
  }
}
