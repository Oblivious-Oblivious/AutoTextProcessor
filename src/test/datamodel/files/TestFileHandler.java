package test.datamodel.files;

import datamodel.files.FileHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @class: TestFileHandler
 * @desc: Tests for reading and writting files
 */
public class TestFileHandler {
    @Before
    public final void setup_file_structure() {
        File file;
        try {
            file = new File("existing.txt"); 
            FileWriter fw = new FileWriter(file);
            fw.write("data");
            fw.close();
        }
        catch(Exception e) {
            System.out.println("Setup error: `" + e + "`");
        }
    }

    @After
    public final void teardown_file_structure() {
        File file;
        try {
            file = new File("_test_new_file.txt"); 
            file.delete();

            file = new File("new_file.txt");
            file.delete();

            file = new File("existing.txt");
            file.delete();
        }
        catch(Exception e) {
            System.out.println("Teardown error: `" + e + "`");
        }
    }

    @Test
    public final void test_createWriterFD_new() {
        FileHandler handler = new FileHandler("_test_new_file.txt");
        int result = handler.createWriterFD();
        assertEquals(0, result);
    }

    @Test
    public final void test_createWriterFD_existing() {
        FileHandler handler = new FileHandler("existing.txt");
        int result = handler.createWriterFD();
        assertEquals(-1, result);
    }

    @Test
    public final void test_createReaderFD_new() {
        FileHandler handler = new FileHandler("_test_new_file.txt");
        int result = handler.createReaderFD();
        assertEquals(-1, result);
    }

    @Test
    public final void test_createReaderFD_existing() {
        FileHandler handler = new FileHandler("existing.txt");
        int result = handler.createReaderFD();
        assertEquals(0, result);

        handler.closeFD();
    }

    @Test
    public final void test_reading_existing_file() {
        FileHandler handler = new FileHandler("existing.txt");
        handler.createReaderFD();
        assertNotNull(handler.readLineFromFile());
    }

    @Test
    public final void test_writing_to_non_existing_file() {
        FileHandler handler = new FileHandler("new_file.txt");
        handler.createWriterFD();
        assertEquals(0, handler.writeToFile("test data"));
    }

    @Test
    public final void test_that_closing_files_always_succeeds() {
        FileHandler handler = new FileHandler("test");
        assertNotNull(handler.closeFD());
    }
}