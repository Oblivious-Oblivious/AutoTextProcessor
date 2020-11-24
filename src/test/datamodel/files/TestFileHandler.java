package test.datamodel.files;

import datamodel.files.FileHandler;
// import datamodel.files.ReadHandler;
import datamodel.files.WriteHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @class: TestFileHandler
 * @desc: Tests for reading and writting files
 */
public class TestFileHandler {
    @BeforeClass
    public final static void setup_file_structure() {
        File file;
        try {
            file = new File("newfile.txt");
            file.delete();

            file = new File("existing.txt");
            file.delete();

            file = new File("test");
            file.delete();
        }
        catch(Exception e) {
            System.out.println("Teardown error: `" + e + "`");
        }

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

    // @Test
    // public final void test_reading_existing_file() {
    //     FileHandler handler = new ReadHandler("existing.txt");
    //     assertNotNull(handler.readLine());
    // }

    @Test
    public final void test_writing_to_new_file() {
        FileHandler handler = new WriteHandler("newfile.txt");
        assertEquals(0, handler.appendLine("sth"));
    }

    @Test
    public final void test_writing_to_existing_file() {
        FileHandler handler = new WriteHandler("existing.txt");
        assertEquals(0, handler.appendLine("sth"));
    }

    @Test
    public final void test_that_closing_files_always_succeeds() {
        FileHandler handler = new WriteHandler("test");
        assertNotNull(handler.closeFD());
    }
}