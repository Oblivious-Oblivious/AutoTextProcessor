package dataload;

import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;
import datamodel.files.FileHandler;

/* TODO -> IMPLEMENT READ LINE INSTEAD OF READ CHARACTER */

public class RawFileLineLoader {
    private String filePath;
    private List<LineBlock> lineblocks;

    public RawFileLineLoader() {}

    private StringBuilder readFile() {
        StringBuilder sb = new StringBuilder();
        FileHandler handler = new FileHandler(this.filePath);

        handler.createReaderFD();

        int ch;
        while((ch = handler.readCharacterFromFile()) >= 0)
            sb.append((char)ch);

        return sb;
    }

    private void setupBlocks() {
        List<String> block = new ArrayList<String>();
        StringBuilder arr = new StringBuilder();
        StringBuilder sb = readFile();
        
        for(int i = 0; i < sb.length(); i++) {
            if(sb.charAt(i) == '\n') {
                if(arr.length() > 1)
                    // block.add(stuff[i].replaceAll("<[^>]*>", ""));
                    block.add(arr.toString());
                else {
                    this.lineblocks.add(new LineBlock(block));
                    block = new ArrayList<String>();
                }

                arr = new StringBuilder();
            }
            else if(sb.charAt(i) == '\r'); /* Skip carriage returns */
            else
                arr.append(sb.charAt(i));
        }

        block.add(arr.toString());
        this.lineblocks.add(new LineBlock(block));
    }

    public void load(String filePath, List<LineBlock> lineblocks) {
        this.filePath = filePath;
        this.lineblocks = lineblocks;
        setupBlocks();
    }
}
