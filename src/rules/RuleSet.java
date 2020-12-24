package rules;

import datamodel.FormatEnum;
import datamodel.StyleEnum;
import datamodel.LineBlock;

/**
 * @class RuleSet
 * @brief Class to represent a set of rules
 * 		  The main idea of the class is that it has a field for every rule of interest.
 * 		  So, it has rules for headings and rules for style.
 * 		  Headings: the class has a rule for what to omit,
 * 		  a rule for how to handle heading 1, a rule for handling heading 2
 * 		  Style: the class has a rule for bold and a rule for italics.
 * 		  All these rules dictate when sth is t obe treated as h1, or bold, etc.
 * 		  If an item does not apply by any of the "heading" rules, it is by default NORMAL
 * 		  If an item does not apply by any of the "style" rules, it is by default REGULAR
 * 		  The class comes with two methods, that, given a LineBlock,
 * 		  decide which classification applies (a) for its heading and (b) for its style
 */
public class RuleSet {
	/**
	 * name -> The name of the current rule_set to produce
	 * omit_rule -> The IRule containing information about the omit rules
	 * h1_rule -> The IRule containing information about the h1 rules
	 * h2_rule -> The IRule containing information about the h2 rules
	 * bold_rule -> The IRule containing information about the bold rules
	 * italics_rule -> The IRule containing information about the italics rules
	 */
	private final String name;
	private final IRule omit_rule;
	private final IRule h1_rule;
	private final IRule h2_rule;
	private final IRule bold_rule;
	private final IRule italics_rule;

	/**
	 * @message name
	 * @brief Getter for name
	 * @return name
	 */
	private String name() {
		return this.name;
	}
	/**
	 * @message omit_rule
	 * @brief Getter for omit_rule
	 * @return omit_rule
	 */
	private IRule omit_rule() {
		return this.omit_rule;
	}
	/**
	 * @message h1_rule
	 * @brief Getter for h1_rule
	 * @return h1_rule
	 */
	private IRule h1_rule() {
		return this.h1_rule;
	}
	/**
	 * @message h2_rule
	 * @brief Getter for h2_rule
	 * @return h2_rule
	 */
	private IRule h2_rule() {
		return this.h2_rule;
	}
	/**
	 * @message bold_rule
	 * @brief Getter for bold_rule
	 * @return bold_rule
	 */
	private IRule bold_rule() {
		return this.bold_rule;
	}
	/**
	 * @message italics_rule
	 * @brief Getter for italics_rule
	 * @return italics_rule
	 */
	private IRule italics_rule() {
		return this.italics_rule;
	}

	/** @Constructor **/
	public RuleSet(String name, IRule omit_rule, IRule h1_rule, IRule h2_rule, IRule bold_rule, IRule italics_rule) {
		this.name = name;
		this.omit_rule = omit_rule;
		this.h1_rule = h1_rule;
		this.h2_rule = h2_rule;
		this.bold_rule = bold_rule;
		this.italics_rule = italics_rule;
	}

	public StyleEnum determine_heading_status(LineBlock lineblock) {
		if(omit_rule().is_valid(lineblock))
			return StyleEnum.OMITTED;
		if(h1_rule().is_valid(lineblock))
			return StyleEnum.H1;
		if(h2_rule().is_valid(lineblock))
			return StyleEnum.H2;
		return StyleEnum.NORMAL;
	}

	public FormatEnum determine_format_status(LineBlock lineblock) {
		if(bold_rule().is_valid(lineblock))
			return FormatEnum.BOLD;
		if(italics_rule().is_valid(lineblock))
			return FormatEnum.ITALICS;
		return FormatEnum.REGULAR;
	}
	
	public String toString() {
		return name()
			+ "\nOMIT: " + omit_rule().toString()
			+ "\nH1: " + h1_rule().toString()
			+ "\nH2: " + h2_rule().toString()
			+ "\nBOLD: " + bold_rule().toString()
			+ "\nITALICS: " + italics_rule().toString()
			+ "\n";
	}
}
