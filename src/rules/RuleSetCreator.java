package rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import datamodel.LineBlock;

/**
 * @class RuleSetCreator
 * @brief The class is responsible for creating a RuleSet, given a set of strings as input.
 *        The set of strings to specify the rules are given as input at the constructor of the class
 *        The constructor also needs the set of LineBlocks of the document (for the positional rules)
 *        and a name to discriminate the new RuleSet from the others
 *        The core of the processing is performed by method create_rule_set()
 */
public class RuleSetCreator {
    /**
     * line_blocks -> The blocks to use for the POSITIONS rule creation
     * input_spec -> The list of lists of strings containing the rules
     * factory -> The factory object with which we will create a RuleSet
     * name -> The current name of the rule_set to generate
     * omit_rule -> The IRule containing information about the omit rules
	 * h1_rule -> The IRule containing information about the h1 rules
	 * h2_rule -> The IRule containing information about the h2 rules
	 * bold_rule -> The IRule containing information about the bold rules
	 * italics_rule -> The IRule containing information about the italics rules
     */
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
	 * @message line_blocks
	 * @brief Getter for line_blocks
	 * @return line_blocks
	 */
	private List<LineBlock> line_blocks() {
		return this.line_blocks;
	}

	/**
	 * @message input_spec
	 * @brief Getter for input_spec
	 * @return input_spec
	 */
	private List<List<String>> input_spec() {
		return this.input_spec;
	}

	/**
	 * @message factory
	 * @brief Getter for factory
	 * @return factory
	 */
	private ConcreteRuleFactory factory() {
		return this.factory;
	}

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

    /**
     * @Constructor
     * @brief The constructor of the class initializes the attributes of the class to the default values
     *        such that they are subsequently changed only if necessary
     *        The most important aspect of the project is the specification
     *        of the RuleSet by the clients of this class.
     *        This is represented in the attribute this.input_spec,
     *        which in turns is populated by the constructor's input parameter input_spec
     *        The format is as follows: input_spec is a List of List<String>.
     *        Each internal List<String> is a triplet of 3 strings:
     *        (a) style of heading, i.e., H1, H2, OMIT, <B>, <I>
     *        (b) when the rule is activated, i.e., STARTS_WITH, ALL_CAPS, POSITIONS
     *        (c) the value pertaining to the rule
     *        The only case where just a pair of strings is needed is ALL_CAPS
     *        POSITIONS requires a single number or a comma-separated list of numbers as 3rd argument
     *        For example, a triplet saying "OMIT", "POSITIONS" "4,5"
     *        will omit the positions 4 and 5 in the list of paragraphs
     *        As another example, a triplet saying "H1", "ALL_CAPS"
     *        will treat all lines with all their characters as capital letters as H1
     * @param line_blocks a List<LineBlock> representing the paragraphs of
     *                    the input (called LineBlocks in this project)
     * @param input_spec a List<List<String>> with the specification of behavior
     * @param name the name of the RuleSet to be created
     */
    public RuleSetCreator(List<LineBlock> line_blocks, List<List<String>> input_spec, String name) {
        this.line_blocks = line_blocks;
        this.input_spec = input_spec;
        this.name = name;
        this.factory = new ConcreteRuleFactory();

        IRule dummy_rule = factory.create_rule_undefined();
        omit_rule = dummy_rule;
        h1_rule = dummy_rule;
        h2_rule = dummy_rule;
        bold_rule = dummy_rule;
        italics_rule = dummy_rule;
    }

    /**
     * @message produce_position_of_rule
     * @brief Switch on the second argument that signifies the rule position
     * @param l -> The current rule list
     * @return The newly produced rule
     */
    private IRule produce_position_of_rule(List<String> l) {
        /* The rule object to produce */
        IRule current_rule = factory().create_rule_undefined();

        switch(l.get(1).strip().toUpperCase()) {
            case "STARTS_WITH" -> current_rule = factory().create_rule_start_with(l.get(2));
            case "ALL_CAPS" -> current_rule = factory().create_rule_all_caps();
            case "POSITIONS" -> current_rule = factory().create_rule_in_position(
                    line_blocks(),
                    new ArrayList<>(Arrays.asList(l.get(2).split("\\s*,\\s*")))
                            .stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())
            );
        }
        return current_rule;
    }

    /**
     * @message produce_type_of_rule
     * @brief Switch on the first argument that signifies the rule type
     * @param l -> The current rule list
     * @param current_rule -> The rule object to produce
     */
    private void produce_type_of_rule(List<String> l, IRule current_rule) {
        switch(l.get(0).strip().toUpperCase()) {
            case "OMIT" -> this.omit_rule = current_rule;
            case "H1" -> this.h1_rule = current_rule;
            case "H2" -> this.h2_rule = current_rule;
            case "<B>" -> this.bold_rule = current_rule;
            case "<I>" -> this.italics_rule = current_rule;
            default -> System.err.println("[RuleSetCreator] Wrong rule set specification syntax. Aborting");
        }
    }

    /**
     * @message create_rule_set
     * @brief The class populates a new RuleSet with all its necessary
     *        rules and returns it as the result of the processing
     *        The class employs the abstract class IRule
     *        (to be concreted with concrete subclasses per category of rule)
     *        and the respective Factory. Thus, a set of concrete classes to
     *        handle the rules of AllCaps, Position, StartsWith, or Undefined are needed.
     *        At the end, the different kinds of rules of the RuleSet
     *        are all populated with an object of the appropriate class.
     * @return the RuleSet to be generated
     */
    public RuleSet create_rule_set() {
        input_spec().forEach(l -> {
            IRule current_rule = produce_position_of_rule(l);
            produce_type_of_rule(l, current_rule);
            System.out.println("[RuleSetCreator] " + current_rule.toString() + "\n");
        });

        return new RuleSet(name(), omit_rule(), h1_rule(), h2_rule(), bold_rule(), italics_rule());
    }
}
