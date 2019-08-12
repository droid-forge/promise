package promise.promisedb.query.criteria;

import promise.model.List;
import promise.promisedb.Utils;
import promise.promisedb.query.QueryBuilder;

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
          String::valueOf);
    }
  }
}
