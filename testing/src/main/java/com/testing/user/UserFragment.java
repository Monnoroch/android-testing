package com.testing.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.common.FileReader;
import java.io.File;
import java.io.IOException;

public class UserFragment extends Fragment {

  private TextView textView;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    NameRepository nameRepository =
        new NameRepository(
            new FileReader(
                new File(
                    getContext().getFilesDir().getAbsoluteFile() + File.separator + "test_file")));
    textView = new TextView(getActivity());
    try {
      textView.setText(nameRepository.getName());
    } catch (IOException exception) {
      textView.setText(exception.getMessage());
    }

    return textView;
  }
}
