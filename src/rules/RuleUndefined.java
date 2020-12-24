package rules;

import datamodel.LineBlock;

/**
 * @class RuleUndefined
 * @brief Factory component for completing the null object pattern
 */
public class RuleUndefined implements IRule {
	/**
	 * @message is_valid
	 * @brief Delegate to line_block
	 */
	@Override
	public boolean is_valid(LineBlock paragraph) {
		return paragraph.undefined();
	}

	@Override
	public String toString() {
		return "RuleUndefined";
	}
}
