package com.testing.common;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.testing.R;

/** Class that helps to replace fragments in activity. */
public class ActivityUtils {

  private ActivityUtils() {}

  /**
   * Replace fragment in R.id.container from activity.
   *
   * @param activity - current AppCompatActivity.
   * @param newFragment - fragment that will replace old fragment.
   */
  public static void openFragment(AppCompatActivity activity, Fragment newFragment) {
    activity
        .getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, newFragment)
        .commitAllowingStateLoss();
  }
}
