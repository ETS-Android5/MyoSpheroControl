package de.nachregenkommtsonne.myospherocontrol.service;

final class ServiceControllerStarter implements Runnable {

	private final ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver;

	ServiceControllerStarter(ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver) {
		this.serviceControllerBroadcastReceiver = serviceControllerBroadcastReceiver;
	}

	public void run() {
		try {
			Thread.sleep(1400);
		} catch (InterruptedException e) {
		}
		
		this.serviceControllerBroadcastReceiver.serviceController._state.setBluetoothState(BluetoothState.on);

		if (this.serviceControllerBroadcastReceiver.serviceController._state.isRunning()) {
			this.serviceControllerBroadcastReceiver.serviceController._myoController.startConnecting();
			this.serviceControllerBroadcastReceiver.serviceController._spheroController.start();
		}
		
		this.serviceControllerBroadcastReceiver.serviceController.onChanged();
	}
}