package test.datamodel.buildingblocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import dataload.ILoader;
import org.junit.BeforeClass;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.Document;
import datamodel.LineBlock;
import datamodel.DocumentLoadType;

import files.WriteHandler;

/**
 * TestDocument
 */
public class TestDocument {
    private Document docApi;

    private Document get_raw_api() {
        Document doc = new Document();
        doc.set_doc_type("RAW");

        /* Load the file */
        ILoader loader = new RawFileLineLoader();
        loader.load("test_document_raw.txt", doc.get_line_blocks());

        return doc;
    }

    private Document get_annotated_api() {
        Document doc = new Document();
        doc.set_doc_type("ANNOTATED");

        /* Load the file */
        ILoader loader = new RawFileLineLoader();
        loader.load("test_document_annotated.html", doc.get_line_blocks());

        return doc;
    }

    @BeforeClass
    public static void setup_files() {
        /* Handle files */
        File file;
        file = new File("test_document_raw.txt");
        file.delete();

        file = new File("test_document_annotated.html");
        file.delete();

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        WriteHandler handler = new WriteHandler();
        handler.open("test_document_raw.txt");

        handler.write_line("TITLE AND STUFF\n\nparagraph1\nsub1.1\nsub1.2\n\nparagraph2\nsub2.1\n\nparagraph3\n\nparagraph4 line4\n\n");
        handler.close();

        handler = new WriteHandler();
        handler.open("test_document_annotated.html");
        handler.write_line("<H1>TITLE AND STUFF\n\n<p>paragraph1\nsub1.1\nsub1.2\n\n<p>paragraph2\nsub2.1\n\n<p>paragraph3\n\n<p>paragraph4 line4\n\n");
        handler.close();
    }

    @Test
    public final void test_get_input_filetype_RAW() {
        this.docApi = get_raw_api();
        DocumentLoadType actual = docApi.get_doc_type();
        DocumentLoadType expected = DocumentLoadType.RAW;
        assertEquals(expected, actual);
    }

    @Test
    public final void test_get_input_filetype_ANNOTATED() {
        this.docApi = get_annotated_api();
        DocumentLoadType actual = docApi.get_doc_type();
        DocumentLoadType expected = DocumentLoadType.ANNOTATED;
        assertEquals(expected, actual);
    }

    @Test
    public final void test_get_line_blocks_RAW() {
        this.docApi = get_raw_api();
        List<LineBlock> actual = this.docApi.get_line_blocks();
        assertNotNull(actual);
    }

    @Test
    public final void test_get_line_blocks_ANNOTATED() {
        this.docApi = get_annotated_api();
        List<LineBlock> actual = this.docApi.get_line_blocks();
        assertNotNull(actual);
    }

    @Test
    public final void test_line_blocks_have_at_least_one_element() {
        this.docApi = get_raw_api();
        int expected = this.docApi.get_line_blocks().size();
        int actual = 0;
        assertNotEquals(expected, actual);
    }

    @Test
    public final void test_line_blocks_correct_size() {
        this.docApi = get_raw_api();
        int expected = 5;
        int actual = this.docApi.get_line_blocks().size();
        assertEquals(expected, actual);
    }

    @Test
    public final void test_raw_correct_values() {
        this.docApi = get_raw_api();
        assertEquals(5, docApi.get_line_blocks().size());

        String actual0 = this.docApi.get_line_blocks().get(0).get_lines().get(0);
        String expected0 = "TITLE AND STUFF";
        assertEquals(expected0, actual0);

        String actual1 = this.docApi.get_line_blocks().get(1).get_lines().get(0);
        String expected1 = "paragraph1";
        assertEquals(expected1, actual1);

        String actual11 = this.docApi.get_line_blocks().get(1).get_lines().get(1);
        String expected11 = "sub1.1";
        assertEquals(expected11, actual11);

        String actual12 = this.docApi.get_line_blocks().get(1).get_lines().get(2);
        String expected12 = "sub1.2";
        assertEquals(expected12, actual12);

        String actual2 = this.docApi.get_line_blocks().get(2).get_lines().get(0);
        String expected2 = "paragraph2";
        assertEquals(expected2, actual2);

        String actual21 = this.docApi.get_line_blocks().get(2).get_lines().get(1);
        String expected21 = "sub2.1";
        assertEquals(expected21, actual21);

        String actual3 = this.docApi.get_line_blocks().get(3).get_lines().get(0);
        String expected3 = "paragraph3";
        assertEquals(expected3, actual3);

        String actual4 = this.docApi.get_line_blocks().get(4).get_lines().get(0);
        String expected4 = "paragraph4 line4";
        assertEquals(expected4, actual4);
    }

    @Test
    public final void test_annotated_correct_values() {
        this.docApi = get_annotated_api();
        assertEquals(5, docApi.get_line_blocks().size());

        String actual0 = this.docApi.get_line_blocks().get(0).get_lines().get(0);
        String expected0 = "<H1>TITLE AND STUFF";
        assertEquals(expected0, actual0);

        String actual1 = this.docApi.get_line_blocks().get(1).get_lines().get(0);
        String expected1 = "<p>paragraph1";
        assertEquals(expected1, actual1);

        String actual11 = this.docApi.get_line_blocks().get(1).get_lines().get(1);
        String expected11 = "sub1.1";
        assertEquals(expected11, actual11);

        String actual12 = this.docApi.get_line_blocks().get(1).get_lines().get(2);
        String expected12 = "sub1.2";
        assertEquals(expected12, actual12);

        String actual2 = this.docApi.get_line_blocks().get(2).get_lines().get(0);
        String expected2 = "<p>paragraph2";
        assertEquals(expected2, actual2);

        String actual21 = this.docApi.get_line_blocks().get(2).get_lines().get(1);
        String expected21 = "sub2.1";
        assertEquals(expected21, actual21);

        String actual3 = this.docApi.get_line_blocks().get(3).get_lines().get(0);
        String expected3 = "<p>paragraph3";
        assertEquals(expected3, actual3);

        String actual4 = this.docApi.get_line_blocks().get(4).get_lines().get(0);
        String expected4 = "<p>paragraph4 line4";
        assertEquals(expected4, actual4);
    }
}