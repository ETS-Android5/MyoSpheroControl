package de.nachregenkommtsonne.myospherocontrol.controller;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.ConnectorState;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;

public class GuiController implements IGuiCapabilities {

	ControlFragment _placeholderFragment;
	GuiStateHinter _guGuiStateHinter;
	boolean _viewActive;

	public GuiController(ControlFragment placeholderFragment) {
		_placeholderFragment = placeholderFragment;
		_guGuiStateHinter = new GuiStateHinter();
		_viewActive = false;
	}

	public void setDisabled() {
		ConnectorState.getInstance().setRunning(false);
		updateUI();
	}

	public void setEnabled() {
		ConnectorState.getInstance().setRunning(true);
		updateUI();
	}

	public void toast(String message) {
		Toast.makeText(_placeholderFragment.getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	public void informBluetoothState(BluetoothState bluetoothState) {
		ConnectorState.getInstance().setBluetoothEnabled(bluetoothState);
		updateUI();
	}

	public void informSpheroState(SpheroStatus spheroState) {
		ConnectorState.getInstance().setSpheroStatus(spheroState);
		updateUI();
	}

	public void informMyoState(MyoStatus myoStatus) {
		ConnectorState.getInstance().setMyoStatus(myoStatus);
		updateUI();
	}

	private void updateUI() {
		if (!_viewActive)
			return;

		ImageView myoLinkedIcon = (ImageView) _placeholderFragment.getView().findViewById(R.id.myoLinkedIcon);
		ImageView myoConnectedIcon = (ImageView) _placeholderFragment.getView().findViewById(R.id.myoConnectedIcon);
		ImageView myoSyncronizedIcon = (ImageView) _placeholderFragment.getView().findViewById(R.id.myoSyncronizedIcon);
		ImageView spheroDiscoveredIcon = (ImageView) _placeholderFragment.getView().findViewById(R.id.spheroDiscoveredIcon);
		ImageView spheroConnectedIcon = (ImageView) _placeholderFragment.getView().findViewById(R.id.spheroConnectedIcon);
		TextView hintText = (TextView) _placeholderFragment.getView().findViewById(R.id.hintText);
		
		MyoStatus myoStatus = ConnectorState.getInstance().getMyoStatus();
		SpheroStatus spheroStatus = ConnectorState.getInstance().getSpheroStatus();
		String hint = _guGuiStateHinter.getHint(ConnectorState.getInstance());

		myoLinkedIcon.setImageResource((myoStatus == MyoStatus.connecting || myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		myoConnectedIcon.setImageResource((myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		myoSyncronizedIcon.setImageResource((myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		spheroDiscoveredIcon.setImageResource((spheroStatus == SpheroStatus.connecting || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		spheroConnectedIcon.setImageResource((spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		
		hintText.setText(hint);
	}

	public void EnableView() {
		_viewActive = true;
		updateUI();
	}

	public void DisableView() {
		_viewActive = false;
	}
}
