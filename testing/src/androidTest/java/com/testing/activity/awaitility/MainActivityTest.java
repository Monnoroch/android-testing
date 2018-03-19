package com.testing.activity.awaitility;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.testing.MainActivity;
import com.testing.MainApplication;
import com.testing.R;
import com.testing.user.dagger.UserFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Tests for {@link MainApplication} that use Awaitility. */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule =
      new ActivityTestRule<>(MainActivity.class);

  @Test
  public void awaitUserFragmentOpened() {
    await()
        .atMost(5, SECONDS)
        .until(
            () ->
                activityTestRule
                        .getActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.container)
                    instanceof UserFragment);
  }
}
