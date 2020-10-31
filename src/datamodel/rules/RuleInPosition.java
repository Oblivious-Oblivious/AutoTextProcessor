package datamodel.rules;

import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RuleInPosition extends AbstractRule {
    public RuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {}

    @Override
    public boolean isValid(LineBlock paragraph) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
