/*
 * Copyright 2017, Solutech RMS
 * Licensed under the Apache License, Version 2.0, "Solutech Limited".
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.data.net.extras;

import org.json.JSONException;

import java.io.File;
import java.io.InputStream;

import promise.data.file.Dir;

/**
 * Created on 2/14/18 by yoctopus.
 */

public class InputStreamHttpResponse extends HttpResponse<InputStream, File>  {
    private File file;

    public InputStreamHttpResponse(File file) {
        this.file = file;
    }

    @Override
    public HttpResponse<InputStream, File> getResponse(InputStream response) throws JSONException {
        return response(Dir.save(response, file));
    }
}
