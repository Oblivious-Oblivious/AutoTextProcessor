package test.datamodel.buildingblocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.Document.DocumentRawType;
import datamodel.files.FileHandler;
import datamodel.files.WriteHandler;

/**
 * TestDocument
 */
public class TestDocument {
    private Document docApi;

    private Document get_raw_api() {
        Document doc = new Document("test_document_raw.txt", DocumentRawType.RAW);

        /* Load the file */
        RawFileLineLoader loader = new RawFileLineLoader();
        loader.load("test_document_raw.txt", doc.getLineblocks());

        return doc;
    }

    private Document get_annotated_api() {
        Document doc = new Document("test_document_annotated.html", DocumentRawType.ANNOTATED);

        /* Load the file */
        RawFileLineLoader loader = new RawFileLineLoader();
        loader.load("test_document_annotated.html", doc.getLineblocks());

        return doc;
    }

    @BeforeClass
    public final static void setup_files() {
        /* Handle files */
        File file;
        try {
            file = new File("test_document_raw.txt"); 
            file.delete();

            file = new File("test_document_annotated.html");
            file.delete();
        }
        catch(Exception e) {
            System.out.println("Error in deleting: `" + e + "`");
        }

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        FileHandler handler = new WriteHandler("test_document_raw.txt");
        handler.appendLine("TITLE AND STUFF\n\nparagraph1\nsub1.1\nsub1.2\n\nparagraph2\nsub2.1\n\nparagraph3\n\nparagraph4 line4\n\n");
        handler.closeFD();

        handler = new WriteHandler("test_document_annotated.html");
        handler.appendLine("<H1>TITLE AND STUFF\n\n<p>paragraph1\nsub1.1\nsub1.2\n\n<p>paragraph2\nsub2.1\n\n<p>paragraph3\n\n<p>paragraph4 line4\n\n");
        handler.closeFD();
    }

    @Test
    public final void test_getInputFileType_RAW() {
        this.docApi = get_raw_api();
        DocumentRawType actual = docApi.getInputFileType();
        DocumentRawType expected = DocumentRawType.RAW;
        assertEquals(expected, actual);
    }

    @Test
    public final void test_getInputFileType_ANNOTATED() {
        this.docApi = get_annotated_api();
        DocumentRawType actual = docApi.getInputFileType();
        DocumentRawType expected = DocumentRawType.ANNOTATED;
        assertEquals(expected, actual);
    }

    @Test
    public final void test_getLineBlocks_RAW() {
        this.docApi = get_raw_api();
        List<LineBlock> actual = this.docApi.getLineblocks();
        assertNotNull(actual);
    }

    @Test
    public final void test_getLineBlocks_ANNOTATED() {
        this.docApi = get_annotated_api();
        List<LineBlock> actual = this.docApi.getLineblocks();
        assertNotNull(actual);
    }

    @Test
    public final void test_lineblocks_have_at_least_one_element() {
        this.docApi = get_raw_api();
        int expected = this.docApi.getLineblocks().size();
        int actual = 0;
        assertNotEquals(expected, actual);
    }

    @Test
    public final void test_lineblocks_correct_size() {
        this.docApi = get_raw_api();
        int expected = 5;
        int actual = this.docApi.getLineblocks().size();
        assertEquals(expected, actual);
    }

    @Test
    public final void test_raw_correct_values() {
        this.docApi = get_raw_api();
        assertEquals(5, docApi.getLineblocks().size());

        String actual0 = this.docApi.getLineblocks().get(0).getLines().get(0);
        String expected0 = "TITLE AND STUFF";
        assertEquals(expected0, actual0);

        String actual1 = this.docApi.getLineblocks().get(1).getLines().get(0);
        String expected1 = "paragraph1";
        assertEquals(expected1, actual1);

        String actual11 = this.docApi.getLineblocks().get(1).getLines().get(1);
        String expected11 = "sub1.1";
        assertEquals(expected11, actual11);

        String actual12 = this.docApi.getLineblocks().get(1).getLines().get(2);
        String expected12 = "sub1.2";
        assertEquals(expected12, actual12);

        String actual2 = this.docApi.getLineblocks().get(2).getLines().get(0);
        String expected2 = "paragraph2";
        assertEquals(expected2, actual2);

        String actual21 = this.docApi.getLineblocks().get(2).getLines().get(1);
        String expected21 = "sub2.1";
        assertEquals(expected21, actual21);

        String actual3 = this.docApi.getLineblocks().get(3).getLines().get(0);
        String expected3 = "paragraph3";
        assertEquals(expected3, actual3);

        String actual4 = this.docApi.getLineblocks().get(4).getLines().get(0);
        String expected4 = "paragraph4 line4";
        assertEquals(expected4, actual4);
    }

    @Test
    public final void test_annotated_correct_values() {
        this.docApi = get_annotated_api();
        assertEquals(5, docApi.getLineblocks().size());

        String actual0 = this.docApi.getLineblocks().get(0).getLines().get(0);
        String expected0 = "<H1>TITLE AND STUFF";
        assertEquals(expected0, actual0);

        String actual1 = this.docApi.getLineblocks().get(1).getLines().get(0);
        String expected1 = "<p>paragraph1";
        assertEquals(expected1, actual1);

        String actual11 = this.docApi.getLineblocks().get(1).getLines().get(1);
        String expected11 = "sub1.1";
        assertEquals(expected11, actual11);

        String actual12 = this.docApi.getLineblocks().get(1).getLines().get(2);
        String expected12 = "sub1.2";
        assertEquals(expected12, actual12);

        String actual2 = this.docApi.getLineblocks().get(2).getLines().get(0);
        String expected2 = "<p>paragraph2";
        assertEquals(expected2, actual2);

        String actual21 = this.docApi.getLineblocks().get(2).getLines().get(1);
        String expected21 = "sub2.1";
        assertEquals(expected21, actual21);

        String actual3 = this.docApi.getLineblocks().get(3).getLines().get(0);
        String expected3 = "<p>paragraph3";
        assertEquals(expected3, actual3);

        String actual4 = this.docApi.getLineblocks().get(4).getLines().get(0);
        String expected4 = "<p>paragraph4 line4";
        assertEquals(expected4, actual4);
    }
}