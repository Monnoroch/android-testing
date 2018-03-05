package com.testing;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

/** Represents an application context of the application. */
public class MainApplication extends Application {

  private ApplicationComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
    component = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
  }

  /**
   * Get application component instance.
   *
   * @return application component for providing dependencies.
   */
  public ApplicationComponent getComponent() {
    return component;
  }

  /**
   * Set test component for replace dependencies in tests.
   *
   * @param component - application component for providing dependencies.
   */
  @VisibleForTesting
  public void setComponentForTest(ApplicationComponent component) {
    this.component = component;
  }
}
