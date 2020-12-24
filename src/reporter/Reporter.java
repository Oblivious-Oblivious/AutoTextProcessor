package reporter;

import java.util.ArrayList;
import java.util.List;

import datamodel.LineBlock;

/**
 * @class Reporter
 * @brief Controller class implementing the IReporter api for generating a report
 */
public class Reporter implements IReporter {
    /**
     * line_blocks -> The line_blocks from which to gather data
     * report -> The report list of strings with aggregations about the line_blocks
     */
    private final List<LineBlock> line_blocks;
    private final List<String> report;

    /**
     * @message calculate_words_and_setup_stats
     * @brief Add each stats lines to the report
     * @return The total number of words counted for the report
     */
    private int calculate_words_and_setup_stats() {
        return this.line_blocks
                    .stream()
                    .mapToInt(block -> {
                        this.report.add("\n" + block.get_stats_as_string());
                        return block.words();
                    })
                    .sum();
    }

    /**
     * @message setup_num_words
     * @brief Adds a dummy value for the total number of words
     *        and gets the actual number after counting each line
     * @return The total number of words
     */
    private int setup_num_words() {
        this.report.add("\n"+ "Total number of words: " + 0);
        return calculate_words_and_setup_stats();
    }

    /** @Constructor **/
    public Reporter(List<LineBlock> line_blocks) {
        this.line_blocks = line_blocks;
        this.report = new ArrayList<>();
    }

    /**
     * @message generate_report
     * @brief Creates a report of the number of paragraphs and words read from the file
     * @return The newly created report (list of strings)
     */
    public List<String> generate_report() {
        this.report.add("\n"+ "Total number of paragraphs: " + this.line_blocks.size());
        this.report.set(1, "\nTotal number of words: " + setup_num_words());
        return this.report;
    }
}