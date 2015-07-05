package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class UiUpdater implements Runnable
{
  private ServiceState _serviceState;
  private IViewAccessor _viewAccessor;
  private IGuiStateHinter _guiStateHinter;
  
  public UiUpdater(
      ServiceState state,
      IViewAccessor viewAccessor,
      IGuiStateHinter guiStateHinter)
  {
    _serviceState = state;
    _viewAccessor = viewAccessor;
    _guiStateHinter = guiStateHinter;
  }

  @SuppressWarnings("deprecation")
  public void run()
  {
		View view = _viewAccessor.getView(); 
		Activity activity = _viewAccessor.getActivity();
	  
		ImageView myoLinkedIcon = (ImageView) view.findViewById(R.id.myoLinkedIcon);
    ImageView myoConnectedIcon = (ImageView) view.findViewById(R.id.myoConnectedIcon);
    ImageView myoSyncronizedIcon = (ImageView) view.findViewById(R.id.myoSyncronizedIcon);
    ImageView spheroDiscoveredIcon = (ImageView) view.findViewById(R.id.spheroDiscoveredIcon);
    ImageView spheroConnectedIcon = (ImageView) view.findViewById(R.id.spheroConnectedIcon);
    TextView hintText = (TextView) view.findViewById(R.id.hintText);
    Button startStopButton = (Button) view.findViewById(R.id.startStopButton);
    TextView linkUnlinkButton = (TextView) view.findViewById(R.id.linkUnlinkButton);

    MyoStatus myoStatus = _serviceState.getMyoStatus();
    SpheroStatus spheroStatus = _serviceState.getSpheroStatus();
    BluetoothState bluetoothStatus = _serviceState.getBluetoothState();

    int hintResource = _guiStateHinter.getHint(_serviceState);
    String hint = activity.getString(hintResource);
    Resources resources = activity.getResources();

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

    String startLabel = _viewAccessor.getString(R.string.startLabel);
    String stopLabel = _viewAccessor.getString(R.string.stopLabel);
    startStopButton.setText(_serviceState.isRunning() ? stopLabel : startLabel);

    hintText.setText(hint);

    if (myoStatus == MyoStatus.notLinked && _serviceState.isRunning() && bluetoothStatus == BluetoothState.on)
    {
      String linkLabel = _viewAccessor.getString(R.string.clickToLink);
      linkUnlinkButton.setText(linkLabel);
      linkUnlinkButton.setVisibility(View.VISIBLE);
    }
    else if (myoStatus != MyoStatus.notLinked && !_serviceState.isRunning())
    {
      String unlinkLabel = _viewAccessor.getString(R.string.clickToUnlink);
      linkUnlinkButton.setText(unlinkLabel);
      linkUnlinkButton.setVisibility(View.VISIBLE);
    }
    else
    {
      linkUnlinkButton.setVisibility(View.GONE);
    }
  }
}