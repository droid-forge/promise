package promise.promisedb.query.projection;

import promise.model.List;
import promise.promisedb.Utils;

public class ConstantProjection extends Projection {
  private Object constant;

  public ConstantProjection(Object constant) {
    this.constant = constant;
  }

  @Override
  public String build() {
    if (constant != null) return "?";
    else return "NULL";
  }

  @Override
  public List<String> buildParameters() {
    if (constant != null) {
      List<Object> ret = new List<>();
      ret.add(constant);
      return ret.map(
          String::valueOf);
    } else return Utils.EMPTY_LIST.map(
        String::valueOf);
  }
}
