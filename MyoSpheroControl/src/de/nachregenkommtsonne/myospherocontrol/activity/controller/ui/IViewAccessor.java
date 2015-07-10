package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import android.app.Activity;
import android.view.View;

public interface IViewAccessor
{
  public abstract String getString(int resId);

  public abstract Activity getActivity();

  public abstract View getView();
}
