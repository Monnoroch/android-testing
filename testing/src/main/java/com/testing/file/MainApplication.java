package com.testing.file;

import android.app.Application;
import java.io.File;

public class MainApplication extends Application {

  private File fileWithName = new File("testFile");

  public void setFile(File file) {
    fileWithName = file;
  }

  public File getFile() {
    return fileWithName;
  }
}
