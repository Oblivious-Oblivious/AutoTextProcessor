package test.reporter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.LineBlock;
import reporter.Reporter;

/**
 * TestReporter
 */
public class TestReporter {
    private int getParagraphs(List<LineBlock> lineblocks) {
        Reporter repApi = new Reporter(lineblocks);
        String pLine = repApi
            .generateReport()
            .get(0)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }

    private int getWords(List<LineBlock> lineblocks) {
        Reporter repApi = new Reporter(lineblocks);
        String pLine = repApi
            .generateReport()
            .get(1)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }
    
    @Test
    public final void test_correct_number_of_paragraphs() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));

        int paragraphs = getParagraphs(lineblocks);
        
        assertEquals(4, paragraphs);
    }

    @Test
    public final void test_words_on_empty_lineblocks() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));
        lineblocks.add(new LineBlock(new ArrayList<String>()));

        int words = getWords(lineblocks);

        assertEquals(0, words);
    }

    @Test
    public final void test_words_on_block_with_5_words() {
        List<String> block = new ArrayList<String>();
        block.add("one two three four five");

        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        lineblocks.add(new LineBlock(block));

        int words = getWords(lineblocks);

        assertEquals(5, words);
    }

    @Test
    public final void test_15_words_on_multiple_lineblocks() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();

        List<String> block = new ArrayList<String>();
        block.add("one two three four five");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("six seven eight nine ten");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("eleven twelve thirteen 14 15");
        lineblocks.add(new LineBlock(block));

        int words = getWords(lineblocks);

        assertEquals(15, words);
    }

    @Test
    public final void test_21_words_on_multiple_blocks_multiple_lineblocks() {
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();

        List<String> block = new ArrayList<String>();
        block.add("one two three four five");
        block.add("six seven eight");
        block.add("nine ten");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("eleven twelve");
        block.add("thirteen 14 15");
        lineblocks.add(new LineBlock(block));

        block = new ArrayList<String>();
        block.add("16");
        block.add("seventeen 19 18 20");
        lineblocks.add(new LineBlock(block));

        int words = getWords(lineblocks);

        assertEquals(20, words);
    }

    /* TODO -> READS ONE MORE WORD */
    @Test
    public final void test_words_on_input_file() {
        String inputFileName = "C:\\Users\\roott\\Documents\\C++\\uoi\\java\\Panos\\2020\\project\\Resources\\SampleDocs\\hippocratesOath.txt";
        List<LineBlock> lineblocks = new ArrayList<LineBlock>();
        RawFileLineLoader loader = new RawFileLineLoader();
        loader.load(inputFileName, lineblocks);

        int words = getWords(lineblocks);
        assertEquals(1145, words);
    }
}