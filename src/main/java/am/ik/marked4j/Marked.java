/*
 * Copyright (C) 2014-2017 Toshiaki Maki <makingx@gmail.com>
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.script.*;

public class Marked {
	private final Invocable invocableEngine;
	private final Object marked4j;
	private final boolean autoToc;

	public Marked(boolean gfm, boolean tables, boolean breaks, boolean pedantic,
			boolean sanitize, boolean smartLists, boolean smartypants, String langPrefix,
			boolean enableHeadingIdUriEncoding, boolean autoToc) {
		this(autoToc);
		ScriptEngine engine = (ScriptEngine) invocableEngine;
		try {
			String options = String.format(
					"{\"gfm\": %s,\"tables\": %s,\"breaks\": %s,\"pedantic\": %s,\"sanitize\": %s,\"smartLists\": %s,\"smartypants\": %s,\"langPrefix\": \"%s\"}",
					gfm, tables, breaks, pedantic, sanitize, smartLists, smartypants,
					langPrefix);
			invocableEngine.invokeMethod(marked4j, "setOptions", options);
			if (enableHeadingIdUriEncoding) {
				invocableEngine.invokeMethod(marked4j, "enableHeadingIdUriEncoding");
			}
		}
		catch (ScriptException | NoSuchMethodException e) {
			throw new IllegalStateException("invalid script!", e);
		}
	}

	public Marked(boolean autoToc) {
		this.autoToc = autoToc;
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		this.invocableEngine = (Invocable) engine;
		try (InputStream marked = Marked.class.getClassLoader()
				.getResourceAsStream("META-INF/resources/assets/marked/lib/marked.js");
				InputStream lib = Marked.class.getClassLoader()
						.getResourceAsStream("lib.js")) {
			String js = copyToString(marked, StandardCharsets.UTF_8);
			Bindings bindings = new SimpleBindings(); // todo
			String script = copyToString(lib, StandardCharsets.UTF_8);
			this.marked4j = engine.eval(js + ";" + script, bindings);
		}
		catch (IOException e) {
			throw new IllegalStateException("marked.js is not found.", e);
		}
		catch (ScriptException e) {
			throw new IllegalStateException("invalid script!", e);
		}
	}

	public Marked() {
		this(false);
	}

	private String invoke(String function, String markdownText) {
		try {
			Object result = this.invocableEngine.invokeMethod(marked4j, function,
					markdownText);
			return result == null ? null : result.toString();
		}
		catch (NoSuchMethodException | ScriptException e) {
			throw new IllegalArgumentException("Cannot parse the given markdown text!",
					e);
		}
	}

	public String marked(String markdownText) {
		if (autoToc && markdownText.contains("<!-- toc -->")) {
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
