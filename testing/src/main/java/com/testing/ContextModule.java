package com.testing;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@SuppressWarnings("SingleLineJavadoc")
/** A {@link Module} for providing the application context. */
@Module
public class ContextModule {

    private final Context context;

    /**
     * Create context module instance for providing context.
     *
     * @param context - application context.
     */
    public ContextModule(Context context) {
        this.context = context;
    }

    /**
     * Provide application context.
     */
    @Provides
    Context provideContext() {
        return context;
    }
}
