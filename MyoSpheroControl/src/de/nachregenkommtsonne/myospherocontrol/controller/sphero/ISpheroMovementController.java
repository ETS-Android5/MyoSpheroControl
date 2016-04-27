package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

public interface ISpheroMovementController
{
	public abstract void halt();

	public abstract void changeColor(int red, int green, int blue);

	public abstract void move(float direction, float speed);
}
