package engine;

import datamodel.Document;

import java.util.ArrayList;
import java.util.List;

public class MainEngine implements IMainEngine {
	private final RegistrarEngine registrar_engine;
	private final LoaderEngine loader_engine;
	private final ReporterEngine reporter_engine;
	private final ExporterEngine exporter_engine;
	private final Document document;

	/**
	 * @Constructor
	 * @param p_input_type the String characterizing the document as
	 *                     (DocumentLoadType.RAW) unless "ANNOTATED" is
	 *                     given as input, in which case, the input
	 *                     file is characterized as
	 *                     (DocumentLoadType.ANNOTATED)
	 */
	public MainEngine(String p_input_type) {
		this.document = new Document();
		this.document.set_doc_type(p_input_type);

		this.registrar_engine = new RegistrarEngine(this.document);
		this.loader_engine = new LoaderEngine(this.document);
		this.reporter_engine = new ReporterEngine(loader_engine, this.document);
		this.exporter_engine = new ExporterEngine(loader_engine, this.document);
	}

	@Override
	public void register_input_rule_set_for_plain_files(List<List<String>> input_spec) {
		this.document.set_input_spec(input_spec);
		this.document.set_prefixes(new ArrayList<>());
		this.registrar_engine.perform_task("RAW");
	}

	@Override
	public void register_input_rule_set_for_annotated_files(List<List<String>> input_spec, List<String> prefixes) {
		this.document.set_input_spec(input_spec);
		this.document.set_prefixes(prefixes);
		this.registrar_engine.perform_task("ANNOTATED");
	}

	@Override
	public int load_file_and_characterize_blocks(String filepath) {
		this.document.set_input_filename(filepath);
		return this.loader_engine.perform_task();
	}

	@Override
	public List<String> report_with_stats() {
		return this.reporter_engine.perform_task();
	}

	@Override
	public int export_markdown(String output_file_name) {
		this.document.set_output_filename(output_file_name);
		return this.exporter_engine.perform_task("MD");
	}

	@Override
	public int export_pdf(String output_file_name) {
		this.document.set_output_filename(output_file_name);
		return this.exporter_engine.perform_task("PDF");
	}
}
