package promise.data.db.query.from;

import promise.data.db.Table;
import promise.data.db.Utils;
import promise.model.List;
import promise.model.function.MapFunction;

public class TableFrom extends AliasableFrom<TableFrom> {
  private Table table;

  public TableFrom(Table table) {
    this.table = table;
  }

  @Override
  public String build() {
    String ret = (!Utils.isNullOrWhiteSpace(table.getName()) ? table.getName() : "");

    if (!Utils.isNullOrWhiteSpace(alias)) ret = ret + " AS " + alias;

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
