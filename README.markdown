# Marked4J

[![Build Status](https://travis-ci.org/making/marked4j.svg?branch=master)](https://travis-ci.org/making/marked4j)

[marked](https://github.com/chjj/marked) for Java

## Usage

    String md = "- foo\n- bar\n- baz";
    Marked marked = new MarkedBuilder().gfm(true).build();
    String expected = "<ul>\n<li>foo</li>\n<li>bar</li>\n<li>baz</li>\n</ul>\n";
    assertThat(marked.marked(md), is(expected));

## Use with Maven

you will get this artifact from Maven Central Repository

    <dependencies>
      <dependency>
        <groupId>am.ik.marked4j</groupId>
        <artifactId>marked4j</artifactId>
        <version>0.9.1</version>
      </dependency>
    </dependencies>

## Change Log

* 0.9.1 (2014-04-06)
  * updated marked version to 0.3.1
  * supported `langPrefix` option
* 0.9.0 (2014-03-09)
  * first release

## Prerequisites

* JDK 7 (using Rhino) or JDK 8 (using Nashorn) 

## License

Licensed under the Apache License, Version 2.0.
