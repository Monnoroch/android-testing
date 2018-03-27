package com.testing.user.rx.timeout.withoutrule;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.testing.user.rx.NameRepository;
import com.testing.user.rx.timeout.UserPresenter;
import io.reactivex.Single;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class UserPresenterTest {

  @Rule public final MockitoRule rule = MockitoJUnit.rule();

  @Mock UserPresenter.Listener listener;
  @Mock NameRepository nameRepository;
  UserPresenter presenter;

  @Before
  public void setUp() {
    when(nameRepository.getName()).thenReturn(Single.just("Sasha"));
    presenter = new UserPresenter(listener, nameRepository);
  }

  @Test
  public void getUserName() {
    presenter.getUserName();
    verifyNoMoreInteractions(listener);
  }
}
