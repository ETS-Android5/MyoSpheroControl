package de.nachregenkommtsonne.myospherocontrol.sphero;

public interface ISpheroController {
	void move(float direction, float speed);
	void changeColor();
	void halt();
	void start();
	void stop();
	public abstract void setEventListener(ISpheroEvents eventListener);
}
