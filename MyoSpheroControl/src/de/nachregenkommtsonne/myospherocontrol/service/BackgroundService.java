package de.nachregenkommtsonne.myospherocontrol.service;

import de.nachregenkommtsonne.myospherocontrol.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroController;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundService extends Service {

	ServiceController _serviceController;
	private MyBinder _binder;
	
	public BackgroundService() {
		super();
		_binder = new MyBinder();
	}

	public void onCreate() {
		super.onCreate();
		_serviceController = new ServiceController(new MyoController(this), new SpheroController(this), this);
		_serviceController.setEventListener(_serviceControllerEvents);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	public IBinder onBind(Intent intent) {
		return _binder;
	}

	public void onDestroy() {
		super.onDestroy();
	}
	
	public class MyBinder extends Binder{
		IBinderEvents _binderEvents;

		public void onChanged(){
			if (_binderEvents != null)
				_binderEvents.changed();
		}
		public void setChangedListener(IBinderEvents binderEvents){
			_binderEvents = binderEvents;
		}
		
		public ServiceState getState(){
			return _serviceController.getState();
		}

		public void buttonClicked() {
			_serviceController.buttonClicked();
		}
		public void unlinkClicked() {
			_serviceController.unlinkClicked();
			
		}
	}
	
	public interface IBinderEvents{
		void changed();
	}
	
	IServiceControllerEvents _serviceControllerEvents = new IServiceControllerEvents() {

		public void changed() {
			_binder.onChanged();
			
		}
	};
	
	public void onTaskRemoved(Intent rootIntent) {
		super.onTaskRemoved(rootIntent);
		_serviceController.getState().setRunning(false);
		_serviceController.updateNotification();
	}
}
