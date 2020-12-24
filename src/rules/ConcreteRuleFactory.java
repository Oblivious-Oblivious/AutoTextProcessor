package rules;

import java.util.List;

import datamodel.LineBlock;

/**
 * @class ConcreteRuleFactory
 * @brief Creator of concrete rule objects
 * 		  For each concrete subclass, the factory comes with one method.
 * 		  Thus, the clients of the package need only work with
 * 		  (a) the current factory for rule creation
 * 		  (b) the IRule abstract class for the actual work of a specific rule (rule validation)
 */
public class ConcreteRuleFactory {
	/**
	 * @message create_rule_undefined
	 * @brief Crafts a RuleUndefined object
	 * @return RuleUndefined
	 */
	public IRule create_rule_undefined() {
		return new RuleUndefined();	
	}

	/**
	 * @message create_rule_all_caps
	 * @brief Crafts a RuleAllCaps object
	 * @return RuleAllCaps
	 */
	public IRule create_rule_all_caps() {
		return new RuleAllCaps();
	}

	/**
	 * @message create_rule_in_position
	 * @brief Crafts a RuleInPosition object
	 * @return RuleInPosition
	 */
	public IRule create_rule_in_position(List<LineBlock> line_blocks, List<Integer> positions) {
		return new RuleInPosition(line_blocks, positions);
	}

	/**
	 * @message create_rule_start_with
	 * @brief Crafts a RuleStartWith object
	 * @return RuleStartWith
	 */
	public IRule create_rule_start_with(String prefix) {
		return new RuleStartWith(prefix);
	}
}
