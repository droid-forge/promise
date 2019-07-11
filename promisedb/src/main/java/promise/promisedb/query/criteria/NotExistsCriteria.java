package promise.promisedb.query.criteria;


import promise.promisedb.query.QueryBuilder;

public class NotExistsCriteria extends ExistsCriteria {
  public NotExistsCriteria(QueryBuilder subQuery) {
    super(subQuery);
  }

  @Override
  public String build() {
    return "NOT " + super.build();
  }
}
