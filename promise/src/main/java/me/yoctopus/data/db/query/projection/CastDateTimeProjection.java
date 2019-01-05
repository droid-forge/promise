package me.yoctopus.data.db.query.projection;

import me.yoctopus.data.db.Utils;
import me.yoctopus.model.List;
import me.yoctopus.model.function.MapFunction;

public class CastDateTimeProjection extends Projection {
  private Projection projection;

  public CastDateTimeProjection(Projection projection) {
    this.projection = projection;
  }

  @Override
  public String build() {
    String ret = (projection != null ? projection.build() : "");
    return "DATETIME(" + ret + ")";
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
