package com.example.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.MainApplication;
import javax.inject.Inject;

public class UserFragment extends Fragment implements UserPresenter.Listener {

  private TextView textView;
  @Inject UserPresenter userPresenter;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ((MainApplication) getActivity().getApplication())
        .getComponent()
        .createUserComponent(new UserModule(this))
        .injectsUserFragment(this);
    textView = new TextView(getActivity());
    userPresenter.getUserName();
    return textView;
  }

  @Override
  public void onUserNameLoaded(String name) {
    textView.setText(name);
  }

  @Override
  public void onGettingUserNameError(String message) {
    textView.setText(message);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    userPresenter.stopLoading();
    textView = null;
  }
}
