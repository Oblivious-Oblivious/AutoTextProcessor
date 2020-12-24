package rules;

import java.util.List;

import datamodel.LineBlock;

/**
 * @class RuleInPosition
 * @brief Factory component for the POSITIONS rule
 */
public class RuleInPosition implements IRule {
    /**
     * line_blocks -> The list of LineBlock objects to check
     * positions -> The positions to check for
     */
    private final List<LineBlock> line_blocks;
    private final List<Integer> positions;

    /** @Constructor **/
    public RuleInPosition(List<LineBlock> line_blocks, List<Integer> positions) {
        this.line_blocks = line_blocks;
        this.positions = positions;
    }

    /**
	 * @message is_valid
	 * @brief Delegate to line_block
	 */
    @Override
    public boolean is_valid(LineBlock paragraph) {
        return paragraph.is_in_position(this.line_blocks, this.positions);
    }

    @Override
    public String toString() {
        return "RuleInPosition";
    }
}
