package rules;

import datamodel.LineBlock;

/**
 * @class RuleStartWith
 * @brief Factory component for the STARTS_WITH rule
 */
public class RuleStartWith implements IRule {
	/**
	 * prefix -> The prefix we check for
	 */
	private final String prefix;

	/** @Constructor **/
	public RuleStartWith(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @message is_valid
	 * @brief Delegate to line_block
	 */
	@Override
	public boolean is_valid(LineBlock paragraph) {
		return paragraph.starts_with(this.prefix);
	}

	@Override
	public String toString() {
		return "RuleStartWith";
	}
}
