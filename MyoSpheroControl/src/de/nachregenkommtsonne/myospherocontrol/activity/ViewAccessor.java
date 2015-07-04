package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

public class ViewAccessor
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
}