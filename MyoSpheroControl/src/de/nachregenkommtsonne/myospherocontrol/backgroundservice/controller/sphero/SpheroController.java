package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

//TODO: Decompose
public class SpheroController implements ISpheroController, ISpheroEvents
{
  private Context _context;
  private boolean _running;
  private boolean _connected;
  private ConnectionListener _connectionListener;
  private DiscoveryListener _discoveryListener;
  private SpheroManager _spheroManager;
  private IChangedNotifier _changedNotifier;
  private ServiceState _serviceState;

  public SpheroController(Context context, SpheroManager spheroManager, ServiceState serviceState)
  {
    _connectionListener = new SpheroConnectionListener(this, spheroManager);
    _discoveryListener = new SpheroDiscoveryListener(this, spheroManager);
    _context = context;
    _spheroManager = spheroManager;
    _serviceState = serviceState;
  }

  public void onCreate()
  {
    RobotProvider robotProvider = getRobotProvider();
    robotProvider.addConnectionListener(_connectionListener);
    robotProvider.addDiscoveryListener(_discoveryListener);
  }

  public void setChangedNotifier(IChangedNotifier changedNotifier)
  {
    _changedNotifier = changedNotifier;
  }

  public boolean isRunning()
  {
    return _running;
  }

  public boolean isConnected()
  {
    return _connected;
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
    spheroStateChanged(spheroStatus);
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      changeColor(0, 0, 255);
    }

    _serviceState.setSpheroStatus(spheroStatus);
    _changedNotifier.onChanged();
  }

  public void bluetoothDisabled()
  {
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