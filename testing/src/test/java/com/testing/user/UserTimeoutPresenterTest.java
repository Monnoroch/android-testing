package com.testing.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.testing.rules.RxTimeoutTestSchedulerRule;
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

/**
 * Tests for {@link UserTimeoutPresenter}.
 */
@RunWith(RobolectricTestRunner.class)
public class UserTimeoutPresenterTest {

    private static final int TIMEOUT_SEC = 2;
    private static final String NAME = "Sasha";
    @Rule public final MockitoRule rule = MockitoJUnit.rule();
    @Rule public final RxTimeoutTestSchedulerRule timeoutRule = new RxTimeoutTestSchedulerRule();

    @Mock UserTimeoutPresenter.Listener listener;
    @Mock RxNameRepository nameRepository;
    PublishSubject<String> nameObservable = PublishSubject.create();
    UserTimeoutPresenter presenter;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        when(nameRepository.getName()).thenReturn(nameObservable);
        presenter = new UserTimeoutPresenter(listener, nameRepository);
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
