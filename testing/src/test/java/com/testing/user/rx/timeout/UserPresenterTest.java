package com.testing.user.rx.timeout;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testing.rules.RxImmediateSchedulerRule;
import com.testing.user.rx.NameRepository;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;
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

  static final int TIMEOUT_SEC = 2;
  static final String NAME = "Sasha";

  @Rule public final MockitoRule rule = MockitoJUnit.rule();
  @Rule public final RxImmediateSchedulerRule timeoutRule = new RxImmediateSchedulerRule();

  @Mock UserPresenter.Listener listener;
  @Mock NameRepository nameRepository;
  PublishSubject<String> nameObservable = PublishSubject.create();
  UserPresenter presenter;

  @Before
  public void setUp() {
    when(nameRepository.getName()).thenReturn(nameObservable.firstOrError());
    presenter = new UserPresenter(listener, nameRepository);
  }

  @Test
  public void getUserName() {
    presenter.getUserName();
    timeoutRule.getTestScheduler().advanceTimeBy(TIMEOUT_SEC - 1, TimeUnit.SECONDS);
    nameObservable.onNext(NAME);
    verify(listener).onUserNameLoaded("Sasha");
  }

  @Test
  public void getUserName_timeout() {
    presenter.getUserName();
    timeoutRule.getTestScheduler().advanceTimeBy(TIMEOUT_SEC + 1, TimeUnit.SECONDS);
    nameObservable.onNext(NAME);
    verify(listener).onGettingUserNameError(any());
  }
}
