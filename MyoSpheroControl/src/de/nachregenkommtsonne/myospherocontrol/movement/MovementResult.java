package de.nachregenkommtsonne.myospherocontrol.movement;

public class MovementResult
{
  private float _direction;
  private float _speed;

  private float _red;
  private float _green;
  private float _blue;

  public MovementResult(float direction, float speed, float red, float green, float blue)
  {
    super();

    _direction = direction;
    _speed = speed;

    _red = red;
    _green = green;
    _blue = blue;
  }

  public float getRed()
  {
    return _red;
  }

  public float getGreen()
  {
    return _green;
  }

  public float getBlue()
  {
    return _blue;
  }

  public float getDirection()
  {
    return _direction;
  }

  public float getSpeed()
  {
    return _speed;
  }
}