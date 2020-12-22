package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleUndefined implements IRule {
    public RuleUndefined() {}

	@Override
	public boolean is_valid(LineBlock paragraph) {
		return false; /* ALWAYS RETURNS FALSE */
	}

	@Override
	public String toString() {
		return "RuleUndefined";
	}
}
