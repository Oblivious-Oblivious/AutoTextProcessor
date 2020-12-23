package files;

import datamodel.LineBlock;

/**
 * @class BothWriter
 * @brief A General writer specialized for adding annotations ONLY before the paragraph
 */
public class StartWriter implements IGeneralWriter {
    /**
     * @message write_to_file
     * @brief Implements the writing procedure
     */
    public int write_to_file(String symbol, LineBlock block, WriteHandler writer) {
        writer.write(symbol);
        writer.write_all_lines(block);
        writer.write("\n\n");
        return 1;
    }
}
