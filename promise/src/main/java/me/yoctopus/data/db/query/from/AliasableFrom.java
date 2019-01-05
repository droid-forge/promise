package me.yoctopus.data.db.query.from;

public abstract class AliasableFrom<T> extends From {
  protected String alias;

  @SuppressWarnings("unchecked")
  public T as(String alias) {
    this.alias = alias;
    return (T) this;
  }
}
