package com.testing.user.dagger;

import static org.mockito.Mockito.verify;

import android.support.test.runner.AndroidJUnit4;
import com.testing.MainActivity;
import com.testing.TestApplicationComponent;
import com.testing.rules.FragmentTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserFragmentTest {

  public final UserFragment userFragment = new UserFragment();

  @Rule
  public final FragmentTestRule<MainActivity, UserFragment> fragmentRule =
      new FragmentTestRule<>(
          MainActivity.class, userFragment, TestApplicationComponent.create());

  @Test
  public void getName() throws Throwable {
    verify(userFragment.nameRepository).getName();
  }
}
