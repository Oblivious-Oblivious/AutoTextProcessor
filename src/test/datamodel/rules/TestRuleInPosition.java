package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import datamodel.LineBlock;
import rules.RuleInPosition;

/**
 * @class TestRuleInPosition
 */
public class TestRuleInPosition {

    @Test
    public final void test_toString() {
        RuleInPosition ruleApi = new RuleInPosition(new ArrayList<>(), new ArrayList<>());
        assertEquals("RuleInPosition", ruleApi.toString());
    }

    @Test
    public final void test_is_valid_for_invalid_line_block() {
        RuleInPosition ruleApi = new RuleInPosition(new ArrayList<>(), new ArrayList<>());
        assertFalse(ruleApi.is_valid(new LineBlock(new ArrayList<>())));
    }

    @Test
    public final void test_is_valid_for_correct_line_block() {
        List<LineBlock> line_blocks = new ArrayList<>();

        List<String> block = new ArrayList<>();
        block.add("paragraph 1");
        block.add("sub 1.1");
        block.add("sub 1.2");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("paragraph 2");
        block.add("sub 2.1");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("paragraph 3");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("paragraph 4");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("paragraph 5");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("paragraph 6");
        line_blocks.add(new LineBlock(block));

        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(4);
        positions.add(5);
        RuleInPosition ruleApi = new RuleInPosition(line_blocks, positions);

        assertFalse(ruleApi.is_valid(line_blocks.get(0)));
        assertFalse(ruleApi.is_valid(line_blocks.get(1)));
        assertFalse(ruleApi.is_valid(line_blocks.get(2)));
        assertFalse(ruleApi.is_valid(line_blocks.get(3)));
        assertTrue(ruleApi.is_valid(line_blocks.get(4)));
        assertTrue(ruleApi.is_valid(line_blocks.get(5)));
    }
}