package me.yoctopus.data.db.query.from;

import me.yoctopus.data.db.Column;
import me.yoctopus.data.db.Table;
import me.yoctopus.data.db.query.QueryBuilder;
import me.yoctopus.data.db.query.criteria.Criteria;
import me.yoctopus.data.db.query.projection.Projection;
import me.yoctopus.model.List;

public abstract class From {
  public static TableFrom table(Table table) {
    return new TableFrom(table);
  }

  public static SubQueryFrom subQuery(QueryBuilder subQuery) {
    return new SubQueryFrom(subQuery);
  }

  public PartialJoin innerJoin(Table table) {
    return innerJoin(From.table(table));
  }

  public PartialJoin innerJoin(QueryBuilder subQuery) {
    return innerJoin(From.subQuery(subQuery));
  }

  public PartialJoin innerJoin(From table) {
    return new PartialJoin(this, table, "INNER JOIN");
  }

  public PartialJoin leftJoin(Table table) {
    return leftJoin(From.table(table));
  }

  public PartialJoin leftJoin(QueryBuilder subQuery) {
    return leftJoin(From.subQuery(subQuery));
  }

  public PartialJoin leftJoin(From table) {
    return new PartialJoin(this, table, "LEFT JOIN");
  }

  public abstract String build();

  public abstract List<String> buildParameters();

  public static class PartialJoin {
    private String joinType;
    private From left;
    private From right;

    protected PartialJoin(From left, From right, String joinType) {
      this.joinType = joinType;
      this.left = left;
      this.right = right;
    }

    public JoinFrom on(Column leftColumn, Column rightColumn) {
      return on(Criteria.equals(Projection.column(leftColumn), Projection.column(rightColumn)));
    }

    public JoinFrom on(Criteria criteria) {
      return new JoinFrom(left, right, joinType, criteria);
    }
  }
}
