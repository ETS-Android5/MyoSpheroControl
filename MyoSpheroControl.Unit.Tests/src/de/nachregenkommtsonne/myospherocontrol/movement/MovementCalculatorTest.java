package de.nachregenkommtsonne.myospherocontrol.movement;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.thalmic.myo.Quaternion;

@RunWith(Parameterized.class)
public class MovementCalculatorTest
{
  @Parameter
  public TestCase _map;

  public static class TestCase
  {
    Quaternion quaternion;
    float speed, reverseSpeed;
    float direction, newdirection;
    float reverseDirection, reverseNewdirection;

    public TestCase(Quaternion quaternion, float speed, float direction, float newdirection, float reverseSpeed, float reverseDirection, float reverseNewdirection)
    {
      this.quaternion = quaternion;
      
      this.speed = speed;
      this.direction = direction;
      this.newdirection = newdirection;
      
      this.reverseSpeed = reverseSpeed;
      this.reverseDirection = reverseDirection;
      this.reverseNewdirection = reverseNewdirection;
    }
  }

  private IMovementCalculator _movementCalculator;

  @Parameters(name="{index}")
  public static Collection<Object[]> configs()
  {
    ArrayList<Object[]> map = new ArrayList<Object[]>();

    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.1, -0.5, 0.5), 0.041f, 248.2f, 161.5f, 0.765f, 248.2f, 327.6f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.2, -0.5, 0.5), 0.088f, 253.3f, 192.0f, 0.597f, 253.3f, 297.0f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.3, -0.5, 0.5), 0.131f, 258.7f, 224.4f, 0.445f, 258.7f, 264.7f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.4, -0.5, 0.5), 0.168f, 264.3f, 258.0f, 0.312f, 264.3f, 231.1f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.7, -0.5, 0.5), 0.365f, 281.3f, 000.1f, 0.153f, 281.3f, 129.0f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 1.0, -0.5, 0.5), 0.495f, 296.6f, 091.6f, 0.117f, 296.6f, 037.4f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 1.0, -0.4, 0.5), 0.577f, 295.2f, 129.5f, 0.094f, 295.2f, 359.6f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.3, 1.0, -0.4, 0.5), 0.741f, 309.4f, 122.6f, 0.048f, 309.4f, 006.5f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.3, 1.0, -0.4, 0.8), 1.000f, 289.1f, 008.9f, 0.000f, 289.1f, 120.2f) });

    return map;
  }

  @Before
  public void setUp()
  {
    _movementCalculator = new MovementCalculator();
  }

  @Test
  public void calculate()
  {
    MovementResult result = _movementCalculator.calculate(_map.quaternion.normalized(), false);

    assertEquals(_map.speed, result.getSpeed(), 0.001f);
    assertEquals(_map.direction, result.getDirection(), 0.1f);
    assertEquals(_map.speed, result.getRed(), 0.001f);
    assertEquals(_map.speed, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
    
    result = _movementCalculator.calculate(new Quaternion(-0.6, 0.5, -0.4, 0.8).normalized(), false);

    assertEquals(0.433f, result.getSpeed(), 0.001f);
    assertEquals(_map.newdirection, result.getDirection(), 0.1f);
    assertEquals(0.433f, result.getRed(), 0.001f);
    assertEquals(0.433f, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
    
    _movementCalculator.resetRoll();
    
    result = _movementCalculator.calculate(new Quaternion(-0.6, 0.5, -0.4, 0.8).normalized(), false);

    assertEquals(0.433f, result.getSpeed(), 0.001f);
    assertEquals(244.6, result.getDirection(), 0.1f);
    assertEquals(0.433f, result.getRed(), 0.001f);
    assertEquals(0.433f, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
  }
  
  @Test
  public void calculate_towardElbow()
  {
    MovementResult result = _movementCalculator.calculate(_map.quaternion.normalized(), true);

    assertEquals(_map.reverseSpeed, result.getSpeed(), 0.001f);
    assertEquals(_map.reverseDirection, result.getDirection(), 0.1f);
    assertEquals(_map.reverseSpeed, result.getRed(), 0.001f);
    assertEquals(_map.reverseSpeed, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
    
    result = _movementCalculator.calculate(new Quaternion(-0.6, 0.5, -0.4, 0.8).normalized(), true);

    assertEquals(0.134f, result.getSpeed(), 0.001f);
    assertEquals(_map.reverseNewdirection, result.getDirection(), 0.1f);
    assertEquals(0.134f, result.getRed(), 0.001f);
    assertEquals(0.134f, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
    
    _movementCalculator.resetRoll();
    
    result = _movementCalculator.calculate(new Quaternion(-0.6, 0.5, -0.4, 0.8).normalized(), true);

    assertEquals(0.134f, result.getSpeed(), 0.001f);
    assertEquals(244.6, result.getDirection(), 0.1f);
    assertEquals(0.134f, result.getRed(), 0.001f);
    assertEquals(0.134f, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
  }
}
