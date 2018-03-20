package com.testing;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

  private final Context context;

  public ContextModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideContext() {
    return context;
  }
}
