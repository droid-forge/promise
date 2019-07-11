package promise.promisedb.query.order;

import promise.model.List;
import promise.promisedb.Utils;
import promise.promisedb.query.projection.Projection;

public class OrderDescendingIgnoreCase extends Order {

  public OrderDescendingIgnoreCase(Projection projection) {
    super(projection);
  }

  @Override
  public String build() {
    String ret = " COLLATE NOCASE DESC";

    if (projection != null) ret = projection.build() + ret;

    return ret;
  }

  @Override
  public List<String> buildParameters() {
    if (projection != null) return projection.buildParameters();
    else
      return Utils.EMPTY_LIST.map(
          String::valueOf);
  }
}
