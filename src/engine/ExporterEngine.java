package engine;

import datamodel.buildingblocks.Document;
import exporters.IExporter;
import exporters.MarkdownExporter;
import exporters.PdfExporter;

import java.nio.file.Paths;

public class ExporterEngine {
    private final LoaderEngine loader_engine;
	private final Document document;

	private void ensure_loaded_line_blocks(int size) {
		if(size == 0)
			this.loader_engine.perform_task();
	}

	private String get_simple_input_filename() {
		return Paths.get(this.document.get_input_filename()).getFileName().toString();
	}

	private void log_execution(String output_filename, int outputNumParagraphs, String s) {
		String simple_input_filename = get_simple_input_filename();

		System.out.println(s + simple_input_filename + "]"
				+ " exported as " + output_filename + "\n"
				+ " Input #pars: " + this.document.get_line_blocks().size()
				+ " Output #pars: " + outputNumParagraphs);
	}

	private int export_for_markdown_file(String output_filename) {
		ensure_loaded_line_blocks(this.document.get_line_blocks().size());

		IExporter exporter = new MarkdownExporter(this.document, output_filename);
		int outputNumParagraphs = exporter.export();

		log_execution(output_filename,
				outputNumParagraphs,
				"[MainEngine.loadProcessMarkup] [file: ");

		return outputNumParagraphs;
	}

	private int export_for_pdf_file(String output_filename) {
		ensure_loaded_line_blocks(this.document.get_line_blocks().size());

		IExporter exporter = new PdfExporter(this.document, output_filename);
		int outputNumParagraphs = exporter.export();

		log_execution(output_filename,
				outputNumParagraphs,
				"[MainEngine.loadProcessPdf] [file: ");

		return outputNumParagraphs;
	}

	public ExporterEngine(LoaderEngine loader_engine, Document document) {
    	this.document = document;
        this.loader_engine = loader_engine;
    }

	public Integer perform_task(String what) {
        if(what.equals("MD"))
            return export_for_markdown_file(this.document.get_output_filename());
        else if(what.equals("PDF"))
            return export_for_pdf_file(this.document.get_output_filename());
        else
        	return -1;
    }
}
