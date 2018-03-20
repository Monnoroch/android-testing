package com.testing.rules;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A rule to be used for unlocking screen. Screen will be unlocked before each test annotated
 * with @Test and before methods annotated with @Before.
 *
 * @param <A> - the activity to test.
 */
class UnlockScreenRule<A extends AppCompatActivity> implements TestRule {

  ActivityTestRule<A> activityRule;

  /**
   * Create Emulator Rule, don't forget to add @Rule.
   *
   * @param activityRule - Activity Test Rule for unlocking screen.
   */
  UnlockScreenRule(ActivityTestRule<A> activityRule) {
    this.activityRule = activityRule;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        activityRule.runOnUiThread(
            () ->
                activityRule
                    .getActivity()
                    .getWindow()
                    .addFlags(
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON));
        statement.evaluate();
      }
    };
  }
}
