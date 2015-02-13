package de.nachregenkommtsonne.myospherocontrol.interfaces;

public interface IGuiCapabilities {
	void setDisabled();
	void setEnabled();
	void informNoBluetooth();
	void informNoSync();
	void informNoSphero();
	void toast(String message);
}
