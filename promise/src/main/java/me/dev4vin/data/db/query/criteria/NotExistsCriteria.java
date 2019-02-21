package me.dev4vin.data.db.query.criteria;

import me.dev4vin.data.db.query.QueryBuilder;

public class NotExistsCriteria extends ExistsCriteria {
  public NotExistsCriteria(QueryBuilder subQuery) {
    super(subQuery);
  }

  @Override
  public String build() {
    return "NOT " + super.build();
  }
}
