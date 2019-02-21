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

package me.yoctopus.data.file;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yoctopus.data.log.LogUtil;


public class FManager {
    private String TAG = LogUtil.makeTag(FManager.class);
    private FileOperations fileOperations;
    private File source,
            destination;
    private Dir dir;
    private Context context;

    public FManager(Context context) {
        this.context = context;
    }
    public List<File> importFiles(Dir dir) throws NoFilesFound {
        ArrayList<File> files =
                new ArrayList<>();
        File folder = new File(dir.getName());
        if (folder.isDirectory()) {
            File[] lists = folder.listFiles();
            if (lists == null || lists.length == 0) {
                throw new NoFilesFound();
            }
            for (File file : lists) {
                if (file.isDirectory()) {
                    files.addAll(Arrays.asList(file.listFiles()));
                }
                if (file.isFile()) {
                    files.add(file);
                }
            }
        } else {
            throw new NoFilesFound();
        }
        return files;
    }
    public boolean delete(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }
    public boolean delete(File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }
    public boolean deleteAll(Dir dir) {
        File file = new File(dir.getName());
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) file1.delete();
        }
        return true;
    }
    public boolean copy(File source, File destination) {
        this.source = source;
        fileOperations = new FileOperations(source, destination);
        this.destination = fileOperations.getDestination();
        return fileOperations.copy();
    }
    public boolean rename(File file, String name) {
        FUri fUri = new FUri(dir, name);
        File file1 = new File(fUri.getUri().toString());
        fileOperations = new FileOperations(file, file1);
        if (fileOperations.copy()) {
            file.delete();
            return true;
        }
        else {
            return false;
        }
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public File getDestination() {
        return destination;
    }



    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private static class FileOperations {
        private String TAG = LogUtil.makeTag(FileOperations.class);
        private File source,
                destination;
        private Dir dir;

        FileOperations(File source,
                       File destination) {
            this.source = source;
            this.destination = destination;
        }



        protected boolean copy() {
            if (destination.exists()) {
                try {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(destination);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) > 0) {
                        out.write(buffer, 0, read);
                    }
                    out.flush();
                    in.close();
                    out.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        protected Dir getDir() {
            return dir;
        }

        protected void setDir(Dir dir) {
            this.dir = dir;
        }

        protected File getSource() {
            return source;
        }

        protected void setSource(File source) {
            this.source = source;
        }

        protected File getDestination() {
            return destination;
        }


    }
}
