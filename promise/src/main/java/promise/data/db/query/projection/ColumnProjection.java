package promise.data.db.query.projection;

import promise.data.db.Column;
import promise.data.db.Table;
import promise.data.db.Utils;
import promise.model.List;
import promise.model.function.MapFunction;

public class ColumnProjection extends Projection {
  private Table table;
  private Column column;

  public ColumnProjection(Table table, Column column) {
    this.table = table;
    this.column = column;
  }

  @Override
  public String build() {
    String ret = "";

    if (!Utils.isNullOrWhiteSpace(table != null ? table.getName(): "")) ret = table.getName();

    if (!Utils.isNullOrWhiteSpace(column.getName())) ret = ret + column.getName();

    return ret;
  }

  @Override
  public List<String> buildParameters() {
    return Utils.EMPTY_LIST.map(
        new MapFunction<String, Object>() {
          @Override
          public String from(Object o) {
            return String.valueOf(o);
          }
        });
  }
}
