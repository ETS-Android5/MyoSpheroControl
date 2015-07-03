package de.nachregenkommtsonne.myospherocontrol.service;

import com.thalmic.myo.Quaternion;

public interface IMovementCalculator {
	public MovementResult calculate(Quaternion rotation, boolean towardElbow);
	public void resetRoll();
}
