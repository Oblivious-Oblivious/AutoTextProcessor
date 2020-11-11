package test.datamodel.buildingblocks;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import datamodel.buildingblocks.StyleEnum;

/**
 * TestStyleEnum
 */
public class TestStyleEnum {
    @Test
    public final void test_items() {
        assertNotNull(StyleEnum.valueOf("OMITTED"));
        assertNotNull(StyleEnum.valueOf("H1"));
        assertNotNull(StyleEnum.valueOf("H2"));
        assertNotNull(StyleEnum.valueOf("NORMAL"));
    }
}
