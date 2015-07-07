package de.nachregenkommtsonne.myospherocontrol.movement;

public class MovementResult
{
  private float _direction;
  private float _speed;
  private int _red;
  private int _green;
  private int _blue;

  public int getRed()
  {
    return _red;
  }

  public int getGreen()
  {
    return _green;
  }

  public int getBlue()
  {
    return _blue;
  }

  public MovementResult(float direction, float speed, int red, int green, int blue)
  {
    super();

    _direction = direction;
    _speed = speed;
    _red = red;
    _green = green;
    _blue = blue;
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