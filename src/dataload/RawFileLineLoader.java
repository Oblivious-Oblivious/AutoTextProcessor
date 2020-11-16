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

    // private StringBuilder readFile() {
    private List<String> readFile() {
        // StringBuilder sb = new StringBuilder();
        List<String> sb = new ArrayList<String>();
        FileHandler handler = new FileHandler(this.filePath);

        // int ch;
        // while((ch = handler.readCharacterFromFile()) >= 0)
        //     sb.append((char)ch);

        String data = handler.readLine();
        while(data != null) {
            sb.add(data);
            data = handler.readLine();
        }

        return sb;
    }

    private void setupBlocks() {
        List<String> block = new ArrayList<String>();
        List<String> sb = readFile();
        if(sb == null) 
            return;

        for(int i = 0; i < sb.size(); i++) {
            if(sb.get(i).equals("")) {
                if(sb.get(i-1).equals("")) {
                    block = new ArrayList<String>();
                    continue;
                }

                this.lineblocks.add(new LineBlock(block));
                block = new ArrayList<String>();
            }
            else
                block.add(sb.get(i));
        }

        if(block.size() > 0) {
            this.lineblocks.add(new LineBlock(block));
            block = new ArrayList<String>();
        }
    }

    public void load(String filePath, List<LineBlock> lineblocks) {
        this.filePath = filePath;
        this.lineblocks = lineblocks;
        setupBlocks();
    }
}
