package de.nachregenkommtsonne.myospherocontrol.movement;

import com.thalmic.myo.Quaternion;

public interface IMovementCalculator
{
  public MovementResult calculate(Quaternion rotation, boolean towardElbow);

  public void resetRoll();
}
