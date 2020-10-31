package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith extends AbstractRule {
    public RuleStartWith(String prefix) {}

	@Override
	public boolean isValid(LineBlock paragraph) {
		return false;
	}

	@Override
	public String toString() {
		return null;
	}
}
