package datamodel;

import rules.RuleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @message Document
 * @brief Model for storing data about the current document we are editing
 */
public class Document {
    /**
     * doc_type -> The current load type
     * export_type -> The current export type
     * line_blocks -> The current state of the line blocks loaded
     * input_filename -> The input filename to read from
     * output_filename -> The output filename to export to
     * input_spec -> The list of rules before being edited
     * prefixes -> The list of prefixes to replace
     * rule_set -> The produced rule set for the current document
     */
    private DocumentLoadType doc_type;
    private DocumentExportType export_type;
    private final List<LineBlock> line_blocks;
    private String input_filename;
    private String output_filename;
    private List<List<String>> input_spec;
    private List<String> prefixes;
    private RuleSet rule_set;

    /** @Constructor **/
    public Document() {
        this.line_blocks = new ArrayList<>();
    }

    /* ACCESSORS FOR ALL FIELDS */
    public DocumentLoadType doc_type() {
        return this.doc_type;
    }
    public void doc_type_eq(DocumentLoadType doc_type) {
        this.doc_type = doc_type;
    }

    public DocumentExportType export_type() {
        return this.export_type;
    }
    public void export_type_eq(DocumentExportType export_type) {
        this.export_type = export_type;
    }

    public List<LineBlock> line_blocks() {
        return this.line_blocks;
    }

    public String input_filename() {
        return input_filename;
    }
    public void input_filename_eq(String input_filename) {
        this.input_filename = input_filename;
    }

    public String output_filename() {
        return output_filename;
    }
    public void output_filename_eq(String output_filename) {
        this.output_filename = output_filename;
    }

    public List<List<String>> input_spec() {
        return input_spec;
    }
    public void input_spec_eq(List<List<String>> input_spec) {
        this.input_spec = input_spec;
    }

    public List<String> prefixes() {
        return prefixes;
    }
    public void prefixes_eq(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public RuleSet rule_set() {
        return rule_set;
    }
    public void rule_set_eq(RuleSet rule_set) {
        this.rule_set = rule_set;
    }
}
