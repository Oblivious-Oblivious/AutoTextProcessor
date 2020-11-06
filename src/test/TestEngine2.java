package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.Document.DocumentRawType;
import datamodel.rules.RuleAllCaps;
import datamodel.ruleset.RuleSet;
import engine.Engine;

/**
 * TestEngine2
 */
public class TestEngine2 {
    private Engine engineApi;

    @Before
    public final void createEngineApi() {
        this.engineApi = new Engine();
    }

    @Test
    public final void test_handleError_output_nullity() {
        String error = "test error message";
        Object result = this.engineApi.handleError(error);
        assertNull(result);
    }

    @Test
    public final void test_loadRawDocument_wrong_filename() {
        String fileName = "_doesnotexistyet.txt";
        int result = engineApi.loadRawDocument(fileName);
        assertEquals(-1, result);
    }

    @Test
    public final void test_loadRawDocument_empty_file() {
        String fileName = "empty_file.txt";
        boolean file_status = false;
        File fd = null;

        try {
            fd = new File(fileName);
            file_status = fd.createNewFile();
        }
        catch(Exception e) {
            System.out.println("File already exists");
            assertTrue("File already exists", false);
            return;
        }

        int result = engineApi.loadRawDocument(fileName);
        assertTrue(file_status);
        assertEquals(0, result);

        fd.delete();
    }

    @Test
    public final void test_loadRawDocument_one_lineblock() {
        assertTrue(false);
    }

    @Test
    public final void test_loadRawDocument_multiple_lineblocks() {
        assertTrue(false);
    }

    @Test
    public final void test_characterizeLineblocks_null_document() {
        Document document = null;
        RuleSet ruleSet = new RuleSet("name", null, null, null, null, null);
        assertFalse(engineApi.characterizeLineblocks(document, ruleSet));
    }
    
    @Test
    public final void test_characterizeLineblocks_null_lineblocks() {
        Document document = null;
        RuleSet ruleSet = new RuleSet("name", null, null, null, null, null);
        assertFalse(engineApi.characterizeLineblocks(document, ruleSet));
    }

    @Test
    public final void test_characterizeLineblocks_null_ruleSet() {
        Document document = new Document("pFilePath", DocumentRawType.RAW);
        RuleSet ruleSet = new RuleSet(null, null, null, null, null, null);
        assertFalse(engineApi.characterizeLineblocks(document, ruleSet));
    }
}