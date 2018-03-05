package com.testing.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.MainApplication;
import java.io.IOException;
import javax.inject.Inject;

/** Screen that show user name. */
public class UserFragment extends Fragment {

  @Inject UserPresenter userPresenter;
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
      textView.setText(userPresenter.getUserName());
    } catch (IOException exception) {
      textView.setText(exception.getMessage());
    }

    return textView;
  }
}
