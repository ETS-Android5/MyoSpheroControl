package de.nachregenkommtsonne.myospherocontrol.interfaces;

public interface ISpheroCapabilities {
	void move(float direction, float speed);
	void changeColor();
	void initialize();
	void halt();
	void start();
	void stop();
}
