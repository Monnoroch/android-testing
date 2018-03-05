package com.testing.user;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.nio.charset.StandardCharsets.UTF_8;

import android.support.test.runner.AndroidJUnit4;
import com.testing.MainActivity;
import com.testing.common.ActivityUtils;
import com.testing.rules.FragmentAsyncTestRule;
import com.testing.rules.FragmentTestRule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/** Tests for {@link UserFragment}. */
@RunWith(AndroidJUnit4.class)
public class UserFragmentTest {

  private static final String FILE_CONTENT = "{name : Sasha}";

  @ClassRule
  public static TestRule asyncRule =
      new FragmentAsyncTestRule<>(MainActivity.class, new UserFragment());

  @Rule
  public final FragmentTestRule<MainActivity, UserFragment> fragmentRule =
      new FragmentTestRule<>(MainActivity.class, new UserFragment(), 5);

  File file;

  /** Set up. */
  @Before
  public void setUp() throws FileNotFoundException {
    file =
        new File(
            fragmentRule.getActivity().getFilesDir().getAbsoluteFile()
                + File.separator
                + "test_file");
    PrintWriter writer =
        new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8)), true);
    writer.println(FILE_CONTENT);
    writer.close();
  }

  @Test
  public void getName() {
    ActivityUtils.openFragment(fragmentRule.getActivity(), new UserFragment());
    onView(withText("Sasha")).check(matches(isDisplayed()));
  }

  /** Tear down. */
  @After
  public void tearDown() {
    boolean isFileDeleted = file.delete();
  }
}
