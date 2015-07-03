package de.nachregenkommtsonne.myospherocontrol.sphero;

import android.content.Context;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

public class SpheroController implements ISpheroController
{
  Context _context;
  ISpheroEvents _eventListener;
  Sphero _sphero;
  boolean _running;
  boolean _connected;

  public SpheroController(Context context)
  {
    _context = context;

    RobotProvider robotProvider = getRobotProvider();

    robotProvider.addConnectionListener(_connectionListener);
    robotProvider.addDiscoveryListener(_discoveryListener);
  }

  public void setEventListener(ISpheroEvents eventListener)
  {
    _eventListener = eventListener;
  }

  private RobotProvider getRobotProvider()
  {
    return RobotProvider.getDefaultProvider();
  }

  void onSpheroStateChanged(SpheroStatus spheroStatus)
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

  void startDiscovery()
  {
    RobotProvider robotProvider = getRobotProvider();
    /* boolean success = */ robotProvider.startDiscovery(_context);
    // if (!success) {
    // throw new RuntimeException();
    // }

    onSpheroStateChanged(SpheroStatus.discovering);
  }

  void startConnecting()
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

  ConnectionListener _connectionListener = new SpheroConnectionListener(this);

  DiscoveryListener _discoveryListener = new SpheroDiscoveryListener(this);
}