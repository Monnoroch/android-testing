package com.example.user;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.rules.DebugRule;
import com.example.rules.RxImmediateSchedulerRule;
import io.reactivex.subjects.PublishSubject;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class UserPresenterDebugTest {

  private static final String NAME = "Sasha";
  @Rule public final DebugRule debugRule = new DebugRule();
  @Rule public final MockitoRule mockitoRule = MockitoJUnit.rule();
  @Rule public final RxImmediateSchedulerRule timeoutRule = new RxImmediateSchedulerRule();

  @Mock UserPresenter.Listener listener;
  @Mock NameRepository nameRepository;
  @Mock Logger logger;
  PublishSubject<String> nameObservable = PublishSubject.create();
  UserPresenter presenter;

  @Before
  public void setUp() {
    when(nameRepository.getName()).thenReturn(nameObservable.firstOrError());
    presenter = new UserPresenter(listener, nameRepository, logger);
  }

  @Test
  public void userNameLogged() {
    presenter.getUserName();
    timeoutRule.getTestScheduler().triggerActions();
    nameObservable.onNext(NAME);
    verify(logger).info(contains(NAME));
  }
}
