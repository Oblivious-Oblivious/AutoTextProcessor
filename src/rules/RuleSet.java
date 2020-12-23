package rules;

import datamodel.FormatEnum;
import datamodel.StyleEnum;
import datamodel.LineBlock;

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
	private final IRule omit_rule;
	private final IRule h1_rule;
	private final IRule h2_rule;
	private final IRule bold_rule;
	private final IRule italics_rule;
	
	public RuleSet(String name, IRule omit_rule, IRule h1_rule, IRule h2_rule, IRule bold_rule, IRule italics_rule) {
		this.name = name;
		this.omit_rule = omit_rule;
		this.h1_rule = h1_rule;
		this.h2_rule = h2_rule;
		this.bold_rule = bold_rule;
		this.italics_rule = italics_rule;
	}

	public StyleEnum determine_heading_status(LineBlock lineblock) {
		if(this.omit_rule.is_valid(lineblock))
			return StyleEnum.OMITTED;
		if(this.h1_rule.is_valid(lineblock))
			return StyleEnum.H1;
		if(this.h2_rule.is_valid(lineblock))
			return StyleEnum.H2;
		return StyleEnum.NORMAL;
	}

	public FormatEnum determine_format_status(LineBlock lineblock) {
		if(this.bold_rule.is_valid(lineblock))
			return FormatEnum.BOLD;
		if(this.italics_rule.is_valid(lineblock))
			return FormatEnum.ITALICS;
		return FormatEnum.REGULAR;
	}
	
	public String toString() {
		return this.name
			+ "\nOMIT: " + this.omit_rule.toString()
			+ "\nH1: " + this.h1_rule.toString()
			+ "\nH2: " + this.h2_rule.toString()
			+ "\nBOLD: " + this.bold_rule.toString()
			+ "\nITALICS: " + this.italics_rule.toString()
			+ "\n";
	}
}
