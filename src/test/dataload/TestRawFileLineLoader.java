package test.dataload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    public final void test_load_lineblock_wrong_file_zero_lines() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        this.rawApi.load("doesnotexist.txt", lineblocks);
        assertEquals(0, lineblocks.size());
    }
}