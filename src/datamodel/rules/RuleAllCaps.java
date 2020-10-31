package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleAllCaps extends AbstractRule {
    public RuleAllCaps() {}

	@Override
	public boolean isValid(LineBlock paragraph) {
		return false;
	}

	@Override
	public String toString() {
		return null;
	}
}
