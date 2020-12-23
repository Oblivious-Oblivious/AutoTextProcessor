package exporters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import datamodel.Document;
import datamodel.LineBlock;

/**
 * @class PdfExporter
 * @brief Controller for handling the export_markdown to a pdf type file
 */
public class PdfExporter implements IExporter {
    /**
     * document -> The document containing all information for the export_markdown
     * output_filename -> The file name to export_markdown to
     */
    private final Document document;
    private final String output_filename;

    /**
     * @Constructor
     */
    public PdfExporter(Document document, String output_filename) {
        this.document = document;
        this.output_filename = output_filename;
    }

    /**
     * @message count_and_export
     * @brief a method that opens a new pdf writer and handles exporting
     * @return The exported number of paragraphs (skips omitted)
     */
    public int count_and_export() {
        int num_of_paragraphs = 0;
        com.itextpdf.text.Document pdf_document = new com.itextpdf.text.Document();

        try {
            /* We get an instance of a writer in order to properly work */
            /* pdf_document is a throwable */
            PdfWriter.getInstance(pdf_document, new FileOutputStream(this.output_filename));
            pdf_document.open();

            for(LineBlock paragraph : document.get_line_blocks())
                num_of_paragraphs += paragraph.export_pdf(pdf_document);

            pdf_document.close();
        }
        catch(FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }

        return num_of_paragraphs;
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
