package de.nachregenkommtsonne.myospherocontrol.test.service;

import org.junit.Before;
import com.thalmic.myo.Quaternion;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.service.IServiceController;
import de.nachregenkommtsonne.myospherocontrol.service.MovementResult;
import de.nachregenkommtsonne.myospherocontrol.service.MyoEvents;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;

public class MyoEventsTest extends AndroidTestCase {
	private final class ServiceControllerStub implements IServiceController {
		private boolean onChangedCalled = false;
		
		public void onChanged() {
			onChangedCalled = true;
		}
		
		public boolean wasOnChangedCalled(){
			return onChangedCalled;
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

	private ServiceState _serviceState;
	private MovementCalculatorStub _movementCalculator;
	private SpheroControllerStub _spheroController;
	private ServiceControllerStub _serviceController;
	private MyoEvents _myoEvents;
	
	@Before
	public void setUp() throws Exception {
		_serviceState = new ServiceState();
		_movementCalculator = new MovementCalculatorStub();
		_spheroController = new SpheroControllerStub();
		_serviceController = new ServiceControllerStub();

		_myoEvents = new MyoEvents(_serviceState, _movementCalculator, _spheroController, _serviceController);
	}

	 @SmallTest
	 public void init() {
		
		assertTrue(false);
		assertSame(_serviceState, _myoEvents.get_state());
		assertSame(_movementCalculator, _myoEvents.get_state());
		assertSame(_spheroController, _myoEvents.get_state());
		assertSame(_serviceController, _myoEvents.get_state());
	}

	public void myoStateChanged_connected() {
		MyoStatus myoStatus = MyoStatus.connected;
		
		_myoEvents.myoStateChanged(myoStatus);
		
		assertEquals(myoStatus, _serviceState.getMyoStatus());
		assertTrue(_serviceController.wasOnChangedCalled());
	}

	public void myoStateChanged_disconnected() {
		MyoStatus myoStatus = MyoStatus.disconnected;
		
		_myoEvents.myoStateChanged(myoStatus);
		
		assertEquals(myoStatus, _serviceState.getMyoStatus());
		assertTrue(_serviceController.wasOnChangedCalled());
	}

	public void myoStateChanged_linked() {
		MyoStatus myoStatus = MyoStatus.linked;
		
		_myoEvents.myoStateChanged(myoStatus);
		
		assertEquals(myoStatus, _serviceState.getMyoStatus());
		assertTrue(_serviceController.wasOnChangedCalled());
	}

	public void myoStateChanged_notLinked() {
		MyoStatus myoStatus = MyoStatus.notLinked;
		
		_myoEvents.myoStateChanged(myoStatus);
		
		assertEquals(myoStatus, _serviceState.getMyoStatus());
		assertTrue(_serviceController.wasOnChangedCalled());
	}

	public void myoStateChanged_notSynced() {
		MyoStatus myoStatus = MyoStatus.notSynced;
		
		_myoEvents.myoStateChanged(myoStatus);
		
		assertEquals(myoStatus, _serviceState.getMyoStatus());
		assertTrue(_serviceController.wasOnChangedCalled());
	}	
}
