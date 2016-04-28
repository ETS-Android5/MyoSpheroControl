package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

public interface ISpheroMovementController
{
	public abstract void halt();

	public abstract void changeColor(float red, float green, float blue);

	public abstract void move(float direction, float speed);
}
