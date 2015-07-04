package de.nachregenkommtsonne.myospherocontrol.activity;

import android.view.View;
import android.view.View.OnClickListener;

public class StartStopClickListener implements OnClickListener
{
  private BackgroundServiceConnection _myServiceConnection;

  public StartStopClickListener(BackgroundServiceConnection myServiceConnection)
  {
    _myServiceConnection = myServiceConnection;
  }

  public void onClick(View arg0)
  {
    _myServiceConnection.get_myBinder().buttonClicked();
  }
}