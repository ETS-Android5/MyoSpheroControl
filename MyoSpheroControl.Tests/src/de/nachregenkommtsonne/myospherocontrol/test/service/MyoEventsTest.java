package de.nachregenkommtsonne.myospherocontrol.test.service;

import org.junit.Test;

import com.thalmic.myo.Quaternion;

import android.test.AndroidTestCase;
import de.nachregenkommtsonne.myospherocontrol.service.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.service.IServiceController;
import de.nachregenkommtsonne.myospherocontrol.service.MovementCalculator.MovementResult;
import de.nachregenkommtsonne.myospherocontrol.service.MyoEvents;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;

public class MyoEventsTest extends AndroidTestCase {
	private final class ServiceControllerStub implements IServiceController {
		public void onChanged() {
		}
	}

	private final class SpheroControllerStub implements ISpheroController {
		public void stopForBluetooth() {
		}

		public void stop() {
		}

		public void start() {
		}

		public void setEventListener(ISpheroEvents eventListener) {
		}

		public void move(float direction, float speed) {
		}

		public void halt() {
		}

		public void changeColor(int red, int green, int blue) {
		}
	}

	private final class MovementCalculatorStub implements IMovementCalculator {
		public void resetRoll() {
		}

		public MovementResult calculate(Quaternion rotation, boolean towardElbow) {
			return null;
		}
	}

	@Test
	public void init() {
		ServiceState serviceState = new ServiceState();
		MovementCalculatorStub movementCalculator = new MovementCalculatorStub();
		SpheroControllerStub spheroController = new SpheroControllerStub();
		ServiceControllerStub serviceController = new ServiceControllerStub();
		
		MyoEvents myoEvents = new MyoEvents(
				serviceState, 
				movementCalculator, 
				spheroController,
				serviceController);
		
		assertSame(serviceState, myoEvents.get_state());
		assertSame(serviceState, myoEvents.get_state());
		assertSame(serviceState, myoEvents.get_state());
		assertSame(serviceState, myoEvents.get_state());
	}
}
