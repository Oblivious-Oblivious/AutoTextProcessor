package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleUndefined extends AbstractRule {
    public RuleUndefined() {}

	@Override
	public boolean isValid(LineBlock paragraph) {
		return false; /* ALWAYS RETURNS FALSE */
	}

	@Override
	public String toString() {
		return "RuleUndefined";
	}
}
