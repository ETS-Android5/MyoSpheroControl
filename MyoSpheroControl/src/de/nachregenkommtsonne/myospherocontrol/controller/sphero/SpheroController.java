package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

//TODO: Decompose
public class SpheroController implements ISpheroController
{
  private Context _context;
  private ConnectionListener _connectionListener;
  private DiscoveryListener _discoveryListener;
  private SpheroManager _spheroManager;
  private IServiceState _serviceState;

  private boolean _running;
  private boolean _connected;

  public SpheroController(Context context, SpheroManager spheroManager, IServiceState serviceState)
  {
    _context = context;
    _spheroManager = spheroManager;
    _serviceState = serviceState;

    _connectionListener = new SpheroConnectionListener(this, spheroManager);
    _discoveryListener = new SpheroDiscoveryListener(this, spheroManager);
  }

  public void onCreate()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.addConnectionListener(_connectionListener);
    robotProvider.addDiscoveryListener(_discoveryListener);
  }

  public boolean isRunning()
  {
    return _running;
  }

  public void setConnected(boolean connected)
  {
    _connected = connected;
  }

  private RobotProvider getRobotProvider()
  {
    return RobotProvider.getDefaultProvider();
  }

  public void onSpheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      changeColor(0, 0, 255);
    }
    
    _serviceState.setSpheroStatus(spheroStatus);
  }

  public void move(float direction, float speed)
  {
    Sphero sphero = _spheroManager.getSphero();

    if (!_connected && sphero != null && sphero.isConnected())
      _connected = true;

    if (sphero != null && sphero.isConnected())
      sphero.drive(direction, speed);
  }

  public void changeColor(int red, int green, int blue)
  {
    Sphero sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
      sphero.setColor(red, green, blue);
  }

  public void halt()
  {
    Sphero sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
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
    Sphero sphero = _spheroManager.getSphero();

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