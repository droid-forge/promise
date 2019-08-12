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

package promise.file;

/**
 * Created by octopus on 10/4/16.
 */
public enum Ext {
    CSV(".csv"),
    XLS(".xls"),
    PDF(".pdf"),
    JPEG(".jpg"),
    PNG(".png");
    public static final String CS = ".csv";
    public static final String XL = ".xls";
    public static final String PD = ".pdf";
    public static final String JP = ".jpg";
    public static final String PN = ".png";

    private String extension;

    public String getExtension() {
        return extension;
    }
    private Ext(String extension) {
        this.extension = extension;
    }
}
