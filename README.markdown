# Marked4J

[![Build Status](https://travis-ci.org/making/marked4j.svg?branch=master)](https://travis-ci.org/making/marked4j)
[![Apache 2.0](https://img.shields.io/github/license/making/marked4j.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/am.ik.marked4j/marked4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/am.ik.marked4j/marked4j)
[![Javadocs](https://www.javadoc.io/badge/am.ik.marked4j/marked4j.svg)](https://www.javadoc.io/doc/am.ik.marked4j/marked4j)

[marked](https://github.com/chjj/marked) for Java

## Usage

``` java
String md = "- foo\n- bar\n- baz";
Marked marked = new MarkedBuilder().gfm(true).build();
String expected = "<ul>\n<li>foo</li>\n<li>bar</li>\n<li>baz</li>\n</ul>\n";
assertThat(marked.marked(md), is(expected));
```

## Use with Maven

you will get this artifact from Maven Central Repository

``` xml
<dependencies>
  <dependency>
    <groupId>am.ik.marked4j</groupId>
    <artifactId>marked4j</artifactId>
    <version>0.11.0</version>
  </dependency>
</dependencies>
```

## Change Log

* 0.11.0 (2019-06-06)
  * Use j2v8 instead of Nashorn
* 0.10.3 (2018-03-08)
  * updated marked version to 0.3.17.
* 0.10.2 (2017-06-04)
  * fix nested list in TOC
* 0.10.1 (2017-06-04)
  * supported `autoToc`
* 0.10.0 (2017-06-04)
  * supported TOC 👏 ([gh-2](https://github.com/making/marked4j/issues/2))
  * updated marked version to 0.3.6.
* 0.9.2 (2016-01-13)
  * updated marked version to 0.3.5. ([gh-11](https://github.com/making/marked4j/issues/11))
* 0.9.1 (2014-04-06)
  * updated marked version to 0.3.1
  * supported `langPrefix` option
* 0.9.0 (2014-03-09)
  * first release

## Prerequisites

* JDK 8+

## License

Licensed under the Apache License, Version 2.0.
