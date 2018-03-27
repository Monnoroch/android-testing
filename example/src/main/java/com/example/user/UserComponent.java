package com.example.user;

import dagger.Subcomponent;

@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

  void injectsUserFragment(UserFragment userFragment);
}
