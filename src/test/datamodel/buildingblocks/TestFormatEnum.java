package test.datamodel.buildingblocks;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import datamodel.FormatEnum;

/**
 * TestFormatEnum
 */
public class TestFormatEnum {
    @Test
    public final void test_items() {
        assertNotNull(FormatEnum.valueOf("BOLD"));
        assertNotNull(FormatEnum.valueOf("ITALICS"));
        assertNotNull(FormatEnum.valueOf("REGULAR"));
    }
}
