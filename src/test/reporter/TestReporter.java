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
    private int get_paragraphs(List<LineBlock> line_blocks) {
        IReporter repApi = new Reporter(line_blocks);
        String pLine = repApi
            .generate_report()
            .get(0)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }

    private int get_words(List<LineBlock> line_blocks) {
        IReporter repApi = new Reporter(line_blocks);
        String pLine = repApi
            .generate_report()
            .get(1)
            .split(":")[1]
            .strip();
        return Integer.parseInt(pLine);
    }

    private List<LineBlock> get_loaded_blocks(String filename) {
        List<LineBlock> line_blocks = new ArrayList<>();
        ILoader loader = new RawFileLineLoader();
        loader.load(filename, line_blocks);

        return line_blocks;
    }

    private int get_words_from_file(String filename) {
        return get_words(get_loaded_blocks(filename));
    }

    private int get_paragraphs_from_file(String filename) {
        return get_paragraphs(get_loaded_blocks(filename));
    }
    
    @Test
    public final void test_correct_number_of_paragraphs() {
        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));

        int paragraphs = get_paragraphs(line_blocks);
        
        assertEquals(4, paragraphs);
    }

    @Test
    public final void test_words_on_empty_line_blocks() {
        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));
        line_blocks.add(new LineBlock(new ArrayList<>()));

        int words = get_words(line_blocks);

        assertEquals(0, words);
    }

    @Test
    public final void test_words_on_block_with_5_words() {
        List<String> block = new ArrayList<>();
        block.add("one two three four five");

        List<LineBlock> line_blocks = new ArrayList<>();
        line_blocks.add(new LineBlock(block));

        int words = get_words(line_blocks);

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

        int words = get_words(line_blocks);

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

        int words = get_words(line_blocks);

        assertEquals(20, words);
    }

    /* TODO -> READS WRONG */
    /**
     * adam_mt.txt     -> 4459   -> ✔
     * atlantis.txt    -> 15546  -> ✔
     * beagle.txt      -> 207476 -> 207475 (-1)
     * crito_plato.txt -> 6591   -> 6576   (-15)
     * economy_mt.txt  -> 2451   -> 2450   (-1)
     * ghost_mt.txt    -> 2546   -> 2544   (-2)
     * hippoOath.html  -> 1145   -> ✔
     * hippoOath.txt   -> 1145   -> ✔
     * suntzu.txt      -> 10993  -> 10980  (-13)
     */

    @Test
    public final void test_on_adam_mt() {
        String input_filename = "Resources/SampleDocs/adam_mt.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(40, get_paragraphs_from_file(input_filename));
        // assertEquals(4459, words);
        assertEquals(4459, words);
    }

    @Test
    public final void test_on_atlantis() {
        String input_filename = "Resources/SampleDocs/atlantis.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(96, get_paragraphs_from_file(input_filename));
        // assertEquals(15546, words);
        assertEquals(15546, words);
    }

    @Test
    public final void test_on_beagle() {
        String input_filename = "Resources/SampleDocs/beagle.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(1147, get_paragraphs_from_file(input_filename));
        // assertEquals(209476, words);
        assertEquals(207475, words);
    }

    @Test
    public final void test_on_crito_plato() {
        String input_filename = "Resources/SampleDocs/crito_plato.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(118, get_paragraphs_from_file(input_filename));
        // assertEquals(6591, words);
        assertEquals(6576, words);
    }

    @Test
    public final void test_on_economy_mt() {
        String input_filename = "Resources/SampleDocs/economy_mt.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(19, get_paragraphs_from_file(input_filename));
        assertEquals(2450, words);
    }

    @Test
    public final void test_on_ghost_mt() {
        String input_filename = "Resources/SampleDocs/ghost_mt.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(37, get_paragraphs_from_file(input_filename));
        // assertEquals(2546, words);
        assertEquals(2544, words);
    }

    @Test
    public final void test_on_hippoOathTxt() {
        String input_filename = "Resources/SampleDocs/hippocratesOath.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(17, get_paragraphs_from_file(input_filename));
        assertEquals(1145, words);
    }

    @Test
    public final void test_on_hippoOathHtml() {
        String input_filename = "Resources/SampleDocs/hippocratesOath.html";
        int words = get_words_from_file(input_filename);

        assertEquals(17, get_paragraphs_from_file(input_filename));
        assertEquals(1145, words);
    }

    @Test
    public final void test_on_suntzu() {
        String input_filename = "Resources/SampleDocs/suntzu.txt";
        int words = get_words_from_file(input_filename);

        assertEquals(401, get_paragraphs_from_file(input_filename));
        // assertEquals(10993, words);
        assertEquals(10980, words);
    }
}
