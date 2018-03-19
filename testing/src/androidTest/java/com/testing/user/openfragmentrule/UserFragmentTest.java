package com.testing.user.openfragmentrule;

import static com.google.common.truth.Truth.assertThat;

import android.support.test.runner.AndroidJUnit4;
import com.testing.MainActivity;
import com.testing.R;
import com.testing.rules.FragmentTestRule;
import com.testing.user.dagger.UserFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link UserFragment}. */
@RunWith(AndroidJUnit4.class)
public class UserFragmentTest {

  @Rule
  public final FragmentTestRule<MainActivity, UserFragment> fragmentRule =
      new FragmentTestRule<>(MainActivity.class, new UserFragment(), 5);

  @Test
  public void checkFragmentOpened() {
    assertThat(
            fragmentRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.container))
        .isInstanceOf(UserFragment.class);
  }
}
