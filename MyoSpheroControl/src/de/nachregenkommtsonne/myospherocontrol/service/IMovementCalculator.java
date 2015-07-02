package de.nachregenkommtsonne.myospherocontrol.service;

import com.thalmic.myo.Quaternion;

import de.nachregenkommtsonne.myospherocontrol.service.MovementCalculator.MovementResult;

public interface IMovementCalculator {
	public MovementResult calculate(Quaternion rotation, boolean towardElbow);
	public void resetRoll();
}
