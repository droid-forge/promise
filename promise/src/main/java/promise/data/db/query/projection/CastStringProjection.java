package promise.data.db.query.projection;

import promise.data.db.Utils;
import promise.model.List;
import promise.model.function.MapFunction;

public class CastStringProjection extends Projection {
  private Projection projection;

  public CastStringProjection(Projection projection) {
    this.projection = projection;
  }

  @Override
  public String build() {
    String ret = (projection != null ? projection.build() : "");
    return "CAST(" + ret + " AS TEXT)";
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
