package engine;

import datamodel.buildingblocks.Document;
import datamodel.ruleset.RuleSetCreator;

import java.util.List;

public class RegistrarEngine {
	private final Document document;

	private boolean annotated_rules_are_all_start_with(List<List<String>> input_spec) {
		if(input_spec == null)
			return true;

		for(List<String> l : input_spec)
			/* TODO -> IMPROVE */
			if(l.size() != 3 || !l.get(1).strip().equalsIgnoreCase("STARTS_WITH")) {
				System.err.println("Error in annotation spec");
				return true;
			}
		return false;
	}

	private void register_for_raw_files(List<List<String>> input_spec) {
		RuleSetCreator rule_set_creator = new RuleSetCreator(this.document.get_line_blocks(), input_spec, "input_rule_set");
		this.document.set_rule_set(rule_set_creator.create_rule_set());
	}

	private void register_for_annotated_files(List<List<String>> input_spec) {
		if(annotated_rules_are_all_start_with(input_spec))
			return;

		RuleSetCreator rule_set_creator = new RuleSetCreator(this.document.get_line_blocks(), input_spec, "input_rule_set");
		this.document.set_rule_set(rule_set_creator.create_rule_set());
	}

	public RegistrarEngine(Document document) {
		this.document = document;
	}

	public void perform_task(String what) {
        if(what.equals("RAW"))
            register_for_raw_files(this.document.get_input_spec());
        else if(what.equals("ANNOTATED"))
            register_for_annotated_files(this.document.get_input_spec());
	}
}
