package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BackgroundService;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class ControlFragment extends Fragment
{
  private IGuiStateHinter _guiStateHinter;
  private BackgroundServiceConnection _myServiceConnection;
  private ControlFragmentUpdateUI _controlFragmentUpdateUI;
  UiOnUiThreadUpdater _uiOnUiThreadUpdater;
  

  public ControlFragment()
  {
    _guiStateHinter = new GuiStateHinter();
    _myServiceConnection = new BackgroundServiceConnection(this);
    _controlFragmentUpdateUI = new ControlFragmentUpdateUI(this, _guiStateHinter);
    _uiOnUiThreadUpdater = new UiOnUiThreadUpdater(_myServiceConnection);
  }

  // TODO: extract controller
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    Button startStopButton = (Button) rootView.findViewById(R.id.startStopButton);
    TextView linkUnlinkButton = (TextView) rootView.findViewById(R.id.linkUnlinkButton);

    startStopButton.setOnClickListener(new StartStopClickListener(_myServiceConnection));
    linkUnlinkButton.setOnClickListener(new LinkUnlinkClickListener(this, _myServiceConnection));

    return rootView;
  }

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    Activity activity = getActivity();

    Intent intent = new Intent(activity, BackgroundService.class);
    activity.startService(intent);
  }

  public void onResume()
  {
    super.onResume();

    Activity activity = getActivity();

    Intent intent = new Intent(activity, BackgroundService.class);
    activity.bindService(intent, _myServiceConnection, ControlActivity.BIND_AUTO_CREATE);
  }

  public void onPause()
  {
    super.onPause();

    // TODO: stop service when not running here!
    getActivity().unbindService(_myServiceConnection);
  }

  public class UiOnUiThreadUpdater
  {
    private BackgroundServiceConnection _myServiceConnection;
    
    public UiOnUiThreadUpdater(BackgroundServiceConnection myServiceConnection)
    {
      _myServiceConnection = myServiceConnection;
    }
    
    public void updateUiOnUiThread(ControlFragment controlFragment)
    {
      ServiceState state = _myServiceConnection.get_myBinder().getState();

      Activity activity = controlFragment.getActivity();

      if (activity == null)
        return;

      UiUpdater uiUpdater = new UiUpdater(state, _controlFragmentUpdateUI, controlFragment.getView(), activity);
      activity.runOnUiThread(uiUpdater);
    }
  }

  // TODO: move to uiUpdater and caller
  public void updateUiOnUiThread()
  {
    _uiOnUiThreadUpdater.updateUiOnUiThread(this);
  }
}