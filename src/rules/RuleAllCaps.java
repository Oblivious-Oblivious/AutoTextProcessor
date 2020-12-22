package rules;

import datamodel.LineBlock;

public class RuleAllCaps implements IRule {
	public RuleAllCaps() {}
	
	@Override
	public boolean is_valid(LineBlock paragraph) {
		return paragraph.is_capital();
	}

	@Override
	public String toString() {
		return "RuleAllCaps";
	}
}
