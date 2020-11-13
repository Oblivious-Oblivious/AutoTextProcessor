package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleAllCaps extends AbstractRule {
	public RuleAllCaps() {}
	
	@Override
	public boolean isValid(LineBlock paragraph) {
		String line = paragraph.getLines().get(0);
		return line.equals(line.toUpperCase());
	}

	@Override
	public String toString() {
		return "RuleAllCaps";
	}
}
