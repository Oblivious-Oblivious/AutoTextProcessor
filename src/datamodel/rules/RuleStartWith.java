package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith implements IRule {
	private final String prefix;

	public RuleStartWith(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean is_valid(LineBlock paragraph) {
		return paragraph.starts_with(this.prefix);
	}

	@Override
	public String toString() {
		return "RuleStartWith";
	}
}
