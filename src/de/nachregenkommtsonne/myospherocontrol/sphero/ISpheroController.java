package de.nachregenkommtsonne.myospherocontrol.sphero;

import orbotix.robot.base.RobotProvider;

public interface ISpheroController {
	void move(float direction, float speed);
	void changeColor();
	void halt();
	void start();
	void stop();
	public abstract void setEventListener(ISpheroEvents eventListener);
	public abstract void stopForBluetooth();
}
