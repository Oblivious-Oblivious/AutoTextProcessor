package test.dataload;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.LineBlock;

/**
 * @class TestRawFileLineLoader
 * @desc: Unit tests for RawFileLineLoader
 */
public class TestRawFileLineLoader {
    private RawFileLineLoader rawApi;

    @Before
    public final void createRawFileLineLoaderApi() {
        this.rawApi = new RawFileLineLoader();
    }

    @Test
    public final void test_load_lineblock_state_unchanged() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        this.rawApi.load("randomfile", lineblocks);
        assertNotNull(lineblocks);
    }

    @Test
    public final void test_load_lineblocks_size_negative() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        // lineblocks.add(new LineBlock());
        // lineblocks.add(new LineBlock());
        // lineblocks.add(new LineBlock());
        this.rawApi.load("nonexistentfile", lineblocks);
    }

    // @Test
    // public final void test_load_lineblocks_size_zero() {}

    // @Test
    // public final void test_load_lineblocks_positive() {}
}