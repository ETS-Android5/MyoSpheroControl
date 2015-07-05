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
  private Sphero _sphero;
  private boolean _running;
  private boolean _connected;
  private ConnectionListener _connectionListener;
  private DiscoveryListener _discoveryListener;

  public SpheroController(Context context)
  {
    _connectionListener = new SpheroConnectionListener(this);
    _discoveryListener = new SpheroDiscoveryListener(this);
    _context = context;
  }

  public void onCreate()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.addConnectionListener(_connectionListener);
    robotProvider.addDiscoveryListener(_discoveryListener);
  }

  public Sphero get_sphero()
  {
    return _sphero;
  }

  public boolean is_running()
  {
    return _running;
  }

  public boolean is_connected()
  {
    return _connected;
  }

  public void set_sphero(Sphero _sphero)
  {
    this._sphero = _sphero;
  }

  public void set_connected(boolean _connected)
  {
    this._connected = _connected;
  }

  public void setEventListener(ISpheroEvents eventListener)
  {
    _eventListener = eventListener;
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
    if (!_connected && _sphero != null && _sphero.isConnected())
      _connected = true;

    // if (_connected)
    if (_sphero != null && _sphero.isConnected())
      _sphero.drive(direction, speed);
  }

  public void changeColor(int red, int green, int blue)
  {
    if (_sphero != null && _sphero.isConnected())
      _sphero.setColor(red, green, blue);
  }

  public void halt()
  {
    if (_sphero != null && _sphero.isConnected())
      // if (_connected)
      _sphero.stop();
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
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.connect(_sphero);
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