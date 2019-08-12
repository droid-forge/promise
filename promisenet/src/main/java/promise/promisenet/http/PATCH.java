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
package promise.promisenet.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import okhttp3.HttpUrl;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Make a PATCH request. */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface PATCH {
  /**
   * A relative or absolute path, or full URL of the endpoint. This value is optional if the first
   * parameter of the method is annotated with {@link Url @Url}.
   * <p>
   * See {@linkplain retrofit2.Retrofit.Builder#baseUrl(HttpUrl) base URL} for details of how
   * this is resolved against a base URL to create the full endpoint URL.
   */
  String value() default "";
}