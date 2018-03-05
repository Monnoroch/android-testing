package com.testing.rules;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A class that change RxJava schedulers to Immediate scheduler for immediate actions in tests.
 */
public class RxImmediateSchedulerRule implements TestRule {

    private Scheduler immediate = new Scheduler() {
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
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> immediate);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediate);
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> immediate);
                try {
                    base.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
