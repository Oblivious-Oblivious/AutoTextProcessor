package dataload;

import datamodel.LineBlock;

import java.util.List;

public interface ILoader {
    void load(String filepath, List<LineBlock> line_blocks);
}
