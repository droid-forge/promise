package me.dev4vin.samplepromiseapp.error;

public class ServerError extends Exception {
  private int code;

  public ServerError(String message) {
    super(message);
  }

  public int code() {
    return code;
  }

  public ServerError code(int code) {
    this.code = code;
    return this;
  }
}
