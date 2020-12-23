package test.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datamodel.LineBlock;
import rules.RuleStartWith;

/**
 * @class TestRuleStartWith
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
    public final void test_is_valid_for_invalid_line_block() {
        List<String> block = new ArrayList<>();
        block.add("sth else");
        LineBlock line_block = new LineBlock(block);
        assertFalse(this.ruleApi.is_valid(line_block));
    }

    @Test
    public final void test_is_valid_for_correct_line_block() {
        List<String> block = new ArrayList<>();
        block.add("paragraph 1");
        block.add("sub 1.1");
        block.add("sub 1.2");
        LineBlock line_block = new LineBlock(block);

        assertTrue(this.ruleApi.is_valid(line_block));
    }
}