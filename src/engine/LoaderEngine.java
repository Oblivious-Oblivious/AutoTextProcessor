package engine;

import dataload.RawFileLineLoader;

import datamodel.Document;
import datamodel.LineBlock;

import java.util.List;
import java.util.Objects;

/**
 * @class LoaderEngine
 * @brief A helper controller for the engine, that performs loading actions
 */
public class LoaderEngine implements IEngine<Integer> {
	/**
	 * document -> The main document that stores the data used
	 */
	private final Document document;

	/**
	 * @message document
	 * @brief Getter for document
	 * @return document
	 */
	private Document document() {
		return this.document;
	}

	/**
	 * @message load_raw_document
	 * @brief Loads a filename and stores data into the line_blocks value
	 * @param filepath -> The file to load from
	 */
	private void load_raw_document(String filepath) {
		new RawFileLineLoader().load(filepath, document().line_blocks());
	}

	/**
	 * @message load_file
	 * @brief Loads a raw document file if the line_blocks are not yet set
	 */
	private void load_file() {
        if(document().line_blocks().size() == 0)
			load_raw_document(document().input_filename());
		System.out.println(document().input_filename());
	}

	/**
	 * @message replace_prefixes
	 * @brief Iterate through the lines of the line_block and remove prefixes
	 * @param l -> The line_block to process
	 */
	private void replace_prefixes(LineBlock l) {
		document().prefixes().forEach(prefix -> {
			if(l.lines().get(0).startsWith(prefix))
				l.lines().set(0, l.lines().get(0).replaceFirst(prefix, ""));
		});
	}

	/**
	 * @message characterize_raw_file
	 * @brief Set the style and format of the each line in the line_blocks List
	 * @param line_blocks -> The List of line_blocks to modify
	 */
	private void characterize_raw_file(List<LineBlock> line_blocks) {
		line_blocks.forEach(l -> {
			l.style_eq(document().rule_set().determine_heading_status(l));
			l.format_eq(document().rule_set().determine_format_status(l));
			/* TODO -> MAKE API THE SAME */
		});
	}

	/**
	 * @message characterize_annotated_file
	 * @brief Set style, format and prefix
	 * @param line_blocks -> The List of line_blocks to modify
	 */
	private void characterize_annotated_file(List<LineBlock> line_blocks) {
		line_blocks.forEach(l -> {
			l.style_eq(document().rule_set().determine_heading_status(l));
			l.format_eq(document().rule_set().determine_format_status(l));
			replace_prefixes(l); /* TODO -> REVERSE */
		});
	}

	/**
	 * @message produce_characterize_message
	 * @brief Factory method that calls the correct characterize method
	 * @param line_blocks -> The safe line_blocks after being checked for nulls
	 */
	private void produce_characterize_message(List<LineBlock> line_blocks) {
		switch(document.doc_type()) {
			case RAW -> characterize_raw_file(line_blocks);
			case ANNOTATED -> characterize_annotated_file(line_blocks);
			default -> {
				System.err.println("   WRONG FILE TYPE !!!");
				System.exit(-100);
			}
		}
	}

	/**
	 * @message get_safe_line_blocks
	 * @brief Checks the line_blocks inside document for nullity errors
	 * @return Null safe line_blocks
	 */
	private List<LineBlock> get_safe_line_blocks() {
		List<LineBlock> line_blocks = document().line_blocks();
		Objects.requireNonNull(document());
		Objects.requireNonNull(line_blocks);
		Objects.requireNonNull(document().rule_set());
		return line_blocks;
	}

	/**
	 * @message set_format_and_prefixes
	 * @brief Change format and prefixes for the document
	 */
	private void set_format_and_prefixes() {
		List<LineBlock> line_blocks = get_safe_line_blocks();
		produce_characterize_message(line_blocks);
	}

	/**
	 * @message characterize_blocks
	 * @brief If the rule set is safe, it characterizes the line_blocks then calculates their size
	 */
	private void characterize_blocks() {
		if(document().rule_set() != null)
			set_format_and_prefixes();
	}

	/**
	 * @message get_blocks_size
	 * @brief Finds the size of the line_block list
	 * @return The size of the line_blocks list
	 */
	private int get_blocks_size() {
		return document().line_blocks().size();
	}

	/** @Constructor **/
	public LoaderEngine(Document document) {
		this.document = document;
	}

	/**
	 * @message perform_task
	 * @brief Loads file, characterizes blocks, and gets their size
	 * @return The number of line_blocks loaded
	 */
	@Override
    public Integer perform_task() {
        load_file();
        characterize_blocks();
        return get_blocks_size();
    }
}
