/*
 * Copyright (C) 2014-2019 Toshiaki Maki <makingx@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package am.ik.marked4j;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Marked implements Closeable {

    private final boolean autoToc;

    private final V8 v8;

    private final V8Object marked;

    public Marked(boolean gfm, boolean tables, boolean breaks, boolean pedantic,
                  boolean sanitize, boolean smartLists, boolean smartypants, String langPrefix,
                  boolean enableHeadingIdUriEncoding, boolean autoToc) {
        this(autoToc);
        try (ReleasableWrapper<V8Array> parameters = new ReleasableWrapper<>(new V8Array(this.v8));
             ReleasableWrapper<V8Object> options = new ReleasableWrapper<>(new V8Object(this.v8)
                 .add("gfm", gfm)
                 .add("tables", tables)
                 .add("breaks", breaks)
                 .add("pedantic", pedantic)
                 .add("sanitize", sanitize)
                 .add("smartLists", smartLists)
                 .add("smartypants", smartypants)
                 .add("langPrefix", langPrefix))
        ) {
            parameters.unwrap().push(options.unwrap());
            this.marked.executeFunction("setOptions", parameters.unwrap());
        }
        if (enableHeadingIdUriEncoding) {
            this.marked.executeFunction("enableHeadingIdUriEncoding", null);
        }
    }

    public Marked(boolean autoToc) {
        this.autoToc = autoToc;
        this.v8 = V8.createV8Runtime();
        try (InputStream marked = Marked.class.getClassLoader()
            .getResourceAsStream("META-INF/resources/assets/marked/lib/marked.js");
             InputStream lib = Marked.class.getClassLoader()
                 .getResourceAsStream("lib.js")) {
            String js = copyToString(marked, StandardCharsets.UTF_8);
            String script = copyToString(lib, StandardCharsets.UTF_8);
            this.marked = this.v8.executeObjectScript(js + ";" + script);
        } catch (IOException e) {
            throw new IllegalStateException("marked.js is not found.", e);
        }
    }

    public Marked() {
        this(false);
    }

    @Override
    public void close() {
        if (this.marked != null) {
            this.marked.release();
        }
        if (this.v8 != null) {
            this.v8.release();
        }
    }

    private String invoke(String function, String markdownText) {
        try (ReleasableWrapper<V8Array> parameters = new ReleasableWrapper<>(new V8Array(this.v8));
        ) {
            parameters.unwrap().push(markdownText);
            return this.marked.executeStringFunction(function, parameters.unwrap());
        }
    }

    public String marked(String markdownText) {
        if (this.autoToc && markdownText.contains("<!-- toc -->")) {
            markdownText = this.insertToc(markdownText);
        }
        return this.invoke("marked", markdownText);
    }

    public String toc(String markdownText) {
        return this.invoke("toc", markdownText);
    }

    public String insertToc(String markdownText) {
        return this.invoke("insertToc", markdownText);
    }

    private static String copyToString(InputStream in, Charset charset)
        throws IOException {
        StringBuilder out = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(in, charset);) {
            char[] buffer = new char[4096];
            int bytesRead = -1;
            while ((bytesRead = reader.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }
            return out.toString();
        }
    }
}
