package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class MyoControllerFactory
{
  public MyoControllerFactory()
  {

  }

  public MyoController create(Context context, ServiceState serviceState, ISpheroController spheroController)
  {
    SettingsEditor settingsEditor = new SettingsEditor();
    IMovementCalculator mMovementCalculator = new MovementCalculator();
    IMyoEvents myoEventHandler = new MyoEventHandler(serviceState, mMovementCalculator, spheroController);
    MyoController myoController = new MyoController(myoEventHandler, settingsEditor, context, serviceState);

    return myoController;
  }
}
