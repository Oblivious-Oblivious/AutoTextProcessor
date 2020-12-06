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
    public final void setuplbApi() {
        this.block = new ArrayList<String>();
        this.block.add("paragraph 1");
        this.block.add("sub 1.1");
        this.block.add("sub 1.2");

        this.lbApi = new LineBlock(this.block);
    }

    @Test
    public final void test_getStatsAsString_not_null() {
        assertNotNull(this.lbApi.getStatsAsString());
    }

    @Test
    public final void test_getStatsAsString_on_lb() {
        assertEquals("Lines: 3, Words: 6", this.lbApi.getStatsAsString());
    }

    @Test
    public final void test_getWords_is_six() {
        assertEquals(6, this.lbApi.getWords());
    }

    @Test
    public final void test_getLines_same_size() {
        assertEquals(this.block.size(), this.lbApi.getLines().size());
    }

    @Test
    public final void test_getLines_same_content() {
        assertEquals(this.block.get(0), this.lbApi.getLines().get(0));
        assertEquals(this.block.get(1), this.lbApi.getLines().get(1));
        assertEquals(this.block.get(2), this.lbApi.getLines().get(2));
    }

    @Test
    public final void test_startsWith_correct() {
        assertFalse(this.lbApi.startsWith("not this"));
        
        assertTrue(this.lbApi.startsWith(""));
        assertTrue(this.lbApi.startsWith("p"));
        assertTrue(this.lbApi.startsWith("pa"));
        assertTrue(this.lbApi.startsWith("par"));
        assertTrue(this.lbApi.startsWith("para"));
        assertTrue(this.lbApi.startsWith("parag"));
        assertTrue(this.lbApi.startsWith("paragr"));
        assertTrue(this.lbApi.startsWith("paragra"));
        assertTrue(this.lbApi.startsWith("paragrap"));
        assertTrue(this.lbApi.startsWith("paragraph"));
        assertTrue(this.lbApi.startsWith("paragraph "));
        assertTrue(this.lbApi.startsWith("paragraph 1"));
    }

    @Test
    public final void test_replaceFirst_not_null() {
        assertNotNull(this.lbApi.replaceFirst("stuff", "new stuff"));
    }

    @Test
    public final void test_replaceFirst_correct() {
        this.lbApi.replaceFirst("paragraph", "block");
        assertEquals("block 1", this.lbApi.getLines().get(0));
    }
}