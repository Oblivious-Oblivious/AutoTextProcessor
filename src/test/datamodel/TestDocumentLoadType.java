package test.datamodel;

import datamodel.DocumentLoadType;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestDocumentLoadType {
    @Test
    public final void test_items() {
        assertNotNull(DocumentLoadType.valueOf("RAW"));
        assertNotNull(DocumentLoadType.valueOf("ANNOTATED"));
    }
}
