package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.myo.MyoStatus;
import de.nachregenkommtsonne.myospherocontrol.service.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

public class GuiStateHinter
{
  public int getHint(ServiceState guiState)
  {
    if (!guiState.isRunning())
    {
      return R.string.pressStart;
    }

    if (guiState.getBluetoothState() != BluetoothState.on)
    {
      if (guiState.getBluetoothState() == BluetoothState.turningOn)
        return R.string.bluetoothTurningOn;

      return R.string.pleaseEnableBluetooth;
    }

    if (guiState.getMyoStatus() != MyoStatus.connected)
    {
      if (guiState.getMyoStatus() == MyoStatus.notSynced)
        return R.string.myoNotSynced;

      if (guiState.getMyoStatus() == MyoStatus.linked)
        return R.string.connectingToMyo;

      if (guiState.getMyoStatus() == MyoStatus.notLinked)
        return R.string.linkMyo;
    }

    if (guiState.getSpheroStatus() != SpheroStatus.connected)
    {
      if (guiState.getSpheroStatus() == SpheroStatus.connecting)
        return R.string.connectingToSphero;

      if (guiState.getSpheroStatus() == SpheroStatus.discovering)
        return R.string.searchingSphero;
    }

    if (guiState.getSpheroStatus() == SpheroStatus.connected && guiState.getMyoStatus() == MyoStatus.connected)
      return R.string.controllerActive;

    return R.string.heavyError;
  }
}
