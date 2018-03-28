package com.testing.rules;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import java.util.concurrent.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A class that changes RxJava schedulers to Immediate and Test schedulers for immediate actions in
 * tests and waiting timeouts for testing RxJava timeout in host java tests.
 */
public class RxImmediateSchedulerRule implements TestRule {

  private static final TestScheduler TEST_SCHEDULER = new TestScheduler();

  private Scheduler IMMEDIATE_SCHEDULER =
      new Scheduler() {
        @Override
        public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
          // Changing delay to 0 prevents StackOverflowErrors when scheduling with a delay.
          return super.scheduleDirect(run, 0, unit);
        }

        @Override
        public Worker createWorker() {
          return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }
      };

  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> TEST_SCHEDULER);
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> TEST_SCHEDULER);
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> TEST_SCHEDULER);
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> IMMEDIATE_SCHEDULER);
        try {
          base.evaluate();
        } finally {
          RxJavaPlugins.reset();
          RxAndroidPlugins.reset();
        }
      }
    };
  }

  /**
   * Get test scheduler for testing RxJava timeout.
   *
   * @return {@link TestScheduler} object for testing RxJava timeout.
   */
  public TestScheduler getTestScheduler() {
    return TEST_SCHEDULER;
  }
}
