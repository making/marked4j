# Marked4J

[marked](https://github.com/chjj/marked) for Java

## Usage

    String md = "- foo\n- bar\n- baz";
    Marked marked = new MarkedBuilder().gfm(true).build();
    String expected = "<ul>\n<li>foo</li>\n<li>bar</li>\n<li>baz</li>\n</ul>\n";
    assertThat(marked.marked(md), is(expected));

## Use with Maven

you will get this artifact from Maven Central Repository in future :)

    <dependencies>
      <dependency>
        <groupId>am.ik.marked4j</groupId>
        <artifactId>marked4j</artifactId>
        <version>0.9.0</version>
      </dependency>
    </dependencies>

## Prerequisites

* JDK7+

## License

Licensed under the Apache License, Version 2.0.