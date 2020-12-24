package engine;

import datamodel.Document;

import reporter.IReporter;
import reporter.Reporter;

import java.util.List;

/**
 * @class ReporterEngine
 * @brief A helper controller for performing the reporting tasks
 */
public class ReporterEngine implements IEngine<List<String>> {
	/**
     * loader_engine -> A loader engine needed for ensuring all line_blocks are loaded
     * document -> A document object storing information needed to perform the export actions
     */
	private final LoaderEngine loader_engine;
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
     * @message loader_engine
     * @brief Getter for loader_engine
     * @return loader_engine
     */
    private LoaderEngine loader_engine() {
        return this.loader_engine;
    }

	/**
     * @message ensure_loaded_line_blocks
     * @brief Makes sure if the size of the line_blocks is zero, that is loads them correctly
	 */
	private void ensure_loaded_line_blocks() {
		if(document().line_blocks().size() == 0)
			loader_engine().perform_task();
	}

	/** @Constructor **/
	public ReporterEngine(Document document, LoaderEngine loader_engine) {
		this.document = document;
		this.loader_engine = loader_engine;
	}

	/**
     * @message perform_task
     * @brief Main interface that executes the reporting task
     * @return A list of statistics about the line_blocks
     */
	@Override
	public List<String> perform_task() {
		ensure_loaded_line_blocks();

		IReporter reporter = new Reporter(document().line_blocks());
		return reporter.generate_report();
	}
}
