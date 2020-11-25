package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.RuleAllCaps;

/**
 * @class: TestRuleAllCaps
 */
public class TestRuleAllCaps {
    RuleAllCaps ruleApi;

    @Before
    public final void setupRuleApi() {
        this.ruleApi = new RuleAllCaps();
    }
    
    @Test
    public final void test_toString() {
        assertEquals("RuleAllCaps", this.ruleApi.toString());
    }

    @Test
    public final void test_isValid_for_invalid_lineblock() {
        List<String> block = new ArrayList<String>();
        block.add("more stuff");
        block.add("CAPITAL STUFF UNDERNEATH");
        LineBlock lineblock = new LineBlock(block);

        assertFalse(this.ruleApi.isValid(lineblock));
    }

    @Test
    public final void test_isValid_for_correct_lineblock() {
        List<String> block = new ArrayList<String>();
        block.add("CAPITAL STUFF");
        block.add("AND MORE");
        LineBlock lineblock = new LineBlock(block);
        
        assertTrue(this.ruleApi.isValid(lineblock));
    }
}