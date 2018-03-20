package com.testing.user;

import com.testing.user.dagger.UserFragment;
import dagger.Subcomponent;

@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

  void injectsUserFragment(UserFragment userFragment);
}
