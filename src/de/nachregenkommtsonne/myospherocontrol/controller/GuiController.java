package de.nachregenkommtsonne.myospherocontrol.controller;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.GuiState;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.ControlActivity.ControlFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;

public class GuiController implements IGuiCapabilities {

	ControlFragment _placeholderFragment;
	GuiState _guiState;
	GuiStateHinter _guGuiStateHinter;
	boolean _viewActive;

	public GuiController(ControlFragment placeholderFragment, GuiState guiState) {
		_placeholderFragment = placeholderFragment;
		_guiState = guiState;
		_guGuiStateHinter = new GuiStateHinter();
		_viewActive = false;
	}

	public void setDisabled() {
		_guiState.setRunning(false);
		updateUI();
	}

	public void setEnabled() {
		_guiState.setRunning(true);
		updateUI();
	}

	public void toast(String message) {
		Toast.makeText(_placeholderFragment.getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	public void informBluetoothState(BluetoothState bluetoothState) {
		_guiState.setBluetoothEnabled(bluetoothState);
		updateUI();
	}

	public void informSpheroState(SpheroStatus spheroState) {
		_guiState.setSpheroStatus(spheroState);
		updateUI();
	}

	public void informMyoState(MyoStatus myoStatus) {
		_guiState.setMyoStatus(myoStatus);
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
		
		MyoStatus myoStatus = _guiState.getMyoStatus();
		SpheroStatus spheroStatus = _guiState.getSpheroStatus();
		String hint = _guGuiStateHinter.getHint(_guiState);

		myoLinkedIcon.setImageResource((myoStatus == MyoStatus.connecting || myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		myoConnectedIcon.setImageResource((myoStatus == MyoStatus.notSynced || myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		myoSyncronizedIcon.setImageResource((myoStatus == MyoStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		spheroDiscoveredIcon.setImageResource((spheroStatus == SpheroStatus.connecting || spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		spheroConnectedIcon.setImageResource((spheroStatus == SpheroStatus.connected) ? R.drawable.ic_ok : android.R.drawable.ic_delete);
		hintText.setText(hint);
	}

	public void EnableView() {
		_viewActive = true;
	}

	public void DisableView() {
		_viewActive = false;
	}
}
