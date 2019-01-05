package me.dev4vin.samplepromiseapp.auth;

public class Role {
  public static final String CAN_ADD_TODO = "can_add_todo", CAN_UPDATE_TODO = "can_update_todo";

  private String name;
  private boolean allowed;

  public String name() {
    return name;
  }

  public Role name(String name) {
    this.name = name;
    return this;
  }

  public boolean allowed() {
    return allowed;
  }

  public Role allowed(boolean allowed) {
    this.allowed = allowed;
    return this;
  }
}
