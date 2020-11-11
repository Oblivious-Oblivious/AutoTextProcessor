package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.List;

import datamodel.files.FileHandler;


public class Document {
    private DocumentRawType docType;
    private String pFilePath;
    private List<LineBlock> lineblocks;

    public enum DocumentRawType {
        RAW, ANNOTATED
    }

    private StringBuilder readFile() {
        StringBuilder sb = new StringBuilder();
        FileHandler handler = new FileHandler(this.pFilePath);

        handler.createReaderFD();

        int ch;
        while((ch = handler.readCharacterFromFile()) >= 0)
            sb.append((char)ch);

        return sb;
    }

    private void setupRawBlocks() {
        List<String> block = new ArrayList<String>();
        StringBuilder arr = new StringBuilder();
        StringBuilder sb = readFile();
        
        for(int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '\n') {
                if(arr.length() > 1)
                    block.add(arr.toString());
                else {
                    this.lineblocks.add(new LineBlock(block, "STH"));
                    block = new ArrayList<String>();
                }

                arr = new StringBuilder();
            }
            else
                arr.append(sb.charAt(i));
        }

        block.add(arr.toString());
        this.lineblocks.add(new LineBlock(block, "STH"));
    }

    private void setupAnnotatedBlocks() {
        this.lineblocks.add(new LineBlock(new ArrayList<String>(), "STH2"));
        this.lineblocks.add(new LineBlock(new ArrayList<String>(), "STH2"));
        this.lineblocks.add(new LineBlock(new ArrayList<String>(), "STH2"));
        this.lineblocks.add(new LineBlock(new ArrayList<String>(), "STH2"));
        this.lineblocks.add(new LineBlock(new ArrayList<String>(), "STH2"));

        // TODO -> COMPLETE
        // List<String> block = new ArrayList<String>();
        // StringBuilder arr = new StringBuilder();
        // StringBuilder sb = readFile();
        
        // for(int i = 0; i < sb.length(); i++) {
        //     if(sb.charAt(i) == '<') {
        //         if(arr.length() > 1)
        //             block.add(arr.toString());
        //         else {
        //             lineblocks.add(new LineBlock(block, "STH"));
        //             block = new ArrayList<String>();
        //         }

        //         arr = new StringBuilder();
        //     }
        //     else
        //         arr.append(sb.charAt(i));
        // }

        // block.add(arr.toString());
        // lineblocks.add(new LineBlock(block, "STH"));
    }

    public Document(String pFilePath, DocumentRawType docType) {
        this.pFilePath = pFilePath;
        this.docType = docType;
        this.lineblocks = new ArrayList<LineBlock>();

        if(this.docType == DocumentRawType.RAW)
            setupRawBlocks();
        else
            setupAnnotatedBlocks();
    }

    public List<LineBlock> getLineblocks() {
        return this.lineblocks;
    }

    public DocumentRawType getInputFileType() {
        return this.docType;
    }
}
