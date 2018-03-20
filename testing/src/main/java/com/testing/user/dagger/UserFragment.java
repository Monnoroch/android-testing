package com.testing.user.dagger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.MainApplication;
import com.testing.user.NameRepository;
import java.io.IOException;
import javax.inject.Inject;

public class UserFragment extends Fragment {

  @Inject public NameRepository nameRepository;
  private TextView textView;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ((MainApplication) getActivity().getApplication())
        .getComponent()
        .createUserComponent()
        .injectsUserFragment(this);
    textView = new TextView(getActivity());
    try {
      textView.setText(nameRepository.getName());
    } catch (IOException exception) {
      textView.setText(exception.getMessage());
    }

    return textView;
  }
}
