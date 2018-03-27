package com.testing.user.awaitility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.testing.common.FileReader;
import com.testing.user.rx.NameRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;

public class UserFragment extends Fragment {

  private TextView textView;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    textView = new TextView(getActivity());
    createNameRepository()
        .getName()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(name -> textView.setText(name));
    return textView;
  }

  private NameRepository createNameRepository() {
    return new NameRepository(
        new FileReader(
            new File(getContext().getFilesDir().getAbsoluteFile() + File.separator + "test_file")));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    textView = null;
  }
}
