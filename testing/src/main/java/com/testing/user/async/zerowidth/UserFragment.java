package com.testing.user.async.zerowidth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.common.FileReader;
import com.testing.user.NameRepository;
import java.io.File;
import java.io.IOException;

public class UserFragment extends Fragment {

  private TextView textView;
  private String name;

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
      name = nameRepository.getName();
    } catch (IOException exception) {
      textView.setText(exception.getMessage());
      return textView;
    }
    int nameWidth = textView.getWidth();
    if (nameWidth == 0) {
      textView.setText("Width of name is equals to 0");
    } else {
      textView.setText(String.format("Width of name %s = %d", name, nameWidth));
    }

    return textView;
  }
}
