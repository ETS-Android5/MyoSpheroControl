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

    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.1, -0.5, 0.5), 0.041f, 068.2f, 341.5f, 0.765f, 068.2f, 147.6f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.2, -0.5, 0.5), 0.088f, 073.3f, 012.0f, 0.597f, 073.3f, 117.0f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.3, -0.5, 0.5), 0.131f, 078.7f, 044.4f, 0.445f, 078.7f, 084.7f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.4, -0.5, 0.5), 0.168f, 084.3f, 078.0f, 0.312f, 084.3f, 051.1f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 0.7, -0.5, 0.5), 0.365f, 101.3f, 180.1f, 0.153f, 101.3f, 309.0f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 1.0, -0.5, 0.5), 0.495f, 116.6f, 271.6f, 0.117f, 116.6f, 217.4f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.5, 1.0, -0.4, 0.5), 0.577f, 115.2f, 309.5f, 0.094f, 115.2f, 179.6f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.3, 1.0, -0.4, 0.5), 0.741f, 129.4f, 302.6f, 0.048f, 129.4f, 186.5f) });
    map.add(new Object[] { new TestCase(new Quaternion(-0.3, 1.0, -0.4, 0.8), 1.000f, 109.1f, 188.9f, 0.000f, 109.1f, 300.2f) });

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
    assertEquals(64.6, result.getDirection(), 0.1f);
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
    assertEquals(64.6, result.getDirection(), 0.1f);
    assertEquals(0.134f, result.getRed(), 0.001f);
    assertEquals(0.134f, 1.0f - result.getGreen(), 0.001f);
    assertEquals(0.0f, result.getBlue(), 0.0f);
  }
}
