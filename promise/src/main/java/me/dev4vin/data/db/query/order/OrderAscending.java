package me.dev4vin.data.db.query.order;

import me.dev4vin.data.db.Utils;
import me.dev4vin.data.db.query.projection.Projection;
import me.dev4vin.model.List;
import me.dev4vin.model.function.MapFunction;

public class OrderAscending extends Order {

  public OrderAscending(Projection projection) {
    super(projection);
  }

  @Override
  public String build() {
    String ret = " ASC";

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
