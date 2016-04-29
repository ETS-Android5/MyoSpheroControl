package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.stat.StatRecorder;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class SpheroController implements ISpheroController
{
  private Context _context;
  private SpheroManager _spheroManager;
  private IServiceState _serviceState;

  private boolean _enabled;

  public SpheroController(Context context, SpheroManager spheroManager, IServiceState serviceState)
  {
    _context = context;
    _spheroManager = spheroManager;
    _serviceState = serviceState;
  }

  public void onCreate()
  {
    DualStackDiscoveryAgent discoveryAgent = getDiscoveryAgent();

    discoveryAgent.addRobotStateListener(new RobotChangedStateListener()
    {

      @Override
      public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type)
      {

        switch (type)
          {
          case Connecting:
            _spheroManager.setConnected(false);
            startConnecting();
            break;
          case Connected:
            _spheroManager.setConnected(false);
            break;
          case Online:
          {
            _spheroManager.setConnected(true);

            ConvenienceRobot convenienceRobot = new ConvenienceRobot(robot);

            convenienceRobot.setBackLedBrightness(1.0f);
            convenienceRobot.setLed(0.0f, 0.0f, 1.0f);

            _spheroManager.setSphero(convenienceRobot);

            _serviceState.setSpheroStatus(SpheroStatus.connected);
            break;
          }
          case Disconnected:
            _spheroManager.setConnected(false);

            if (_enabled)
              startDiscovery();

            break;
          case FailedConnect:
            _spheroManager.setConnected(false);

            if (_enabled)
              startDiscovery();

            break;
          case Offline:
            _spheroManager.setConnected(false);

            if (_enabled)
              startDiscovery();

            break;
          default:
            break;
          }
      }
    });
  }

  private DualStackDiscoveryAgent getDiscoveryAgent()
  {
    return DualStackDiscoveryAgent.getInstance();
  }

  public void start()
  {
    startDiscovery();
    _enabled = true;
  }

  public void startDiscovery()
  {
    DualStackDiscoveryAgent discoveryAgent = getDiscoveryAgent();
    try
    {
      discoveryAgent.startDiscovery(_context);
      StatRecorder.getInstance().stop();
    } catch (DiscoveryException e)
    {
    }

    _serviceState.setSpheroStatus(SpheroStatus.discovering);
  }

  public void startConnecting()
  {
    _serviceState.setSpheroStatus(SpheroStatus.connecting);
  }

  public void stop()
  {
    _enabled = false;

    DualStackDiscoveryAgent robotProvider = getDiscoveryAgent();

    try
    {
      robotProvider.disconnectAll();
    } catch (Exception ex)
    {
    }

    if (robotProvider.isDiscovering())
      robotProvider.stopDiscovery();

    _serviceState.setSpheroStatus(SpheroStatus.disconnected);
  }

  public void stopForBluetooth()
  {
    _enabled = false;

    DualStackDiscoveryAgent robotProvider = getDiscoveryAgent();

    if (robotProvider.isDiscovering())
      robotProvider.stopDiscovery();

    _serviceState.setSpheroStatus(SpheroStatus.disconnected);
  }
}