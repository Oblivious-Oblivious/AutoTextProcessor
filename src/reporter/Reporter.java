package reporter;

import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;

/**
 * Reporter
 */
public class Reporter implements IReporter {
    private final List<LineBlock> line_blocks;
    private final List<String> report;

    private int calculate_words_and_setup_stats(int num_words) {
        for(LineBlock lineblock : this.line_blocks) {
			this.report.add("\n" + lineblock.get_stats_as_string());
			num_words += lineblock.get_words();
        }

        return num_words;
    }
    
    private int setup_num_words() {
        int num_words = 0;
        this.report.add("\n"+ "Total number of words: " + num_words);

        return calculate_words_and_setup_stats(num_words);
    }

    public Reporter(List<LineBlock> line_blocks) {
        this.line_blocks = line_blocks;
        this.report = new ArrayList<>();
    }

    public List<String> generate_report() {
        int numParagraphs = this.line_blocks.size();
        this.report.add("\n"+ "Total number of paragraphs: " + numParagraphs);

        int num_words = setup_num_words();
        this.report.set(1, "\nTotal number of words: " + num_words);

        return this.report;
    }
}