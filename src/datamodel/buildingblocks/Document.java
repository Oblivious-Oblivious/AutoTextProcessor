package datamodel.buildingblocks;

import java.util.List;

public class Document {
    public enum DocumentRawType {
        RAW, ANNOTATED
    }

    public Document(String pFilePath, DocumentRawType docType) {}

    public List<LineBlock> getLineblocks() {
        return null;
    }

    public DocumentRawType getInputFileType() {
        return DocumentRawType.RAW;
    }
}
