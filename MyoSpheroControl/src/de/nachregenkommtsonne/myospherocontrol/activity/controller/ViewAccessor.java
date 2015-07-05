package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

public class ViewAccessor implements IViewAccessor
{
  Fragment _fragment;

  public ViewAccessor(Fragment fragment)
  {
    _fragment = fragment;
  }

  public View getView()
  {
    return _fragment.getView();
  }

  public Activity getActivity()
  {
    return _fragment.getActivity();
  }
  
  public String getString(int resId)
  {
    return _fragment.getString(resId);
  }
}