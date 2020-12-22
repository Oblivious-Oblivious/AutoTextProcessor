package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.LineBlock;
import rules.RuleAllCaps;

/**
 * @class TestRuleAllCaps
 */
public class TestRuleAllCaps {
    RuleAllCaps ruleApi;

    @Before
    public final void setup_rule_api() {
        this.ruleApi = new RuleAllCaps();
    }
    
    @Test
    public final void test_toString() {
        assertEquals("RuleAllCaps", this.ruleApi.toString());
    }

    @Test
    public final void test_is_valid_for_invalid_line_block() {
        List<String> block = new ArrayList<>();
        block.add("CAPITAL STUFF");
        block.add("more stuff underneath");
        LineBlock lineblock = new LineBlock(block);

        assertFalse(this.ruleApi.is_valid(lineblock));
    }

    @Test
    public final void test_is_valid_for_correct_line_block() {
        List<String> block = new ArrayList<>();
        block.add("CAPITAL STUFF");
        block.add("AND MORE");
        LineBlock lineblock = new LineBlock(block);
        
        assertTrue(this.ruleApi.is_valid(lineblock));
    }
}