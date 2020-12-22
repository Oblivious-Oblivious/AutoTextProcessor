package datamodel.buildingblocks;

import datamodel.ruleset.RuleSet;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private DocumentLoadType doc_type;
    private List<LineBlock> line_blocks;
    private String input_filename;
    private String output_filename;
    private List<List<String>> input_spec;
    private List<String> prefixes;
    private RuleSet rule_set;

    public Document() {
        this.line_blocks = new ArrayList<>();
    }

    public DocumentLoadType get_doc_type() {
        return this.doc_type;
    }

    public void set_doc_type(String p_input_type) {
        this.doc_type = p_input_type.equalsIgnoreCase("ANNOTATED")
                ? DocumentLoadType.ANNOTATED
				: DocumentLoadType.RAW;
    }

    public List<LineBlock> get_line_blocks() {
        return this.line_blocks;
    }
    public void set_line_blocks(List<LineBlock> line_blocks) {
        this.line_blocks = line_blocks;
    }

    public String get_input_filename() {
        return input_filename;
    }
    public void set_input_filename(String input_filename) {
        this.input_filename = input_filename;
    }

    public String get_output_filename() {
        return output_filename;
    }
    public void set_output_filename(String output_filename) {
        this.output_filename = output_filename;
    }

    public List<List<String>> get_input_spec() {
        return input_spec;
    }
    public void set_input_spec(List<List<String>> input_spec) {
        this.input_spec = input_spec;
    }

    public List<String> get_prefixes() {
        return prefixes;
    }
    public void set_prefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public RuleSet get_rule_set() {
        return rule_set;
    }
    public void set_rule_set(RuleSet rule_set) {
        this.rule_set = rule_set;
    }
}
