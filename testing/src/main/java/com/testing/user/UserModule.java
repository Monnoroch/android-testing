package com.testing.user;

import android.content.Context;
import com.testing.common.FileReader;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;

@Module
public class UserModule {

  @Provides
  NameRepository provideNameRepository(@Private FileReader fileReader) {
    return new NameRepository(fileReader);
  }

  @Private
  @Provides
  FileReader provideFileReader(@Private File file) {
    return new FileReader(file);
  }

  @Private
  @Provides
  File provideFile(Context context) {
    return new File(context.getFilesDir().getAbsoluteFile() + File.separator + "test_file");
  }

  @Qualifier
  @Retention(RetentionPolicy.RUNTIME)
  private @interface Private {}
}
