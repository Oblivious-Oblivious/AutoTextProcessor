package files;

import datamodel.LineBlock;

/**
 * @interface IGeneralWriter
 * @brief A Writer Role for appending to line_blocks
 *          gets implemented by StartWriter and BothWriter
 */
public interface IGeneralWriter {
    /**
     * @message write_to_file
     * @brief Writes a symbol into a line_block using the writer object
     * @param symbol -> The symbol to add to both beginning and end
     * @param block -> The block to add to
     * @param writer -> The handler that performs the appending
     * @return 1 if the paragraph was written so we can count it
     */
    int write_to_file(String symbol, LineBlock block, WriteHandler writer);
}
