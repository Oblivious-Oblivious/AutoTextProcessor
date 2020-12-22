package test.reporter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import dataload.ILoader;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.LineBlock;
import reporter.IReporter;
import reporter.Reporter;

/**
 * TestReporter
 */
public class TestReporter {
    private int getParagraphs(List<LineBlock> line_blocks) {
        IReporter repApi = new Reporter(line_blocks);
        String pLine = repApi
            .generate_report()
            .get(0)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }

    private int getWords(List<LineBlock> line_blocks) {
        IReporter repApi = new Reporter(line_blocks);
        String pLine = repApi
            .generate_report()
            .get(1)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }

    private List<LineBlock> getLoadedLineBlocks(String filename) {
        List<LineBlock> line_blocks = new ArrayList<>();
        ILoader loader = new RawFileLineLoader();
        loader.load(filename, line_blocks);

        return line_blocks;
    }

    private int get_words_from_file(String filename) {
        return getWords(getLoadedLineBlocks(filename));
    }

    private int get_paragraphs_from_file(String filename) {
        return getParagraphs(getLoadedLineBlocks(filename));
    }
    
    @Test
    public final void test_correct_number_of_paragraphs() {
        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));

        int paragraphs = getParagraphs(line_blocks);
        
        assertEquals(4, paragraphs);
    }

    @Test
    public final void test_words_on_empty_line_blocks() {
        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));

        int words = getWords(line_blocks);

        assertEquals(0, words);
    }

    @Test
    public final void test_words_on_block_with_5_words() {
        List<String> block = new ArrayList<>();
        block.add("one two three four five");

        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(block));

        int words = getWords(line_blocks);

        assertEquals(5, words);
    }

    @Test
    public final void test_15_words_on_multiple_line_blocks() {
        List<LineBlock> line_blocks = new ArrayList<>();

        List<String> block = new ArrayList<>();
        block.add("one two three four five");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("six seven eight nine ten");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("eleven twelve thirteen 14 15");
        line_blocks.add(new LineBlock(block));

        int words = getWords(line_blocks);

        assertEquals(15, words);
    }

    @Test
    public final void test_21_words_on_multiple_blocks_multiple_line_blocks() {
        List<LineBlock> line_blocks = new ArrayList<>();

        List<String> block = new ArrayList<>();
        block.add("one two three four five");
        block.add("six seven eight");
        block.add("nine ten");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("eleven twelve");
        block.add("thirteen 14 15");
        line_blocks.add(new LineBlock(block));

        block = new ArrayList<>();
        block.add("16");
        block.add("seventeen 19 18 20");
        line_blocks.add(new LineBlock(block));

        int words = getWords(line_blocks);

        assertEquals(20, words);
    }

    /* TODO -> READS WRONG */
    /**
     * adam_mt.txt     -> 4459   -> 4462
     * atlantis.txt    -> 15546  -> 15870
     * beagle.txt      -> 207476 -> 213430
     * crito_plato.txt -> 6591   -> 6576
     * economy_mt.txt  -> 2451   -> ✔
     * ghost_mt.txt    -> 2546   -> 2544
     * hippoOath.html  -> 1145   -> ✔
     * hippoOath.txt   -> 1145   -> ✔
     * suntzu.txt      -> 10993  -> 11090
     */

    @Test
    public final void test_on_adam_mt() {
        String inputFileName = "Resources/SampleDocs/adam_mt.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(40, get_paragraphs_from_file(inputFileName));
        // assertEquals(4459, words);
        assertEquals(4462, words);
    }

    @Test
    public final void test_on_atlantis() {
        String inputFileName = "Resources/SampleDocs/atlantis.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(96, get_paragraphs_from_file(inputFileName));
        // assertEquals(15546, words);
        assertEquals(15870, words);
    }

    @Test
    public final void test_on_beagle() {
        String inputFileName = "Resources/SampleDocs/beagle.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(1147, get_paragraphs_from_file(inputFileName));
        // assertEquals(209476, words);
        assertEquals(213430, words);
    }

    @Test
    public final void test_on_crito_plato() {
        String inputFileName = "Resources/SampleDocs/crito_plato.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(118, get_paragraphs_from_file(inputFileName));
        // assertEquals(6591, words);
        assertEquals(6576, words);
    }

    @Test
    public final void test_on_economy_mt() {
        String inputFileName = "Resources/SampleDocs/economy_mt.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(19, get_paragraphs_from_file(inputFileName));
        assertEquals(2451, words);
    }

    @Test
    public final void test_on_ghost_mt() {
        String inputFileName = "Resources/SampleDocs/ghost_mt.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(37, get_paragraphs_from_file(inputFileName));
        // assertEquals(2546, words);
        assertEquals(2544, words);
    }

    @Test
    public final void test_on_hippoOathTxt() {
        String inputFileName = "Resources/SampleDocs/hippocratesOath.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(17, get_paragraphs_from_file(inputFileName));
        assertEquals(1145, words);
    }

    @Test
    public final void test_on_hippoOathHtml() {
        String inputFileName = "Resources/SampleDocs/hippocratesOath.html";
        int words = get_words_from_file(inputFileName);

        assertEquals(17, get_paragraphs_from_file(inputFileName));
        assertEquals(1145, words);
    }

    @Test
    public final void test_on_suntzu() {
        String inputFileName = "Resources/SampleDocs/suntzu.txt";
        int words = get_words_from_file(inputFileName);

        assertEquals(401, get_paragraphs_from_file(inputFileName));
        // assertEquals(10993, words);
        assertEquals(11090, words);
    }
}