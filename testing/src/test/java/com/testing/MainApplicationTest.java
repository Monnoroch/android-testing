package com.testing;

import static com.google.common.truth.Truth.assertThat;

import com.testing.robolectric.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, application = MainApplication.class)
public class MainApplicationTest {

  @Test
  public void packageName() {
    assertThat(RuntimeEnvironment.application).isInstanceOf(MainApplication.class);
  }
}
