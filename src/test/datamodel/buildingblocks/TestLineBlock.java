package test.datamodel.buildingblocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public final void test_getNumWords_is_six() {
        assertEquals(6, this.lbApi.getNumWords());
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
        assertEquals(false, this.lbApi.startsWith("not this"));
        
        assertEquals(true, this.lbApi.startsWith(""));
        assertEquals(true, this.lbApi.startsWith("p"));
        assertEquals(true, this.lbApi.startsWith("pa"));
        assertEquals(true, this.lbApi.startsWith("par"));
        assertEquals(true, this.lbApi.startsWith("para"));
        assertEquals(true, this.lbApi.startsWith("parag"));
        assertEquals(true, this.lbApi.startsWith("paragr"));
        assertEquals(true, this.lbApi.startsWith("paragra"));
        assertEquals(true, this.lbApi.startsWith("paragrap"));
        assertEquals(true, this.lbApi.startsWith("paragraph"));
        assertEquals(true, this.lbApi.startsWith("paragraph "));
        assertEquals(true, this.lbApi.startsWith("paragraph 1"));
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