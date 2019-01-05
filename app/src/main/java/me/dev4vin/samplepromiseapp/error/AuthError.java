package me.dev4vin.samplepromiseapp.error;

public class AuthError extends Exception {
  private int code;

  public int code() {
    return code;
  }

  public AuthError code(int code) {
    this.code = code;
    return this;
  }
}
