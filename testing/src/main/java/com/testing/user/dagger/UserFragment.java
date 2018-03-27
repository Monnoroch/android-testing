package com.testing.user.dagger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.MainApplication;
import com.testing.user.rx.NameRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class UserFragment extends Fragment {

  private TextView textView;
  private Disposable disposable;
  @Inject NameRepository nameRepository;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ((MainApplication) getActivity().getApplication())
        .getComponent()
        .createUserComponent()
        .injectsUserFragment(this);
    textView = new TextView(getActivity());
    disposable =
        nameRepository
            .getName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(name -> textView.setText(name));
    return textView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    disposable.dispose();
    textView = null;
  }
}
