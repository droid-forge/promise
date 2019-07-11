package promise.promisedb.query.from;

import promise.model.List;
import promise.model.function.MapFunction;
import promise.promisedb.Table;
import promise.promisedb.Utils;

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
        String::valueOf);
  }
}
