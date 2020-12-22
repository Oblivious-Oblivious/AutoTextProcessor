package dataload;

import datamodel.buildingblocks.LineBlock;

import java.util.List;

public interface ILoader {
    void load(String filepath, List<LineBlock> line_blocks);
}
