package test.dataload;

import static org.junit.Assert.assertNotEquals;
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
    public final void test_load_lineblock_is_an_object() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        this.rawApi.load("randomfile.txt", lineblocks);
        assertNotNull(lineblocks);
    }

    @Test
    public final void test_load_lineblock_change_state() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        this.rawApi.load("randomfile.txt", lineblocks);
        assertNotEquals(0, lineblocks.size());
    }
}