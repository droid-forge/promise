package promise.promisenet;

import java.util.Map;

import promise.promisenet.extras.HttpPayload;
import promise.promisenet.extras.HttpResponse;
import promise.util.DoubleConverter;

public abstract class DataBridge<T, INCOMING> implements DoubleConverter<T, INCOMING, Map<String, Object>> {
  private HttpPayload httpPayload;
  private HttpResponse<?, String> httpResponse;

  public DataBridge(HttpPayload httpPayload, HttpResponse<?, String> httpResponse) {
    this.httpPayload = httpPayload;
    this.httpResponse = httpResponse;
  }

  @Override
  public Map<String, Object> serialize(T t) {
    return null;
  }
}
