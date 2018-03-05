package com.testing.rules;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.testing.common.ActivityUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 *
 * <pre>
 * A {@link TestRule} to be used for testing a {@link Fragment} on async errors, that occurring,
 *     when we use view.post() way to do something after fragment appeared on the screen,
 *     but fragment was destroyed before this and we will get {@link NullPointerException}.
 * This rule is configured with an {@link AppCompatActivity} and a {@link Fragment} classes.
 * To use this rule add the following code to the test:
 * </pre>
 *
 * <pre>{@code
 * @ClassRule TestRule rule = new FragmentAsyncTestRule(MainActivity.class, new MainFragment());
 * }</pre>
 *
 * @param <A> - the {@link AppCompatActivity} class to launch.
 */
public class FragmentAsyncTestRule<A extends AppCompatActivity> implements TestRule {

  private final ActivityTestRule<A> activityRule;
  private final Fragment fragment;

  /**
   * Create {@link FragmentAsyncTestRule} object.
   *
   * @param activityClass - activity test rule.
   * @param fragment - fragment for testing.
   */
  public FragmentAsyncTestRule(Class<A> activityClass, Fragment fragment) {
    this.activityRule = new ActivityTestRule<>(activityClass);
    this.fragment = fragment;
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          // Run all tests in class.
          base.evaluate();
        } finally {
          /**
           * This block will be executed after tests. It launch activity, opens your fragment and at
           * once opens another fragment. Therefore all your fragment's async task will be executed
           * when fragment already destroyed.
           */
          activityRule.launchActivity(new Intent());
          ActivityUtils.openFragment(activityRule.getActivity(), fragment);
          ActivityUtils.openFragment(activityRule.getActivity(), new Fragment());
        }
      }
    };
  }
}
