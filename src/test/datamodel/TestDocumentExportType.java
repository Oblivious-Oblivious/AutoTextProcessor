package test.datamodel;

import datamodel.DocumentExportType;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestDocumentExportType {
    @Test
    public final void test_items() {
        assertNotNull(DocumentExportType.valueOf("MD"));
        assertNotNull(DocumentExportType.valueOf("PDF"));
    }
}
