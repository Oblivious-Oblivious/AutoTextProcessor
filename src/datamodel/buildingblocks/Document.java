package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.List;

public class Document {
    public enum DocumentRawType {
        RAW, ANNOTATED
    }

    public Document(String pFilePath, DocumentRawType docType) {}

    public List<LineBlock> getLineblocks() {
        List<LineBlock> lineblocks = new ArrayList<>();
        return lineblocks;
    }

    public DocumentRawType getInputFileType() {
        return DocumentRawType.RAW;
    }
}
