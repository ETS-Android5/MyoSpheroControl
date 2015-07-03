package de.nachregenkommtsonne.myospherocontrol;

import de.nachregenkommtsonne.myospherocontrol.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ControlActivity extends Activity
{
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.activity_main);
    
    if (savedInstanceState == null)
    {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      
      ControlFragment controlFragment = new ControlFragment();
      fragmentTransaction.add(R.id.container, controlFragment);
      fragmentTransaction.commit();
    }
  }
}
