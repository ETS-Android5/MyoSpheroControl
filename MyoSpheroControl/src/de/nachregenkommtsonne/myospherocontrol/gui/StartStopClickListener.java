package de.nachregenkommtsonne.myospherocontrol.gui;

import android.view.View;
import android.view.View.OnClickListener;

public class StartStopClickListener implements OnClickListener
{
  private ControlFragment _controlFragment;

  public StartStopClickListener(ControlFragment controlFragment)
  {
    _controlFragment = controlFragment;
  }

  public void onClick(View arg0)
  {
    _controlFragment.buttonClicked();
  }
}