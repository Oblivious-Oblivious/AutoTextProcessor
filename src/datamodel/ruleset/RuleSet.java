package datamodel.ruleset;

import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.StyleEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.rules.IRule;

/**
 * Class to represent a set of rules
 * 
 * The main idea of the class is that it has a field for every rule of interest.
 * So, it has rules for headings and rules for style.
 * Headings: the class has a rule for what to omit, a rule for how to handle heading 1, a rule for handling heading 2
 * Style: the class has a rule for bold and a rule for italics.
 * 
 * All these rules dictate when sth is t obe treated as h1, or bold, etc.
 * If an item does not apply by any of the "heading" rules, it is by default NORMAL
 * If an item does not apply by any of the "style" rules, it is by default REGULAR
 * 
 * The class comes with two methods, that, given a LineBlock, decide which classification applies (a) for its heading and (b) for its style
 * 
 * @author pvassil
 * @version 0.1
 */
public class RuleSet {
	private final String name;
	private final IRule omitRule;
	private final IRule h1Rule;
	private final IRule h2Rule;
	private final IRule boldRule;
	private final IRule italicsRule;
	
	public RuleSet(String name, IRule omitRule, IRule h1Rule, IRule h2Rule, IRule boldRule, IRule italicsRule) {
		this.name = name;
		this.omitRule = omitRule;
		this.h1Rule = h1Rule;
		this.h2Rule = h2Rule;
		this.boldRule = boldRule;
		this.italicsRule = italicsRule;
	}

	public StyleEnum determineHeadingStatus(LineBlock lineblock) {
		if(this.omitRule.is_valid(lineblock))
			return StyleEnum.OMITTED;
		if(this.h1Rule.is_valid(lineblock))
			return StyleEnum.H1;
		if(this.h2Rule.is_valid(lineblock))
			return StyleEnum.H2;
		return StyleEnum.NORMAL;
	}

	public FormatEnum determineFormatStatus(LineBlock lineblock) {
		if(this.boldRule.is_valid(lineblock))
			return FormatEnum.BOLD;
		if(this.italicsRule.is_valid(lineblock))
			return FormatEnum.ITALICS;
		return FormatEnum.REGULAR;
	}
	
	public String toString() {
		return this.name
			+ "\nOMIT: " + this.omitRule.toString()
			+ "\nH1: " + this.h1Rule.toString()
			+ "\nH2: " + this.h2Rule.toString()
			+ "\nBOLD: " + this.boldRule.toString()
			+ "\nITALICS: " + this.italicsRule.toString()
			+ "\n";
	}
}