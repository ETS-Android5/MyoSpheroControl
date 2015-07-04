package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

public interface IChangedNotifier
{

  void onChanged();

  void setServiceBinder(ServiceBinder binder);

}
