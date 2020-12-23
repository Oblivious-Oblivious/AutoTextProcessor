package files;

import datamodel.LineBlock;

/**
 * @class BothWriter
 * @brief A General writer specialized for adding annotations before AND after the paragraph
 */
public class BothWriter implements IGeneralWriter  {
    /**
     * @message write_to_file
     * @brief Implements the writing procedure
     */
    @Override
    public int write_to_file(String symbol, LineBlock block, WriteHandler writer) {
        writer.write(symbol);
        writer.write_all_lines(block);
        writer.write(symbol);
        writer.write("\n\n");
        return 1;
    }
}