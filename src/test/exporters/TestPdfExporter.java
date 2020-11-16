package test.exporters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.Document.DocumentRawType;
import exporters.PdfExporter;

/**
 * TestPdfExporter
 */
public class TestPdfExporter {
    private Document getDocument(String filename) {
        Document doc = new Document(filename, DocumentRawType.RAW);

        /* Load the file */
        RawFileLineLoader loader = new RawFileLineLoader();
        loader.load(filename, doc.getLineblocks());

        return doc;
    }

    @Test
    public final void export_hippo_txt() {
        Document doc = getDocument("C:\\Users\\roott\\Documents\\C++\\uoi\\java\\Panos\\2020\\project\\Resources\\SampleDocs\\hippocratesOath.txt");
        PdfExporter ex = new PdfExporter(doc, "C:\\Users\\roott\\Documents\\C++\\uoi\\java\\Panos\\2020\\project\\Resources\\Outputs\\hippoTxt.pdf");

        assertEquals(17, ex.export());
    }

    @Test
    public final void export_hippo_html() {
        Document doc = getDocument("C:\\Users\\roott\\Documents\\C++\\uoi\\java\\Panos\\2020\\project\\Resources\\SampleDocs\\hippocratesOath.html");
        PdfExporter ex = new PdfExporter(doc, "C:\\Users\\roott\\Documents\\C++\\uoi\\java\\Panos\\2020\\project\\Resources\\Outputs\\hippo.pdf");

        assertEquals(17, ex.export());
    }
}