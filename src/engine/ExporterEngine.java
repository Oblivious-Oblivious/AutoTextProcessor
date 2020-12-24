package engine;

import datamodel.Document;

import exporters.IExporter;
import exporters.MarkdownExporter;
import exporters.PdfExporter;

import java.nio.file.Paths;

/**
 * @class ExporterEngine
 * @brief Helper controller for engine performing the export actions
 */
public class ExporterEngine implements IEngine<Integer> {
    /**
     * loader_engine -> A loader engine needed for ensuring all line_blocks are loaded
     * document -> A document object storing information needed to perform the export actions
     */
    private final LoaderEngine loader_engine;
    private final Document document;

    /**
     * @message loader_engine
     * @brief Getter for loader_engine
     * @return loader_engine
     */
    private LoaderEngine loader_engine() {
        return this.loader_engine;
    }
    /**
     * @message document
     * @brief Getter for document
     * @return document
     */
    private Document document() {
        return this.document;
    }

    /**
     * @message ensure_loaded_line_blocks
     * @brief Makes sure if the size of the line_blocks is zero, that is loads them correctly
     */
    private void ensure_loaded_line_blocks() {
        if(document().line_blocks().size() == 0)
            loader_engine().perform_task();
    }

    /**
     * @message get_simple_input_filename
     * @brief Get a formatted version of the full filepath given as an input
     * @return The file version of the path
     */
    private String get_simple_input_filename() {
        /* Gets the filename off the input path and returns a string version of it */
        return Paths.get(document().input_filename()).getFileName().toString();
    }

    /**
     * @message log_execution
     * @brief Prints a log of the export execution path
     * @param output_num_paragraphs -> The number of paragraphs exported
     * @param s -> A string detailing the export process used
     */
    private void log_execution(int output_num_paragraphs, String s) {
        System.out.println(s + get_simple_input_filename() + "]"
                + " exported as " + document().output_filename() + "\n"
                + " Input #pars: " + document().line_blocks().size()
                + " Output #pars: " + output_num_paragraphs);
    }

    /**
     * @message export
     * @brief Counts the number of exported paragraphs and exports them to an output file
     * @param process -> A string signifying the output process used
     * @param exporter -> The exporter object injected for the action
     * @return -> The total exported number of paragraphs
     */
    private int export(String process, IExporter exporter) {
        ensure_loaded_line_blocks();

        int output_num_paragraphs = exporter.export();

        log_execution(output_num_paragraphs, "[MainEngine." + process + "] [file: ");

        return output_num_paragraphs;
    }

    /**
     * @Constructor
     */
    public ExporterEngine(Document document, LoaderEngine loader_engine) {
        this.document = document;
        this.loader_engine = loader_engine;
    }

    /**
     * @message perform_task
     * @brief Main (factory) interface that executes the exporting task
     * @return The number of paragraphs exported
     */
    @Override
    public Integer perform_task() {
        switch(document().export_type()) {
            case MD -> {
                return export(
                        "loadProcessMarkup",
                        new MarkdownExporter(document(), document().output_filename()));
            }
            case PDF -> {
                return export(
                        "loadProcessPdf",
                        new PdfExporter(document(), document().output_filename()));
            }
            default -> {
                return -1;
            }
        }
    }
}
