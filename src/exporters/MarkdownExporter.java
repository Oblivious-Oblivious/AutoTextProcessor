package exporters;

import datamodel.Document;

import files.WriteHandler;

/**
 * @class MarkdownExporter
 * @brief Controller for handling the export_markdown to a markdown type file
 */
public class MarkdownExporter implements IExporter {
    /**
     * document -> The document containing all information for the export_markdown
     * output_filename -> The file name to export_markdown to
     */
    private final Document document;
    private final String output_filename;

    /**
     * @message document
     * @brief Getter for document
     * @return document
     */
    private Document document() {
        return this.document;
    }

    /**
     * @message output_filename
     * @brief Getter for output_filename
     * @return output_filename
     */
    private String output_filename() {
        return this.output_filename;
    }

    /**
     * @message count_and_export
     * @brief a method that opens a new file writer and and handles exporting
     * @return The exported number of paragraphs (skips omitted)
     */
    private int count_and_export() {
        WriteHandler writer = new WriteHandler();
        int num_of_paragraphs = 0;

        if(writer.open(output_filename())) {
            num_of_paragraphs = document()
                    .line_blocks()
                    .stream()
                    .mapToInt(paragraph -> paragraph.export_markdown(writer))
                    .sum();
            writer.close();
        }

        return num_of_paragraphs;
    }

    /**
     * @Constructor
     */
    public MarkdownExporter(Document document, String output_filename) {
        this.document = document;
        this.output_filename = output_filename;
    }

    /**
     * @message export_markdown
     * @brief main interface method that gets called from engine
     * @return The number of paragraphs
     */
    @Override
    public int export() {
        return count_and_export();
    }
}
