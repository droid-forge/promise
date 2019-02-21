package me.dev4vin.data.db.query.criteria;

import me.dev4vin.data.db.query.projection.AliasedProjection;
import me.dev4vin.data.db.query.projection.Projection;
import me.dev4vin.model.List;

public class BetweenCriteria extends Criteria {
  private Projection projection;
  private Object valueStart;
  private Object valueEnd;

  public BetweenCriteria(Projection projection, Object valueStart, Object valueEnd) {
    this.projection = projection;
    this.valueStart = valueStart;
    this.valueEnd = valueEnd;

    if (this.projection instanceof AliasedProjection)
      this.projection = ((AliasedProjection) this.projection).removeAlias();
  }

  @Override
  public String build() {
    StringBuilder sb = new StringBuilder();

    if (projection != null) sb.append(projection.build());

    sb.append(" BETWEEN ");
    sb.append((valueStart != null ? "?" : "NULL"));
    sb.append(" AND ");
    sb.append((valueEnd != null ? "?" : "NULL"));

    return sb.toString();
  }

  @Override
  public List<String> buildParameters() {
    List<String> ret = new List<>();

    if (projection != null) ret.addAll(projection.buildParameters());

    if (valueStart != null) ret.add(String.valueOf(valueStart));

    if (valueEnd != null) ret.add(String.valueOf(valueEnd));

    return ret;
  }
}
