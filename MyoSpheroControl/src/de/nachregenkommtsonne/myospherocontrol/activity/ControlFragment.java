package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ControlFramgentController;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ControlFramgentControllerFactory;

public class ControlFragment extends Fragment
{
  private ControlFramgentController _controlFramgentController;

  public ControlFragment()
  {
    ControlFramgentControllerFactory controlFramgentControllerFactory = new ControlFramgentControllerFactory(this);
    _controlFramgentController = controlFramgentControllerFactory.create();
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    Button startStopButton = (Button) rootView.findViewById(R.id.startStopButton);
    TextView linkUnlinkButton = (TextView) rootView.findViewById(R.id.linkUnlinkButton);

    startStopButton.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        _controlFramgentController.startStopClick(v);
      }
    });

    linkUnlinkButton.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        _controlFramgentController.linkUnlinkClick(v);
      }
    });

    return rootView;
  }

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    _controlFramgentController.startService();
  }

  public void onResume()
  {
    super.onResume();

    _controlFramgentController.bindService();
  }

  public void onPause()
  {
    super.onPause();

    _controlFramgentController.unbindService();
  }

}