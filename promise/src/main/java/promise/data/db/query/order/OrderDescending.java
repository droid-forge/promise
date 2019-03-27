package promise.data.db.query.order;

import promise.data.db.Utils;
import promise.data.db.query.projection.Projection;
import promise.model.List;
import promise.model.function.MapFunction;

public class OrderDescending extends Order {

  public OrderDescending(Projection projection) {
    super(projection);
  }

  @Override
  public String build() {
    String ret = " DESC";

    if (projection != null) ret = projection.build() + ret;

    return ret;
  }

  @Override
  public List<String> buildParameters() {
    if (projection != null) return projection.buildParameters();
    else
      return Utils.EMPTY_LIST.map(
          new MapFunction<String, Object>() {
            @Override
            public String from(Object o) {
              return String.valueOf(o);
            }
          });
  }
}
