package de.nachregenkommtsonne.myospherocontrol.controller;

import android.widget.Toast;
import de.nachregenkommtsonne.myospherocontrol.MainActivity.PlaceholderFragment;
import de.nachregenkommtsonne.myospherocontrol.interfaces.IGuiCapabilities;

public class GuiController implements IGuiCapabilities {

	PlaceholderFragment _placeholderFragment;

	public GuiController(PlaceholderFragment placeholderFragment) {
		_placeholderFragment = placeholderFragment;
	}

	public void setDisabled() {
		Toast.makeText(_placeholderFragment.getActivity(), "setDisabled", Toast.LENGTH_SHORT).show();
	}

	public void setEnabled() {
		Toast.makeText(_placeholderFragment.getActivity(), "setEnabled", Toast.LENGTH_SHORT).show();
	}

	public void informNoBluetooth() {
		Toast.makeText(_placeholderFragment.getActivity(), "informNoBluetooth", Toast.LENGTH_SHORT).show();
	}

	public void informNoSync() {
		Toast.makeText(_placeholderFragment.getActivity(), "informNoSync", Toast.LENGTH_SHORT).show();
	}

	public void informNoSphero() {
		Toast.makeText(_placeholderFragment.getActivity(), "informNoSphero", Toast.LENGTH_SHORT).show();
	}

	public void toast(String message){
		Toast.makeText(_placeholderFragment.getActivity(), message, Toast.LENGTH_SHORT).show();
	}
}
