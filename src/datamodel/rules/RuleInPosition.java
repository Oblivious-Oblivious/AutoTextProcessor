package datamodel.rules;

import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RuleInPosition implements IRule {
    private final List<LineBlock> p_line_blocks;
    private final List<Integer> p_positions;

    public RuleInPosition(List<LineBlock> p_line_blocks, List<Integer> p_positions) {
        this.p_line_blocks = p_line_blocks;
        this.p_positions = p_positions;
    }

    @Override
    public boolean is_valid(LineBlock paragraph) {
        return p_positions.contains(this.p_line_blocks.indexOf(paragraph));
    }

    @Override
    public String toString() {
        return "RuleInPosition";
    }
}
