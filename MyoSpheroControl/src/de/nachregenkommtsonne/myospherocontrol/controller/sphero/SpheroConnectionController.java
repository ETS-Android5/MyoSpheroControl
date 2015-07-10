package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import java.util.List;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

public class SpheroConnectionController implements ISpheroConnectionController
{
  private Context _context;
  private SpheroManager _spheroManager;
  private IServiceState _serviceState;

  private boolean _enabled;

  public SpheroConnectionController(Context context, SpheroManager spheroManager, IServiceState serviceState)
  {
    _context = context;
    _spheroManager = spheroManager;
    _serviceState = serviceState;
  }

  public void onCreate()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.addConnectionListener(new ConnectionListener()
		{
    	public void onDisconnected(Robot sphero)
      {
      	_spheroManager.setConnected(false);
        if (!_enabled)
          return;

        if (_spheroManager.getSphero() == null)
          startDiscovery();
        else
        {
          startConnecting();
        }
      }

      public void onConnectionFailed(Robot sphero)
      {
      	_spheroManager.setConnected(false);
        if (!_enabled)
          return;

        if (_spheroManager.getSphero() == null)
          startDiscovery();
        else
        {
          startConnecting();
        }
      }

      public void onConnected(Robot arg0)
      {
        SpheroStatus spheroStatus = SpheroStatus.connected;
				if (spheroStatus == SpheroStatus.connected)
				{
				  Sphero sphero = _spheroManager.getSphero();
				
				  if (sphero != null && sphero.isConnected())
				    sphero.setColor(0, 0, 255);
				}
				
				_serviceState.setSpheroStatus(spheroStatus);
        _spheroManager.setConnected(true);
      }
		});
    
    robotProvider.addDiscoveryListener(new DiscoveryListener()
		{
      public void onFound(List<Sphero> spheros)
      {
        _spheroManager.setSphero(spheros.iterator().next());
        startConnecting();
      }

      public void onBluetoothDisabled()
      {
      	_spheroManager.setConnected(false);
      }

      public void discoveryComplete(List<Sphero> spheros){}
		});
  }

  private RobotProvider getRobotProvider()
  {
    return RobotProvider.getDefaultProvider();
  }

	public void start()
  {
    startDiscovery();
    _enabled = true;
  }

	public void startDiscovery()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.startDiscovery(_context);

    _serviceState.setSpheroStatus(SpheroStatus.discovering);
  }

	public void startConnecting()
  {
    Sphero sphero = _spheroManager.getSphero();

    RobotProvider robotProvider = getRobotProvider();
    robotProvider.connect(sphero);

    _serviceState.setSpheroStatus(SpheroStatus.connecting);
  }

	public void stop()
  {
    _enabled = false;
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.disconnectControlledRobots();
    robotProvider.shutdown();
    
    _serviceState.setSpheroStatus(SpheroStatus.disconnected);
  }

	public void stopForBluetooth()
  {
    _enabled = false;
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.shutdown();
    _serviceState.setSpheroStatus(SpheroStatus.disconnected);
  }
}