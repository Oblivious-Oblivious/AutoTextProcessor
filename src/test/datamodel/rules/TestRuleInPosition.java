package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.RuleInPosition;

/**
 * @class: TestRuleInPosition
 */
public class TestRuleInPosition {
    
    @Test
    public final void test_toString() {
        RuleInPosition ruleApi = new RuleInPosition(new ArrayList<LineBlock>(), new ArrayList<Integer>());
        assertEquals("RuleInPosition", ruleApi.toString());
    }

    @Test
    public final void test_isValid_for_invalid_lineblock() {
        RuleInPosition ruleApi = new RuleInPosition(new ArrayList<LineBlock>(), new ArrayList<Integer>());
        assertFalse(ruleApi.isValid(new LineBlock(new ArrayList<String>())));
    }

    @Test
    public final void test_isValid_for_correct_lineblock() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();

        List<String> block = new ArrayList<String>();
        block.add("paragraph 1");
        block.add("sub 1.1");
        block.add("sub 1.2");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("paragraph 2");
        block.add("sub 2.1");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("paragraph 3");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("paragraph 4");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("paragraph 5");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("paragraph 6");
        lineblocks.add(new LineBlock(block));

        List<String> omList = new ArrayList<String>();
        omList.add("OMIT");
        omList.add("POSITIONS");
        omList.add("4,5");

        ArrayList<Integer> positions = new ArrayList<Integer>();
        positions.add(4);
        positions.add(5);
        RuleInPosition ruleApi = new RuleInPosition(lineblocks, positions);

        assertFalse(ruleApi.isValid(lineblocks.get(0)));
        assertFalse(ruleApi.isValid(lineblocks.get(1)));
        assertFalse(ruleApi.isValid(lineblocks.get(2)));
        assertTrue(ruleApi.isValid(lineblocks.get(3)));
        assertTrue(ruleApi.isValid(lineblocks.get(4)));
        assertFalse(ruleApi.isValid(lineblocks.get(5)));
    }
}