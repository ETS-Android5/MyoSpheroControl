package de.nachregenkommtsonne.myospherocontrol.controller;

import android.widget.TextView;
import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.GuiState;
import de.nachregenkommtsonne.myospherocontrol.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.SpheroStatus;
import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;

public class GuiController implements IGuiCapabilities {

	PlaceholderFragment _placeholderFragment;
	GuiState _guiState;

	public GuiController(PlaceholderFragment placeholderFragment, GuiState guiState) {
		_placeholderFragment = placeholderFragment;
		_guiState = guiState;
	}

	public void setDisabled() {
		Toast.makeText(_placeholderFragment.getActivity(), "setDisabled", Toast.LENGTH_SHORT).show();
	}

	public void setEnabled() {
		Toast.makeText(_placeholderFragment.getActivity(), "setEnabled", Toast.LENGTH_SHORT).show();
	}

	public void toast(String message){
		Toast.makeText(_placeholderFragment.getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	public void informBluetoothState(BluetoothState bluetoothState) {
		_guiState.setBluetoothEnabled(bluetoothState);
		
		TextView textView = (TextView) _placeholderFragment.getView().findViewById(R.id.btState);
		textView.setText(bluetoothState.toString());
	}

	public void informSpheroState(SpheroStatus spheroState){
		_guiState.setSpheroStatus(spheroState);

		TextView textView = (TextView) _placeholderFragment.getView().findViewById(R.id.spheroState);
		textView.setText(spheroState.toString());
}

	public void informMyoState(MyoStatus myoStatus) {
		_guiState.setMyoStatus(myoStatus);

		TextView textView = (TextView) _placeholderFragment.getView().findViewById(R.id.myoState);
		textView.setText(myoStatus.toString());
}	
}
