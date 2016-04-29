package de.nachregenkommtsonne.myospherocontrol.movement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.thalmic.myo.Quaternion;

public class MovementCalculatorTest
{
  IMovementCalculator _movementCalculator;

  @Before
  public void setUp()
  {
    _movementCalculator = new MovementCalculator();
  }

  @Test
  public void calculate_Default()
  {

    Quaternion quaternion = new Quaternion();

    MovementResult result = _movementCalculator.calculate(quaternion, false);

    assertEquals(0.2f, result.getSpeed(), 0.0f);
  }

}
