package de.nachregenkommtsonne.myospherocontrol.controller;

import android.util.Log;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.XDirection;

import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.interfaces.ISpheroCapabilities;

public class MyoHandler implements IMyoEvents {

	IGuiCapabilities _guiController;
	ISpheroCapabilities _spheroController;

	boolean state = false;
	boolean resetRoll = true;
	float startRoll = 0.0f;
	float directionDelta = 0.0f;
	float speed = 0.0f;

	public MyoHandler(IGuiCapabilities guiController, ISpheroCapabilities spheroController) {
		_guiController = guiController;
		_spheroController = spheroController;
	}

	public void myoConnected() {
		// _guiController.toast("Myo Connected");
	}

	public void myoDisconnected() {
	}

	public void myoUnlocked() {
	}

	public void myoControlActivated() {
		_guiController.toast("myoPoseFist");
		// _spheroController.move();
		state = true;
	}

	public void myoControlDeactivated() {
		state = false;
		_spheroController.stop();
	}

	public void myoOrientationDataCollected(Quaternion rotation, Myo myo) {
		float roll = (float) Math.toDegrees(Quaternion.roll(rotation));
		float pitch = (float) Math.toDegrees(Quaternion.pitch(rotation));
		float yaw = (float) Math.toDegrees(Quaternion.yaw(rotation));

		// Adjust roll and pitch for the orientation of the Myo on the arm.
		if (myo.getXDirection() == XDirection.TOWARD_ELBOW) {
			roll *= -1;
			pitch *= -1;
		}

		if (resetRoll) {
			startRoll = roll;
			directionDelta = 0.0f;
			resetRoll = false;
			speed = 0.0f;
		}

		float rollDelta = roll - startRoll;
		
		directionDelta = rollDelta * 6.0f;

		if (state) {

			
			
			float direction = -(yaw + 90) + directionDelta;

			while (direction < 0.0f || direction >= 360.0f) {
				if (direction < 0.0f)
					direction += 360.0f;
				if (direction >= 360.0f)
					direction -= 360.0f;
			}
			
			
			
			//pitch [-20, 90] -> speed [0,1]
			// -40 -> 0
			// 0 -> 0.2
			// 90 -> 1
			
			if (pitch < 0.0f){
				speed = (pitch + 40.f) / 200.f;
			}
			else{
				speed = 0.2f + pitch / 90.f * 0.8f;
			}
			
			if (speed > 1.0f)
				speed = 1.0f;
			
			if (speed < 0.0f)
				speed = 0.0f;

			Log.e("myo Yaw", "" + speed);
			_spheroController.move(direction, speed);

		}
	}

	public void myoSynced() {
	}

	public void myoUnsynced() {
	}
}