package test.datamodel.ruleset;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;
import datamodel.ruleset.RuleSet;
import datamodel.ruleset.RuleSetCreator;

/**
 * TestRuleSet
 */
public class TestRuleSet {
    private RuleSet create_rule_set(List<LineBlock> line_blocks, List<List<String>> inputSpec, String name) {
        return new RuleSetCreator(line_blocks, inputSpec, name).create_rule_set();
    }

    @Test
    public final void test_H1_BOLD_ALL_CAPS() {
        List<String> block = new ArrayList<>();
        block.add("TITLE");
        block.add("SOMETHING ELSE");

        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(block));

        List<String> h1List = new ArrayList<>();
        h1List.add("H1");
        h1List.add("ALL_CAPS");
        h1List.add("");
        
        List<String> boldList = new ArrayList<>();
        boldList.add("<B>");
        boldList.add("ALL_CAPS");
        boldList.add("");
        
        List<List<String>> inputSpec = new ArrayList<>();
        inputSpec.add(h1List);
        inputSpec.add(boldList);

        RuleSet result = create_rule_set(line_blocks, inputSpec, "rule_h1_bold_all_caps");

        assertEquals(FormatEnum.BOLD, result.determineFormatStatus(line_blocks.get(0)));
        assertEquals(StyleEnum.H1, result.determineHeadingStatus(line_blocks.get(0)));
    }

    @Test
    public final void test_H1_STARTS_WITH_para() {
        List<String> block = new ArrayList<>();
        block.add("paragraph 1");
        block.add("");

        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(block));

        List<String> h1List = new ArrayList<>();
        h1List.add("H1");
        h1List.add("STARTS_WITH");
        h1List.add("para");
        
        List<List<String>> inputSpec = new ArrayList<>();
        inputSpec.add(h1List);

        RuleSet result = create_rule_set(line_blocks, inputSpec, "rule_h1_starts_with_para");

        assertEquals(FormatEnum.REGULAR, result.determineFormatStatus(line_blocks.get(0)));
        assertEquals(StyleEnum.H1, result.determineHeadingStatus(line_blocks.get(0)));
    }

    @Test
    public final void test_OMIT_POSITIONS_4_5() {
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

        List<String> omList = new ArrayList<>();
        omList.add("OMIT");
        omList.add("POSITIONS");
        omList.add("4,5");
        
        List<List<String>> inputSpec = new ArrayList<>();
        inputSpec.add(omList);

        RuleSet result = create_rule_set(line_blocks, inputSpec, "rule_omit_positions_4,5");

        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(line_blocks.get(0)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(line_blocks.get(1)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(line_blocks.get(2)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(line_blocks.get(3)));
        assertEquals(StyleEnum.OMITTED, result.determineHeadingStatus(line_blocks.get(4)));
        assertEquals(StyleEnum.OMITTED, result.determineHeadingStatus(line_blocks.get(5)));
    }
}