package dataload;

import datamodel.LineBlock;

import java.util.List;

/**
 * @interface ILoader
 * @brief Defines the basic API for loading files
 */
public interface ILoader {
    /**
     * @message load
     * @brief Handles files and loads to the line_blocks List
     * @param filepath -> The file path to read from
     * @param line_blocks -> The list to fill
     */
    void load(String filepath, List<LineBlock> line_blocks);
}
