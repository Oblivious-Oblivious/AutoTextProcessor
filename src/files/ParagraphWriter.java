package files;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;

/* TODO -> Make the class conform into the GeneralWriter API */

/**
 * @class ParagraphWriter
 * @brief Class for writing to a pdf object (itext)
 */
public class ParagraphWriter {
    /**
     * @message write_to_file
     * @brief uses the pdfDocument to add a new paragraph with the specified font
     * @param pdfDocument -> The document to add to
     * @param paragraph -> The paragraph string to convert to a pdf Paragraph object
     * @param font -> The visual difference in the export
     * @return 1 if the paragraph is exported correctly
     */
    public int write_to_file(com.itextpdf.text.Document pdfDocument, String paragraph, Font font) {
        try {
            /* pdfDocument is a throwable */
            Paragraph newParagraph = new Paragraph(paragraph, font);
            pdfDocument.add(newParagraph);

            /* We add an empty paragraph with a phrase with a newline in it */
            pdfDocument.add(new Paragraph());
            pdfDocument.add(new Phrase("\n"));
            return 1;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
