package de.nachregenkommtsonne.myospherocontrol.activity;

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
      showControlFragment();
    }
  }

  public void showControlFragment()
  {
    FragmentManager fragmentManager = getFragmentManager();
    ControlFragment controlFragment = new ControlFragment();
    
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    
    fragmentTransaction.add(R.id.container, controlFragment);
    fragmentTransaction.commit();
  }
}
