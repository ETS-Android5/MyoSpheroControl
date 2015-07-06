package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import android.content.Context;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

//TODO: Decompose
public class SpheroController implements ISpheroController
{
  private Context _context;
  private ISpheroEvents _eventListener;
  private boolean _running;
  private boolean _connected;
  private ConnectionListener _connectionListener;
  private DiscoveryListener _discoveryListener;
  private SpheroManager _spheroManager;
  
  public SpheroController(Context context, ISpheroEvents eventListener, SpheroManager spheroManager)
  {
    _connectionListener = new SpheroConnectionListener(this, spheroManager);
    _discoveryListener = new SpheroDiscoveryListener(this, spheroManager);
    _context = context;
    _eventListener = eventListener;
    _spheroManager = spheroManager;
 }

  public void onCreate()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.addConnectionListener(_connectionListener);
    robotProvider.addDiscoveryListener(_discoveryListener);
  }

  public boolean is_running()
  {
    return _running;
  }

  public boolean is_connected()
  {
    return _connected;
  }

  public void set_connected(boolean connected)
  {
    _connected = connected;
  }

  private RobotProvider getRobotProvider()
  {
    return RobotProvider.getDefaultProvider();
  }

  public void onSpheroStateChanged(SpheroStatus spheroStatus)
  {
    _eventListener.spheroStateChanged(spheroStatus);
  }

  public void move(float direction, float speed)
  {
  	Sphero sphero = _spheroManager.get_sphero();
  	
    if (!_connected && sphero != null && sphero.isConnected())
      _connected = true;

    // if (_connected)
    if (sphero != null && sphero.isConnected())
      sphero.drive(direction, speed);
  }

  @Deprecated
  public void changeColor(int red, int green, int blue)
  {
  	Sphero sphero = _spheroManager.get_sphero();

  	if (sphero != null && sphero.isConnected())
      sphero.setColor(red, green, blue);
  }

  public void halt()
  {
  	Sphero sphero = _spheroManager.get_sphero();

    if (sphero != null && sphero.isConnected())
      // if (_connected)
      sphero.stop();
  }

  public void start()
  {
    startDiscovery();
    _running = true;
  }

  public void startDiscovery()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.startDiscovery(_context);

    onSpheroStateChanged(SpheroStatus.discovering);
  }

  public void startConnecting()
  {
  	Sphero sphero = _spheroManager.get_sphero();

  	RobotProvider robotProvider = getRobotProvider();
    robotProvider.connect(sphero);
    onSpheroStateChanged(SpheroStatus.connecting);
  }

  public void stop()
  {
    _running = false;
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.disconnectControlledRobots();
    robotProvider.shutdown();
    onSpheroStateChanged(SpheroStatus.disconnected);
  }

  public void stopForBluetooth()
  {
    _running = false;
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.shutdown();
    onSpheroStateChanged(SpheroStatus.disconnected);
  }
}