package engine;

import datamodel.Document;
import reporter.IReporter;
import reporter.Reporter;

import java.util.List;

public class ReporterEngine {
	private final LoaderEngine loader_engine;
	private final Document document;

	private void ensure_loaded_line_blocks(int size) {
		if(size == 0)
			this.loader_engine.perform_task();
	}

	public ReporterEngine(LoaderEngine loader_engine, Document document) {
		this.document = document;
		this.loader_engine = loader_engine;
	}

	public List<String> perform_task() {
		ensure_loaded_line_blocks(this.document.get_line_blocks().size());

		IReporter reporter = new Reporter(this.document.get_line_blocks());
		return reporter.generate_report();
	}
}
