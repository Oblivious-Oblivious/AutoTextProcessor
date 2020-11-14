package datamodel.rules;

import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RuleInPosition extends AbstractRule {
    private List<LineBlock> pLineblocks;
    private List<Integer> pPositions;

    public RuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {
        this.pLineblocks = pLineblocks;
        this.pPositions = pPositions;
    }

    @Override
    public boolean isValid(LineBlock paragraph) {
        for(int p : this.pPositions)
            if(pLineblocks.get(p).equals(paragraph))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "RuleInPosition";
    }
}
