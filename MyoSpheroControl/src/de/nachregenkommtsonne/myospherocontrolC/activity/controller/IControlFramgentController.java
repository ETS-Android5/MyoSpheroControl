package de.nachregenkommtsonne.myospherocontrolC.activity.controller;

import android.view.View;

public interface IControlFramgentController
{
  public abstract void linkUnlinkClick(View v);

  public abstract void startStopClick(View v);

  public abstract void unbindService();

  public abstract void bindService();

  public abstract void startService();
}
