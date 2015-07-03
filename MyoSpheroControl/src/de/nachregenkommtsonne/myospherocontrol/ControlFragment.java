package de.nachregenkommtsonne.myospherocontrol;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.nachregenkommtsonne.myospherocontrol.R;

public class ControlFragment extends Fragment
{
  private GuiStateHinter _guiStateHinter;
  private MyServiceConnection _myServiceConnection;

  public ControlFragment()
  {
    _guiStateHinter = new GuiStateHinter();
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    Button startStopButton = (Button) rootView.findViewById(R.id.startStopButton);
    TextView linkUnlinkButton = (TextView) rootView.findViewById(R.id.linkUnlinkButton);

    String startLabel = getString(R.string.startLabel);
    String stopLabel = getString(R.string.stopLabel);

    startStopButton.setText(ServiceState.OBgetInstance().isRunning() ? stopLabel : startLabel);
    startStopButton.setOnClickListener(new StartStopClickListener(this));
    linkUnlinkButton.setOnClickListener(new LinkUnlinkClickListener(this, _myServiceConnection.get_myBinder().getState()));

    return rootView;
  }

  public void buttonClicked()
  {
    _myServiceConnection.get_myBinder().buttonClicked();
  }

  public void unlinkClicked()
  {
    _myServiceConnection.get_myBinder().unlinkClicked();
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

    _myServiceConnection = new MyServiceConnection(this);
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

  public void updateUiOnUiThread()
  {
    final ServiceState state = _myServiceConnection.get_myBinder().getState();

    Activity activity = getActivity();

    if (activity == null)
      return;

    UiUpdater uiUpdater = new UiUpdater(this, state);
    activity.runOnUiThread(uiUpdater);
  }

  @SuppressWarnings("deprecation")
  public void updateUI(ServiceState serviceState)
  {
    ImageView myoLinkedIcon = (ImageView) getView().findViewById(R.id.myoLinkedIcon);
    ImageView myoConnectedIcon = (ImageView) getView().findViewById(R.id.myoConnectedIcon);
    ImageView myoSyncronizedIcon = (ImageView) getView().findViewById(R.id.myoSyncronizedIcon);
    ImageView spheroDiscoveredIcon = (ImageView) getView().findViewById(R.id.spheroDiscoveredIcon);
    ImageView spheroConnectedIcon = (ImageView) getView().findViewById(R.id.spheroConnectedIcon);
    TextView hintText = (TextView) getView().findViewById(R.id.hintText);
    Button startStopButton = (Button) getView().findViewById(R.id.startStopButton);
    TextView linkUnlinkButton = (TextView) getView().findViewById(R.id.linkUnlinkButton);

    MyoStatus myoStatus = serviceState.getMyoStatus();
    SpheroStatus spheroStatus = serviceState.getSpheroStatus();
    BluetoothState bluetoothStatus = serviceState.getBluetoothState();

    int hintResource = _guiStateHinter.getHint(serviceState);
    String hint = getActivity().getString(hintResource);
    Resources resources = getActivity().getResources();

    int myoLinkedDrawableResource = (myoStatus == MyoStatus.linked || myoStatus == MyoStatus.notSynced
        || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
    Drawable myoLinkedDrawable = resources.getDrawable(myoLinkedDrawableResource);
    myoLinkedIcon.setImageDrawable(myoLinkedDrawable);

    int myoConnectedDrawableResource = (myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected)
        ? R.drawable.ic_ok : android.R.drawable.ic_delete;
    Drawable myoConnectedDrawable = resources.getDrawable(myoConnectedDrawableResource);
    myoConnectedIcon.setImageDrawable(myoConnectedDrawable);

    int myoSyncronizedDrawableResource = (myoStatus == MyoStatus.connected) ? R.drawable.ic_ok
        : android.R.drawable.ic_delete;
    Drawable myoSyncronizedDrawable = resources.getDrawable(myoSyncronizedDrawableResource);
    myoSyncronizedIcon.setImageDrawable(myoSyncronizedDrawable);

    int spheroDiscoveredDrawableResource = (spheroStatus == SpheroStatus.connecting
        || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete;
    Drawable spheroDiscoveredDrawable = resources.getDrawable(spheroDiscoveredDrawableResource);
    spheroDiscoveredIcon.setImageDrawable(spheroDiscoveredDrawable);

    int spheroConnectedDrawableResource = (spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok
        : android.R.drawable.ic_delete;
    Drawable spheroConnectedDrawable = resources.getDrawable(spheroConnectedDrawableResource);
    spheroConnectedIcon.setImageDrawable(spheroConnectedDrawable);

    String startLabel = getString(R.string.startLabel);
    String stopLabel = getString(R.string.stopLabel);
    startStopButton.setText(serviceState.isRunning() ? stopLabel : startLabel);

    hintText.setText(hint);

    if (myoStatus == MyoStatus.notLinked && serviceState.isRunning() && bluetoothStatus == BluetoothState.on)
    {
      String linkLabel = getString(R.string.clickToLink);
      linkUnlinkButton.setText(linkLabel);
      linkUnlinkButton.setVisibility(View.VISIBLE);
    }
    else if (myoStatus != MyoStatus.notLinked && !serviceState.isRunning())
    {
      String unlinkLabel = getString(R.string.clickToUnlink);
      linkUnlinkButton.setText(unlinkLabel);
      linkUnlinkButton.setVisibility(View.VISIBLE);
    }
    else
    {
      linkUnlinkButton.setVisibility(View.GONE);
    }
  }
}