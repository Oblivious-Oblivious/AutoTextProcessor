package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private DocumentRawType docType;
    // private String pFilePath;
    private List<LineBlock> lineblocks;

    public enum DocumentRawType {
        RAW, ANNOTATED
    }

    public Document(String pFilePath, DocumentRawType docType) {
        // this.pFilePath = pFilePath;
        this.docType = docType;
        this.lineblocks = new ArrayList<LineBlock>();
    }

    public List<LineBlock> getLineblocks() {
        return this.lineblocks;
    }

    public DocumentRawType getInputFileType() {
        return this.docType;
    }

    // public String getPFilePath() {
    //     return this.pFilePath;
    // }
}
