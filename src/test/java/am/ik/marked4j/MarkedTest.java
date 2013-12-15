package am.ik.marked4j;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MarkedTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String md = "- foo\n- bar\n- baz";
		Marked marked = new MarkedBuilder().gfm(true).build();
		String expected = "<ul>\n<li>foo</li>\n<li>bar</li>\n<li>baz</li>\n</ul>\n";
		assertThat(marked.marked(md), is(expected));
	}

}
