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

package me.dev4vin.data.file;


import android.net.Uri;

import java.io.File;
import java.io.IOException;

public class FUri {
    private Dir dir;
    private String fileName;
    private Ext ext;
    private String startString;

    public FUri(Dir dir,
                String fileName) {
        this.setDir(dir);
        this.setFileName(fileName);
    }


    public FUri(Dir dir) {
        this(dir,
                null);
    }



    public void setStartString(String startString) {
        this.startString = startString;
    }

    public Ext getExt() {
        return ext;
    }

    public void setExt(Ext ext) {
        this.ext = ext;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getUri() {
        String name = "";
        if (startString != null &&
                !startString.isEmpty()) {
            name = startString;
        }
        File file = new File(name +
                getDir().getName() +
                File.separator +
                getFileName() +
                getExt().getExtension());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.parse(
                file.getAbsolutePath());
    }

    @Override
    public String toString() {
        return getUri().toString();
    }
}
