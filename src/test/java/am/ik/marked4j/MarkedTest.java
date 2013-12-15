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
        String md = "- foo\n" +
                "- bar\n" +
                "- baz";
        String expected = "<ul>\n" +
                "<li>foo</li>\n" +
                "<li>bar</li>\n" +
                "<li>baz</li>\n" +
                "</ul>\n";
        Marked marked = new MarkedBuilder().gfm(true).build();
        assertThat(marked.marked(md), is(expected));
    }

    @Test
    public void testTable() {
        String md = "Colons can be used to align columns.\n" +
                "\n" +
                "| Tables        | Are           | Cool  |\n" +
                "| ------------- |:-------------:| -----:|\n" +
                "| col 3 is      | right-aligned | $1600 |\n" +
                "| col 2 is      | centered      |   $12 |\n" +
                "| zebra stripes | are neat      |    $1 |\n" +
                "\n" +
                "The outer pipes (|) are optional, and you don't need to make the raw Markdown line up prettily. You can also use inline Markdown.\n" +
                "\n" +
                "Markdown | Less | Pretty\n" +
                "--- | --- | ---\n" +
                "*Still* | `renders` | **nicely**\n" +
                "1 | 2 | 3";
        String expected = "<p>Colons can be used to align columns.</p>\n" +
                "<table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>Tables</th>\n" +
                "<th style=\"text-align:center\">Are</th>\n" +
                "<th style=\"text-align:right\">Cool</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>col 3 is</td>\n" +
                "<td style=\"text-align:center\">right-aligned</td>\n" +
                "<td style=\"text-align:right\">$1600</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>col 2 is</td>\n" +
                "<td style=\"text-align:center\">centered</td>\n" +
                "<td style=\"text-align:right\">$12</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>zebra stripes</td>\n" +
                "<td style=\"text-align:center\">are neat</td>\n" +
                "<td style=\"text-align:right\">$1</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<p>The outer pipes (|) are optional, and you don&#39;t need to make the raw Markdown line up prettily. You can also use inline Markdown.</p>\n" +
                "<table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>Markdown</th>\n" +
                "<th>Less</th>\n" +
                "<th>Pretty</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td><em>Still</em></td>\n" +
                "<td><code>renders</code></td>\n" +
                "<td><strong>nicely</strong></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>1</td>\n" +
                "<td>2</td>\n" +
                "<td>3</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n";

        Marked marked = new MarkedBuilder().gfm(true).tables(true).build();
        assertThat(marked.marked(md), is(expected));
    }

}
