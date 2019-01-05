package me.dev4vin.samplepromiseapp.auth;

import me.yoctopus.model.List;

public class User {
  private String email, password, names;
  private List<Role> roles;

  public String email() {
    return email;
  }

  public User email(String email) {
    this.email = email;
    return this;
  }

  public String password() {
    return password;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  public String names() {
    return names;
  }

  public User names(String names) {
    this.names = names;
    return this;
  }

  public List<Role> roles() {
    return roles;
  }

  public User roles(List<Role> roles) {
    this.roles = roles;
    return this;
  }
}
