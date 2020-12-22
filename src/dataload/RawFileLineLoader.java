package dataload;

import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;
import datamodel.files.FileHandler;
import datamodel.files.ReadHandler;

public class RawFileLineLoader implements ILoader {
    private List<String> read_file(String filepath) {
        List<String> sb = new ArrayList<>();
        FileHandler handler = new ReadHandler(filepath);

        String data = handler.read_line();
        while(data != null) {
            sb.add(data);
            data = handler.read_line();
        }

        return sb;
    }

    public RawFileLineLoader() {}

    @Override
    public void load(String filepath, List<LineBlock> line_blocks) {
        List<String> block = new ArrayList<>();
        List<String> sb = read_file(filepath);

        for(int i = 0; i < sb.size(); i++) {
            if(sb.get(i).equals("")) {
                if(sb.get(i-1).equals("")) {
                    block = new ArrayList<>();
                    continue;
                }

                line_blocks.add(new LineBlock(block));
                block = new ArrayList<>();
            }
            else
                block.add(sb.get(i));
        }

        if(block.size() > 0) {
            line_blocks.add(new LineBlock(block));
        }
    }
}
