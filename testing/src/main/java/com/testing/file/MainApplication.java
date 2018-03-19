package com.testing.file;

import android.app.Application;
import java.io.File;

/** Represents an application context of the application. */
public class MainApplication extends Application {

  private File fileWithName = new File("testFile");

  public void setFile(File file) {
    fileWithName = file;
  }

  public File getFile() {
    return fileWithName;
  }
}
