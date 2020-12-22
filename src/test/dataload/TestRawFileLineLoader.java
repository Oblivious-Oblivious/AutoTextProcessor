package test.dataload;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import dataload.RawFileLineLoader;
import org.junit.Before;
import org.junit.Test;

import dataload.ILoader;
import datamodel.buildingblocks.LineBlock;

/**
 * @class TestILoader
 * @brief Unit tests for ILoader
 */
public class TestRawFileLineLoader {
    private ILoader rawApi;

    @Before
    public final void createILoaderApi() {
        this.rawApi = new RawFileLineLoader();
    }

    @Test
    public final void test_load_line_block_wrong_file_zero_lines() {
        List<LineBlock> line_blocks = new ArrayList<>();
        this.rawApi.load("doesnotexist.txt", line_blocks);
        assertEquals(0, line_blocks.size());
    }
}