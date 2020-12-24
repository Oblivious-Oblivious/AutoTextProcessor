package rules;

import datamodel.LineBlock;

/**
 * @class RuleAllCaps
 * @brief Factory component for the ALL_CAPS rule
 */
public class RuleAllCaps implements IRule {
	/**
	 * @message is_valid
	 * @brief Delegate to line_block
	 */
	@Override
	public boolean is_valid(LineBlock paragraph) {
		return paragraph.is_all_caps();
	}

	@Override
	public String toString() {
		return "RuleAllCaps";
	}
}
