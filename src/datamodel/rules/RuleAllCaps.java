package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleAllCaps extends AbstractRule {
	public RuleAllCaps() {}
	
	@Override
	public boolean isValid(LineBlock paragraph) {
		return paragraph.isCapital();
	}

	@Override
	public String toString() {
		return "RuleAllCaps";
	}
}
