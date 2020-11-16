package exporters;

import java.io.FileOutputStream;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

/* TODO -> TURN EXCEPTIONS INTO THROWABLES AND THROWABLES INTO EXCPETIONS */
/**
 * PdfExporter
 */
public class PdfExporter {
    private Document document;
    private String outputFileName;
    private com.itextpdf.text.Document pdfDoc;

    /* TODO -> TURN STYLE AND FORMAT NOTATION INTO A MULTIPLE CHECKS */
    private Font getFont(LineBlock l) {
        if(l.getStyle() == StyleEnum.OMITTED) /* OMMITED */
            return null;
        else if(l.getStyle() == StyleEnum.H1)
            /* TODO -> TRY DIFFERENT CLASSES FOR HEADINGS */
            return new Font(FontFamily.HELVETICA, 28);
        else if(l.getStyle() == StyleEnum.H2)
            return new Font(FontFamily.HELVETICA, 20);
        else if(l.getFormat() == FormatEnum.BOLD)
            return new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        else if(l.getFormat() == FormatEnum.ITALICS)
            return new Font(FontFamily.HELVETICA, 12, Font.ITALIC);
        else /* REGULAR */
            return new Font(FontFamily.HELVETICA, 12);
    }

    private int addParagraph(LineBlock l) {
        /* TODO -> DO NOT FORGET TO CHANGE THOSE SETTINGS SOS SOS */
        StringBuilder parBlock = new StringBuilder();

        Font format = getFont(l);
        if(format == null)
            return 0;

        for(String block : l.getLines())
            parBlock.append(block + " ");

        try {
            this.pdfDoc.add(new Paragraph(parBlock.toString(), format));
            this.pdfDoc.add(new Paragraph());
            this.pdfDoc.add(new Phrase("\n"));
        }
        catch(DocumentException e) {
            System.out.println("Error in creating a PDF handler: " + e);
        }

        return 1;
    }

    private int addContent() {
        int par = 0;

        for(LineBlock l : this.document.getLineblocks())
            par += addParagraph(l);

        return par;
    }

    private void createPdfHandler() {
        this.pdfDoc = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(this.pdfDoc, new FileOutputStream(this.outputFileName));
            this.pdfDoc.open();
        }
        catch(DocumentException e) {
            System.out.println("Error in creating a PDF handler: " + e);
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
        int par = 0;
        createPdfHandler();
        par = addContent();
        closePdfHandler();

        return par;
    }
}
