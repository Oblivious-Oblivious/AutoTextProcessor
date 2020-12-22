package engine;

import dataload.ILoader;
import dataload.RawFileLineLoader;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.LineBlock;
import datamodel.ruleset.RuleSet;

import java.util.List;
import java.util.Objects;

public class LoaderEngine {
	private final Document document;

	/**
	 * @message load_raw_document
	 * @brief Loads a filename and stores data into the line_blocks value
	 * @param filepath -> The file to load from
	 */
	private void load_raw_document(String filepath) {
		ILoader loader = new RawFileLineLoader();
		loader.load(filepath, this.document.get_line_blocks());
	}

	private void load_file(String filepath) {
        if(this.document.get_line_blocks().size() == 0)
			load_raw_document(filepath);
		System.out.println(filepath);
	}

	private void replace_prefixes(LineBlock l) {
		for(String prefix : this.document.get_prefixes())
			if(l.get_lines().get(0).startsWith(prefix))
				l.get_lines().set(0, l.get_lines().get(0).replaceFirst(prefix, ""));
	}

	/**
	 * @message characterize_raw_file
	 * @brief Set the style and format of the each line in the line_blocks List
	 * @param line_blocks -> The List of line_blocks to modify
	 * @param rule_set -> The rule_set according to which we characterize
	 */
	private void characterize_raw_file(List<LineBlock> line_blocks, RuleSet rule_set) {
		for(LineBlock l : line_blocks) {
			l.set_style(rule_set.determineHeadingStatus(l));
			l.set_format(rule_set.determineFormatStatus(l));
			/* TODO -> MAKE API THE SAME */
		}
	}

	/**
	 * @message characterize_annotated_file
	 * @brief Set style, format and prefix
	 * @param line_blocks -> The List of line_blocks to modify
	 * @param rule_set -> The rule_set according to which we characterize
	 */
	private void characterize_annotated_file(List<LineBlock> line_blocks, RuleSet rule_set) {
		for(LineBlock l : line_blocks) {
			l.set_style(rule_set.determineHeadingStatus(l));
			l.set_format(rule_set.determineFormatStatus(l));
			replace_prefixes(l); /* TODO -> REVERSE */
		}
	}

	private void produce_characterize_message(Document document, RuleSet rule_set, List<LineBlock> line_blocks) {
		switch(document.get_doc_type()) {
			case RAW -> characterize_raw_file(line_blocks, rule_set);
			case ANNOTATED -> characterize_annotated_file(line_blocks, rule_set);
			default -> {
				System.err.println("   WRONG FILE TYPE !!!");
				System.exit(-100);
			}
		}
	}

	private List<LineBlock> get_safe_line_blocks(Document document, RuleSet rule_set) {
		List<LineBlock> line_blocks = document.get_line_blocks();
		Objects.requireNonNull(document);
		Objects.requireNonNull(line_blocks);
		Objects.requireNonNull(rule_set);
		return line_blocks;
	}

	/**
	 * @message characterize_line_blocks
	 * @brief Change format and prefixes for the document
	 */
	private void characterize_line_blocks(Document document, RuleSet rule_set) {
		List<LineBlock> line_blocks = get_safe_line_blocks(document, rule_set);
		produce_characterize_message(document, rule_set, line_blocks);
	}

	private int characterize_blocks_and_get_size() {
		if(this.document.get_rule_set() != null)
			characterize_line_blocks(this.document, this.document.get_rule_set());

		return this.document.get_line_blocks().size();
	}

	public LoaderEngine(Document document) {
		this.document = document;
	}

    public int perform_task() {
        load_file(this.document.get_input_filename());
		return characterize_blocks_and_get_size();
    }
}
