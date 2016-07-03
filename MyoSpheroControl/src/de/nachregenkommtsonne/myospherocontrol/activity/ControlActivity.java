package de.nachregenkommtsonne.myospherocontrol.activity;

import de.nachregenkommtsonne.myospherocontrol.R;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class ControlActivity extends Activity
{
  private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

  @TargetApi(23)
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    if (savedInstanceState == null)
    {
      showControlFragment();
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
      if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
      {
        requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, PERMISSION_REQUEST_COARSE_LOCATION);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
  {
    switch (requestCode)
      {
      case PERMISSION_REQUEST_COARSE_LOCATION:
      {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
        {
          finish();
        }
        return;
      }
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
