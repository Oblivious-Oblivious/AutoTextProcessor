package datamodel.ruleset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.IRule;
import datamodel.rules.ConcreteRuleFactory;

/**
 * The class is responsible for creating a RuleSet, given a set of strings as input.
 *
 * The set of strings to specify the rules are given as input at the constructor of the class
 * The constructor also needs the set of LineBlocks of the document (for the positional rules) 
 * and a name to discriminate the new RuleSet from the others
 *
 * The core of the processing is performed by method create_rule_set()
 *
 * @author pvassil
 * @version 0.1
 */
public class RuleSetCreator {
    private final List<LineBlock> line_blocks;
    private final List<List<String>> input_spec;
    private final ConcreteRuleFactory factory;
    private final String name;
    private IRule omit_rule;
    private IRule h1_rule;
    private IRule h2_rule;
    private IRule bold_rule;
    private IRule italics_rule;

    /**
     * The constructor of the class initializes the attributes of the class to the default values
     * such that they are subsequently changed only if necessary
     *
     * The most important aspect of the project is the specification of the RuleSet by the clients of this class.
     * This is represented in the attribute this.input_spec, which in turns is populated by the constructor's input parameter input_spec
     * The format is as follows: input_spec is a List of List<String>. Each internal List<String> is a triplet of 3 strings:
     * (a) style of heading, i.e., H1, H2, OMIT, <B>, <I>
     * (b) when the rule is activated, i.e., STARTS_WITH, ALL_CAPS, POSITIONS
     * (c) the value pertaining to the rule
     * The only case where just a pair of strings is needed is ALL_CAPS
     * POSITIONS requires a single number or a comma-separated list of numbers as 3rd argument
     *
     * For example, a triplet saying "OMIT", "POSITIONS" "4,5" will omit the positions 4 and 5 in the list of paragraphs
     * As another example, a triplet saying "H1", "ALL_CAPS" will treat all lines with all their characters as capital letters as H1
     *
     * @param line_blocks a List<LineBlock> representing the paragraphs of the input (called LineBlocks in this project)
     * @param input_spec a List<List<String>> with the specification of behavior
     * @param name the name of the RuleSet to be created
     */
    public RuleSetCreator(List<LineBlock> line_blocks, List<List<String>> input_spec, String name) {
        this.line_blocks = line_blocks;
        this.input_spec = input_spec;
        this.factory = new ConcreteRuleFactory();
        this.name = name;

        IRule current_rule = factory.create_rule_undefined();

        omit_rule = current_rule;
        h1_rule = current_rule;
        h2_rule = current_rule;
        bold_rule = current_rule;
        italics_rule = current_rule;
    }

    /**
     * The class populates a new RuleSet with all its necessary rules and returns it as the result of the processing
     *
     * The class employs the abstract class IRule (to be concreted with concrete subclasses per category of rule)
     * and the respective Factory. Thus, a set of concrete classes to handle the rules of AllCaps, Position, StartsWith, or Undefined are needed.
     * At the end, the different kinds of rules of the RuleSet are all populated with an object of the appropriate class.
     *
     * @return the RuleSet to be generated
     */
    public RuleSet create_rule_set() {
        String lastParameter;

        for(List<String> l : this.input_spec) {
            IRule current_rule = factory.create_rule_undefined();

            switch(l.get(1).strip().toUpperCase()) {
                case "STARTS_WITH" -> {
                    lastParameter = l.get(2);
                    current_rule = factory.create_rule_start_with(lastParameter);
                }
                case "ALL_CAPS" -> current_rule = factory.create_rule_all_caps();
                case "POSITIONS" -> {
                    lastParameter = l.get(2);
                    List<String> stringList
                            = new ArrayList<>(Arrays.asList(lastParameter.split("\\s*,\\s*")));
                    List<Integer> intList
                            = stringList
                            .stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    current_rule = factory.create_rule_in_position(this.line_blocks, intList);
                }
            }

            if(current_rule == null) {
                System.err.println("[RuleSetCreator] null format rule; exiting");
                System.exit(-1);
            }

            switch(l.get(0).strip().toUpperCase()) {
                case "OMIT" -> this.omit_rule = current_rule;
                case "H1" -> this.h1_rule = current_rule;
                case "H2" -> this.h2_rule = current_rule;
                case "<B>" -> this.bold_rule = current_rule;
                case "<I>" -> this.italics_rule = current_rule;
                default -> {
                    System.err.println("[RuleSetCreator] Wrong rule set specification syntax. Aborting");
                    System.exit(-100);
                }
            }

            System.out.println("[RuleSetCreator] " + current_rule.toString() + "\n");
        }

        return new RuleSet(this.name, this.omit_rule, this.h1_rule, this.h2_rule, this.bold_rule, this.italics_rule);
    }
}
