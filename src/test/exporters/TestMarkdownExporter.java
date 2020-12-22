package test.exporters;

import static org.junit.Assert.assertEquals;

import dataload.ILoader;
import exporters.IExporter;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.Document;
import exporters.MarkdownExporter;

/**
 * TestMarkdownExporter
 */
public class TestMarkdownExporter {
    private Document getDocument(String filename) {
        Document doc = new Document();
        doc.set_doc_type("RAW");

        /* Load the file */
        ILoader loader = new RawFileLineLoader();
        loader.load(filename, doc.get_line_blocks());

        return doc;
    }

    @Test
    public final void export_hippo_txt() {
        Document doc = getDocument("Resources/SampleDocs/hippocratesOath.txt");
        IExporter ex = new MarkdownExporter(doc, "Resources/Outputs/hippoTxt.md");

        assertEquals(17, ex.export());
    }

    @Test
    public final void export_hippo_html() {
        Document doc = getDocument("Resources/SampleDocs/hippocratesOath.html");
        IExporter ex = new MarkdownExporter(doc, "Resources/Outputs/hippo.md");

        assertEquals(17, ex.export());
    }
}