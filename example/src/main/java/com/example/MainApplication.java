package com.example;

import android.app.Application;

public class MainApplication extends Application {

  private ApplicationComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
    component = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
  }

  public ApplicationComponent getComponent() {
    return component;
  }

  public void setComponentForTest(ApplicationComponent component) {
    this.component = component;
  }
}
