package test.datamodel.buildingblocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.LineBlock;

/**
 * TestLineBlock
 */
public class TestLineBlock {
    LineBlock lbApi;
    List<String> block;

    @Before
    public final void setup_lb_api() {
        this.block = new ArrayList<>();
        this.block.add("paragraph 1");
        this.block.add("sub 1.1");
        this.block.add("sub 1.2");

        this.lbApi = new LineBlock(this.block);
    }

    @Test
    public final void test_get_stats_as_string_not_null() {
        assertNotNull(this.lbApi.get_stats_as_string());
    }

    @Test
    public final void test_get_stats_as_string_on_lb() {
        assertEquals("Lines: 3, Words: 6", this.lbApi.get_stats_as_string());
    }

    @Test
    public final void test_get_words_is_six() {
        assertEquals(6, this.lbApi.get_words());
    }

    @Test
    public final void test_get_lines_same_size() {
        assertEquals(this.block.size(), this.lbApi.get_lines().size());
    }

    @Test
    public final void test_get_lines_same_content() {
        assertEquals(this.block.get(0), this.lbApi.get_lines().get(0));
        assertEquals(this.block.get(1), this.lbApi.get_lines().get(1));
        assertEquals(this.block.get(2), this.lbApi.get_lines().get(2));
    }

    @Test
    public final void test_starts_with_correct() {
        assertFalse(this.lbApi.starts_with("not this"));
        
        assertTrue(this.lbApi.starts_with(""));
        assertTrue(this.lbApi.starts_with("p"));
        assertTrue(this.lbApi.starts_with("pa"));
        assertTrue(this.lbApi.starts_with("par"));
        assertTrue(this.lbApi.starts_with("para"));
        assertTrue(this.lbApi.starts_with("parag"));
        assertTrue(this.lbApi.starts_with("paragr"));
        assertTrue(this.lbApi.starts_with("paragra"));
        assertTrue(this.lbApi.starts_with("paragrap"));
        assertTrue(this.lbApi.starts_with("paragraph"));
        assertTrue(this.lbApi.starts_with("paragraph "));
        assertTrue(this.lbApi.starts_with("paragraph 1"));
    }

    @Test
    public final void test_replace_first_not_null() {
        assertNotNull(this.lbApi.replace_first("stuff", "new stuff"));
    }

    @Test
    public final void test_replace_first_correct() {
        this.lbApi.replace_first("paragraph", "block");
        assertEquals("block 1", this.lbApi.get_lines().get(0));
    }
}