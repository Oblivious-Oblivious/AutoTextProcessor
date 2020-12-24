package engine;

import datamodel.Document;
import datamodel.DocumentExportType;
import datamodel.DocumentLoadType;

import java.util.ArrayList;
import java.util.List;

/**
 * @class MainEngine
 * @brief Implements the IMainEngine interface
 */
public class MainEngine implements IMainEngine {
	/**
	 * document -> The main model class storing data used for implementing methods of the use cases
	 * registrar_engine -> Register use case engine
	 * loader_engine -> Load use case engine
	 * reporter_engine -> Report use case engine
	 * exporter_engine -> Export use case engine
	 */
	private final Document document;
	private final RegistrarEngine registrar_engine;
	private final LoaderEngine loader_engine;
	private final ReporterEngine reporter_engine;
	private final ExporterEngine exporter_engine;

	/**
	 * @message document
	 * @brief Getter for document
	 * @return document
	 */
	private Document document() {
		return this.document;
	}
	/**
	 * @message registrar_engine
	 * @brief Getter for registrar_engine
	 * @return registrar_engine
	 */
	private RegistrarEngine registrar_engine() {
		return this.registrar_engine;
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
	 * @message reporter_engine
	 * @brief Getter for reporter_engine
	 * @return reporter_engine
	 */
	private ReporterEngine reporter_engine() {
		return this.reporter_engine;
	}
	/**
	 * @message exporter_engine
	 * @brief Getter for exporter_engine
	 * @return exporter_engine
	 */
	private ExporterEngine exporter_engine() {
		return this.exporter_engine;
	}

	/**
	 * @message find_input_type
	 * @brief Switch on the input type and produce the correct load type
	 * @param input_type -> The string to condition upon
	 * @return -> The correct input type
	 */
	private DocumentLoadType find_input_type(String input_type) {
		switch(input_type) {
			case "RAW" -> {
				return DocumentLoadType.RAW;
			}
			case "ANNOTATED" -> {
				return DocumentLoadType.ANNOTATED;
			}
		}

		/* Considered a null object */
		return DocumentLoadType.RAW;
	}

	/**
	 * @Constructor
	 * @param input_type the String characterizing the document as
	 *                     (DocumentLoadType.RAW) unless "ANNOTATED" is
	 *                     given as input, in which case, the input
	 *                     file is characterized as
	 *                     (DocumentLoadType.ANNOTATED)
	 */
	public MainEngine(String input_type) {
		DocumentLoadType doc_type = find_input_type(input_type);

		this.document = new Document();
		document().doc_type_eq(doc_type);

		this.registrar_engine = new RegistrarEngine(document());
		this.loader_engine = new LoaderEngine(document());
		this.reporter_engine = new ReporterEngine(document(), loader_engine());
		this.exporter_engine = new ExporterEngine(document(), loader_engine());
	}

	@Override
	public void register_input_rule_set_for_plain_files(List<List<String>> input_spec) {
		document().input_spec_eq(input_spec);
		document().prefixes_eq(new ArrayList<>());
		document().doc_type_eq(DocumentLoadType.RAW);
		registrar_engine().perform_task();
	}

	@Override
	public void register_input_rule_set_for_annotated_files(List<List<String>> input_spec, List<String> prefixes) {
		document().input_spec_eq(input_spec);
		document().prefixes_eq(prefixes);
		document().doc_type_eq(DocumentLoadType.ANNOTATED);
		registrar_engine().perform_task();
	}

	@Override
	public int load_file_and_characterize_blocks(String filepath) {
		document().input_filename_eq(filepath);
		return loader_engine().perform_task();
	}

	@Override
	public List<String> report_with_stats() {
		return reporter_engine().perform_task();
	}

	@Override
	public int export_markdown(String output_file_name) {
		document().export_type_eq(DocumentExportType.MD);
		document().output_filename_eq(output_file_name);
		return exporter_engine().perform_task();
	}

	@Override
	public int export_pdf(String output_file_name) {
		document().export_type_eq(DocumentExportType.PDF);
		document().output_filename_eq(output_file_name);
		return exporter_engine().perform_task();
	}
}
