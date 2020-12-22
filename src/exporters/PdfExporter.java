package exporters;

import java.io.FileOutputStream;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

/**
 * PdfExporter
 */
public class PdfExporter implements IExporter {
    private final Document document;
    private final String outputFileName;
    private com.itextpdf.text.Document pdfDoc;

    private Font getFont(LineBlock l) {
        if(l.get_style() == StyleEnum.OMITTED) /* OMITTED */
            return null;
        else if(l.get_style() == StyleEnum.H1)
            return new Font(FontFamily.HELVETICA, 28);
        else if(l.get_style() == StyleEnum.H2)
            return new Font(FontFamily.HELVETICA, 20);
        else if(l.get_format() == FormatEnum.BOLD)
            return new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        else if(l.get_format() == FormatEnum.ITALICS)
            return new Font(FontFamily.HELVETICA, 12, Font.ITALIC);
        else /* REGULAR */
            return new Font(FontFamily.HELVETICA, 12);
    }

    private int addParagraph(LineBlock l) {
        StringBuilder parBlock = new StringBuilder();

        Font format = getFont(l);
        if(format == null)
            return 0;

        for(String block : l.get_lines())
            parBlock.append(block)
                    .append(" ");

        try {
            this.pdfDoc.add(new Paragraph(parBlock.toString(), format));
            this.pdfDoc.add(new Paragraph());
            this.pdfDoc.add(new Phrase("\n"));
        }
        catch(Exception e) {
            System.out.println("Error in creating a PDF handler: " + e);
        }

        return 1;
    }

    private int addContent() {
        int par = 0;

        for(LineBlock l : this.document.get_line_blocks())
            par += addParagraph(l);

        return par;
    }

    private void createPdfHandler() {
        this.pdfDoc = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(this.pdfDoc, new FileOutputStream(this.outputFileName));
            this.pdfDoc.open();
        }
        catch(Exception e) {
            System.out.println("Error in creating a PDF handler");
        }
    }

    private void closePdfHandler() {
        this.pdfDoc.close();
    }

    public PdfExporter(Document document, String outputFileName) {
        this.document = document;
        this.outputFileName = outputFileName;
    }

    public int export() {
        createPdfHandler();
        int par = addContent();
        closePdfHandler();

        return par;
    }
}
