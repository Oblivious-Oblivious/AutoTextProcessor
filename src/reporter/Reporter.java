package reporter;

import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;

/**
 * Reporter
 */
public class Reporter {
    private List<LineBlock> lineblocks;
    private List<String> report;

    private int calculateWordsAndSetupStats(int numWords) {
        for(LineBlock lineblock : this.lineblocks) {
			this.report.add("\n" + lineblock.getStatsAsString());
			numWords += lineblock.getWords();
        }

        return numWords;
    }
    
    private int setupNumWords() {
        int numWords = 0;
        this.report.add("\n"+ "Total number of words: " + numWords);

        return calculateWordsAndSetupStats(numWords);
    }

    public Reporter(List<LineBlock> lineblocks) {
        this.lineblocks = lineblocks;
        this.report = new ArrayList<String>();
    }

    public List<String> generateReport() {
        int numParagraphs = this.lineblocks.size();
        this.report.add("\n"+ "Total number of paragraphs: " + numParagraphs);

        int numWords = setupNumWords();
        this.report.set(1, "\nTotal number of words: " + numWords);

        return this.report;
    }
}