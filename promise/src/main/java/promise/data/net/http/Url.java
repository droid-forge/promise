/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package promise.data.net.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import okhttp3.HttpUrl;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * URL resolved against the {@linkplain promise.data.net.net.FastParserEngine#baseUrl() base URL}.
 * <pre><code>
 * &#64;GET
 * Call&lt;ResponseBody&gt; list(@Url String url);
 * </code></pre>
 * <p>
 * See {@linkplain promise.data.net.net.FastParserEngine.Builder#baseUrl(HttpUrl) base URL} for details of how
 * the value will be resolved against a base URL to create the full endpoint URL.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Url {
}
