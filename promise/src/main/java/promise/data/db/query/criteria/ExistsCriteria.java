package promise.data.db.query.criteria;

import promise.data.db.Utils;
import promise.data.db.query.QueryBuilder;
import promise.model.List;
import promise.model.function.MapFunction;

public class ExistsCriteria extends Criteria {
  private QueryBuilder subQuery;

  public ExistsCriteria(QueryBuilder subQuery) {
    this.subQuery = subQuery;
  }

  @Override
  public String build() {
    String ret = "EXISTS(";

    if (subQuery != null) ret = ret + subQuery.build();

    ret = ret + ")";
    return ret;
  }

  @Override
  public List<String> buildParameters() {
    if (subQuery != null) return List.fromArray(subQuery.buildParameters());
    else {
      return Utils.EMPTY_LIST.map(
          new MapFunction<String, Object>() {
            @Override
            public String from(Object o) {
              return String.valueOf(o);
            }
          });
    }
  }
}
