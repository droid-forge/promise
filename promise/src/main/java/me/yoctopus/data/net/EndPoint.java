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

package me.yoctopus.data.net;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.yoctopus.data.log.LogUtil;

public class EndPoint {
    private String relativeUrl;
    private String extension;
    private Map<String, String> params;
    private boolean hasParts = false;

    public static class Type {
        protected String s;

        public Type(String s) {
            this.s = s;
        }

        public static Type PHP() {
            return new Type("php");
        }

        public static Type GO() {
            return new Type("go");
        }

        public static Type REST() {
            return new Type("");
        }
    }

    public EndPoint() {

    }

    public EndPoint(String relativeUrl) {
        this.relativeUrl = relativeUrl;
        if (relativeUrl.contains(":")) hasParts = true;
    }

    public EndPoint(String relativeUrl, Type type) {
        this(relativeUrl);
        this.extension = type.s;
    }

    public String relativeUrl() {
        return relativeUrl;
    }

    public EndPoint relativeUrl(String name) {
        this.relativeUrl = name;
        return this;
    }

    public String getExtension() {
        return extension;
    }

    public EndPoint extension(String extension) {
        this.extension = extension;
        return this;
    }

    public Map<String, String> params() {
        return params;
    }

    public EndPoint params(Map<String, String> parts) {
        if (this.params != null) {
            this.params.clear();
        }
        this.params = parts;
        return this;
    }

    @Override
    public String toString() {
        if (extension == null || extension.isEmpty()) {
            if (hasParts) {
                if (params == null || params.isEmpty())
                    throw new IllegalArgumentException("Endpoint has fillable params with no " +
                            "params provided to be used");
                else {
                    String[] stringParts = relativeUrl.split("/");
                    List<Integer> indexes = new ArrayList<>();
                    for (int i = 0; i < stringParts.length; i++) {
                        String part = stringParts[i];
                        if (part.startsWith(":")) indexes.add(i);
                    }
                    if (indexes.size() != params.size())
                        throw new IllegalArgumentException("Parts provided cannot be" +
                                " inserted in to url size differ");
                    else {
                        List<String> keys = new ArrayList<>(), values = new ArrayList<>();
                        for (Map.Entry<String, String> entry : params.entrySet()) {
                            values.add(entry.getValue());
                            keys.add(entry.getKey());
                        }
                        for (int i = 0; i < indexes.size(); i++) {
                            String old = ":".concat(keys.get(i));
                            relativeUrl = relativeUrl().replace(old, values.get(i));
                        }
                    }
                }
            }
            return relativeUrl;
        }
        return relativeUrl.concat(".").concat(extension);
    }

}
