package dataload;

import java.util.ArrayList;
import java.util.List;

import datamodel.LineBlock;

import files.ReadHandler;

/**
 * @class RawFileLineLoader
 * @brief main Loader controller class for reading values from the input files
 */
public class RawFileLineLoader implements ILoader {
    /**
     * handler -> A read file handler for simplified file I/O
     * arraylist -> A List of Strings that will compose the
     *              paragraph lines before inserted into the LineBlock List
     */
    private final ReadHandler handler;
    private List<String> arraylist;

    /**
     * @message handler
     * @brief Getter for handler
     * @return handler
     */
    private ReadHandler handler() {
        return this.handler;
    }

    /**
     * @message arraylist
     * @brief Getter for arraylist
     * @return arraylist
     */
    private List<String> arraylist() {
        return this.arraylist;
    }
    /**
     * @message arraylist_eq
     * @brief Setter for arraylist
     * @param arraylist -> New value to set
     */
    private void arraylist_eq(List<String> arraylist) {
        this.arraylist = arraylist;
    }

    /**
     * @message process_line
     * @brief Processes a single line and decides on creating the line_blocks
     * @param line -> The line to process
     * @return -> A boolean that will fail when we have end of file
     */
    private boolean process_line(String line, List<LineBlock> line_blocks) {
        /* TODO -> FIX */

        /* EOF */
        if(line == null) {
            if(arraylist().size() != 0)
                line_blocks.add(new LineBlock(arraylist()));
            return false;
        }

        /* New Paragraph */
        else if(line.equals("")) {
            if(arraylist().size() > 0) {
                line_blocks.add(new LineBlock(arraylist()));
                arraylist_eq(new ArrayList<>());
            }
            return true;
        }

        /* Regular line in the middle of a paragraph */
        else {
            arraylist().add(line);
            return true;
        }
    }

    /**
     * @message fill_line_blocks
     * @brief Processes all lines and fills the line_block list object with paragraphs
     */
    private void fill_line_blocks(List<LineBlock> line_blocks) {
        while(process_line(handler().read_line(), line_blocks));
    }

    /** @Constructor **/
    public RawFileLineLoader() {
        this.handler = new ReadHandler();
        this.arraylist = new ArrayList<>();
    }

    /**
     * @message load
     * @brief the main API call that handles files and loads to the line_blocks List
     * @param filepath -> The file path to read from
     * @param line_blocks -> The list to fill
     */
    @Override
    public void load(String filepath, List<LineBlock> line_blocks) {
        if(handler().open(filepath)) {
            fill_line_blocks(line_blocks);
            handler().close();
        }
    }
}
