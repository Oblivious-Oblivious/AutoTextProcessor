package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith extends AbstractRule {
	private String prefix;

	public RuleStartWith(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public boolean isValid(LineBlock paragraph) {
		return paragraph.startsWith(this.prefix);
	}

	@Override
	public String toString() {
		return "RuleStartWith";
	}
}
