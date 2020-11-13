package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.RuleStartWith;

/**
 * @class: TestRuleStartWith
 */
public class TestRuleStartWith {
    RuleStartWith ruleApi;

    @Before
    public final void setupRuleApi() {
        this.ruleApi = new RuleStartWith("para");
    }
    
    @Test
    public final void test_toString() {
        assertEquals("RuleStartWith", this.ruleApi.toString());
    }

    @Test
    public final void test_isValid_for_invalid_lineblock() {
        List<String> block = new ArrayList<String>();
        block.add("sth else");
        LineBlock lineblock = new LineBlock(block);
        assertFalse(this.ruleApi.isValid(lineblock));
    }

    @Test
    public final void test_isValid_for_correct_lineblock() {
        List<String> block = new ArrayList<String>();
        block.add("paragraph 1");
        block.add("sub 1.1");
        block.add("sub 1.2");
        LineBlock lineblock = new LineBlock(block);

        assertTrue(this.ruleApi.isValid(lineblock));
    }
}