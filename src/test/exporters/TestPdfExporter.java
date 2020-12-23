package test.exporters;

import static org.junit.Assert.assertEquals;

import dataload.ILoader;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.Document;
import exporters.PdfExporter;

/**
 * TestPdfExporter
 */
public class TestPdfExporter {
    private Document get_document(String filename) {
        Document doc = new Document();
        doc.set_doc_type("RAW");

        /* Load the file */
        ILoader loader = new RawFileLineLoader();
        loader.load(filename, doc.get_line_blocks());

        return doc;
    }

    @Test
    public final void export_hippo_txt() {
        Document doc = get_document("Resources/SampleDocs/hippocratesOath.txt");
        PdfExporter ex = new PdfExporter(doc, "Resources/Outputs/hippoTxt.pdf");

        assertEquals(17, ex.export());
    }

    @Test
    public final void export_hippo_html() {
        Document doc = get_document("Resources/SampleDocs/hippocratesOath.html");
        PdfExporter ex = new PdfExporter(doc, "Resources/Outputs/hippo.pdf");

        assertEquals(17, ex.export());
    }
}
