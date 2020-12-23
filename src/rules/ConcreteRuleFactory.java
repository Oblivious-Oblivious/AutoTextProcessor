package rules;

import java.util.List;

import datamodel.LineBlock;

/**
 * Creator of concrete rule objects
 * 
 * For each concrete subclass, the factory comes with one method.
 * Thus, the clients of the package need only work with
 * (a) the current factory for rule creation
 * (b) the IRule abstract class for the actual work of a specific rule (rule validation)
 *  
 * @author pvassil
 * @version 0.1
 */
public class ConcreteRuleFactory {
	public IRule create_rule_undefined() {
		return new RuleUndefined();	
	}
	
	public IRule create_rule_all_caps() {
		return new RuleAllCaps();
	}

	public IRule create_rule_in_position(List<LineBlock> line_blocks, List<Integer> positions) {
		if((line_blocks == null) || (positions == null)) {
			System.err.println("[ConcreteRuleFactory] createRuleInPosition with empty parameters");
			return new RuleUndefined();
		}
		return new RuleInPosition(line_blocks, positions);
	}

	public IRule create_rule_start_with(String prefix) {
		if(prefix == null) {
			System.err.println("[ConcreteRuleFactory] createRuleStartWith with empty parameters");
			return new RuleUndefined();
		}
		return new RuleStartWith(prefix);
	}
}