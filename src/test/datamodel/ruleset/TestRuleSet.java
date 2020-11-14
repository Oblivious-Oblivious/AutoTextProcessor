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
    private RuleSet create_ruleset(List<LineBlock> lineblocks, List<List<String>> inputSpec, String name) {
        return new RuleSetCreator(lineblocks, inputSpec, name).createRuleSet();
    }

    @Test
    public final void test_H1_BOLD_ALL_CAPS() {
        List<String> block = new ArrayList<String>();
        block.add("TITLE");
        block.add("something else");

        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        lineblocks.add(new LineBlock(block));

        List<String> h1List = new ArrayList<String>();
        h1List.add("H1");
        h1List.add("ALL_CAPS");
        h1List.add("");
        
        List<String> boldList = new ArrayList<String>();
        boldList.add("<B>");
        boldList.add("ALL_CAPS");
        boldList.add("");
        
        List<List<String>> inputSpec = new ArrayList<List<String>>();
        inputSpec.add(h1List);
        inputSpec.add(boldList);

        RuleSet result = create_ruleset(lineblocks, inputSpec, "rule_h1_bold_allcaps");

        assertEquals(FormatEnum.BOLD, result.determineFormatStatus(lineblocks.get(0)));
        assertEquals(StyleEnum.H1, result.determineHeadingStatus(lineblocks.get(0)));
    }

    @Test
    public final void test_H1_STARTS_WITH_para() {
        List<String> block = new ArrayList<String>();
        block.add("paragraph 1");
        block.add("");

        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        lineblocks.add(new LineBlock(block));

        List<String> h1List = new ArrayList<String>();
        h1List.add("H1");
        h1List.add("STARTS_WITH");
        h1List.add("para");
        
        List<List<String>> inputSpec = new ArrayList<List<String>>();
        inputSpec.add(h1List);

        RuleSet result = create_ruleset(lineblocks, inputSpec, "rule_h1_startswith_para");

        assertEquals(FormatEnum.REGULAR, result.determineFormatStatus(lineblocks.get(0)));
        assertEquals(StyleEnum.H1, result.determineHeadingStatus(lineblocks.get(0)));
    }

    @Test
    public final void test_OMIT_POSITIONS_4_5() {
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
        
        List<List<String>> inputSpec = new ArrayList<List<String>>();
        inputSpec.add(omList);

        RuleSet result = create_ruleset(lineblocks, inputSpec, "rule_omit_positions_4,5");

        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(lineblocks.get(0)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(lineblocks.get(1)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(lineblocks.get(2)));
        assertEquals(StyleEnum.NORMAL, result.determineHeadingStatus(lineblocks.get(3)));
        assertEquals(StyleEnum.OMITTED, result.determineHeadingStatus(lineblocks.get(4)));
        assertEquals(StyleEnum.OMITTED, result.determineHeadingStatus(lineblocks.get(5)));
    }
}