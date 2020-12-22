package test.engine;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.MainEngine;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.commons.io.FileUtils;

import engine.IMainEngine;


/**
 * Test class to test the back-end, server class MainEngine of the project
 * 
 * Use several profiles for the handling of tested texts
 * 
 * @author pvassil
 * @version 0.1
 * 
 */
public class TestMainEngine {
	private static List<String> omList; 
	private static List<String> h1List; 
	private static List<String> h2List; 
	private static List<String> boldList; 
	private static List<String> italicsList;
	private static List<List<String>> inputSpec;
	private static IMainEngine engine;

	@BeforeClass
	public static void setUpBeforeClass() {
	}

	private static void setupProfileEcon() {
		inputSpec = new ArrayList<>();
		omList = new ArrayList<>(); inputSpec.add(omList);
		omList.add("OMIT");omList.add("POSITIONS");omList.add("1,2");
		h1List = new ArrayList<>(); inputSpec.add(h1List);
		h1List.add("H1");h1List.add("STARTS_WITH"); h1List.add("POLITICAL ECONOMY");
		boldList = new ArrayList<>(); inputSpec.add(boldList);
		boldList.add("<B>");boldList.add("ALL_CAPS");
	}
	
	private static void setupProfileHippo() {
		inputSpec = new ArrayList<>();
		h1List = new ArrayList<>(); inputSpec.add(h1List);
		h1List.add("H1");h1List.add("STARTS_WITH"); h1List.add("OATH AND");
		omList = new ArrayList<>(); inputSpec.add(omList);
		omList.add("OMIT");omList.add("POSITIONS");omList.add("0,3");	
		h2List = new ArrayList<>(); inputSpec.add(h2List);
		h2List.add("H2");h2List.add("ALL_CAPS");
		italicsList = new ArrayList<>(); inputSpec.add(italicsList);
		italicsList.add("<I>");italicsList.add("POSITIONS"); italicsList.add("4,16");
	}
	
	private static void setupProfileHTLMHippo() {
		inputSpec = new ArrayList<>();
		h1List = new ArrayList<>(); inputSpec.add(h1List);
		h1List.add("H1");h1List.add("STARTS_WITH"); h1List.add("<H1>");
		h2List = new ArrayList<>(); inputSpec.add(h2List);
		h2List.add("H2");h2List.add("STARTS_WITH"); h2List.add("<H2>");
		italicsList = new ArrayList<>(); inputSpec.add(italicsList);
		italicsList.add("<I>");italicsList.add("STARTS_WITH"); italicsList.add("<i>");
		boldList = new ArrayList<>(); inputSpec.add(boldList);
		boldList.add("<B>");boldList.add("STARTS_WITH"); boldList.add("<b>");
	}

	@Test
	public final void testLoadProcessWriteMarkupHippo() {
		String inputFileName = "Resources/SampleDocs/hippocratesOath.txt";
		engine = new MainEngine("RAW");
		setupProfileHippo();
		engine.register_input_rule_set_for_plain_files(inputSpec);
		String outputFileName = "Resources//Outputs//hippocratesOath.txt.md";
		
		int inputBlocks = engine.load_file_and_characterize_blocks(inputFileName);
		assertEquals(17,inputBlocks);
		int outputParagraphs = engine.export_markdown(outputFileName);
		assertEquals(15,outputParagraphs); //used to be 17
		
		List<String> report = engine.report_with_stats();
		assertEquals("Total number of paragraphs: 17", report.get(0).strip());
		assertEquals("Total number of words: 1145", report.get(1).strip());
		
		File outputFile = new File("Resources//Outputs//hippocratesOath.txt.md");
		File outputFileRef = new File("Resources//OutputReferences//hippocratesOath.Reference.Setup2.md");
		Boolean localComparison = compareFiles(outputFile, outputFileRef, "testLoadProcessWriteMarkupHippo()");
		assertEquals(true, localComparison);
	}

	@Test
	public final void testLoadProcessWriteMarkupEconomy() {
		String inputFileName = "Resources/SampleDocs/economy_mt.txt";
		engine = new MainEngine("RAW");
		setupProfileEcon();
		engine.register_input_rule_set_for_plain_files(inputSpec);
		String outputFileName = "Resources//Outputs//economy_mt.txt.md";
		
		int inputBlocks = engine.load_file_and_characterize_blocks(inputFileName);
		assertEquals(19,inputBlocks);
		
		int outputParagraphs = engine.export_markdown(outputFileName);//, "economy"
		assertEquals(17,outputParagraphs);			//used to be 19
		File outputFile = new File("Resources//Outputs//economy_mt.txt.md");
		File outputFileRef = new File("Resources//OutputReferences//economy_mt.Reference.Setup1.md");
		Boolean localComparison = compareFiles(outputFile, outputFileRef, "testLoadProcessWriteMarkupEconomy()");
		assertEquals(true, localComparison);
	}

	@Test
	public final void testLoadProcessWritePdfHippo() {
		String inputFileName = "Resources/SampleDocs/hippocratesOath.txt";
		engine = new MainEngine("RAW");
		setupProfileHippo();
		engine.register_input_rule_set_for_plain_files(inputSpec);
		String outputFileName = "Resources//Outputs//hippocratesOath.txt.pdf";

		engine.load_file_and_characterize_blocks(inputFileName);

		int outputParagraphs = engine.export_pdf(outputFileName);
		assertEquals(15,outputParagraphs);
		//does not work: some contents are auto-generated and differ.
		//to find out, must check by reading par. by par.
	}
	
	@Test
	public final void testLoadProcessWritePdfEconomy() {
		String inputFileName = "Resources/SampleDocs/economy_mt.txt";
		engine = new MainEngine("RAW");
		setupProfileEcon();
		engine.register_input_rule_set_for_plain_files(inputSpec);
		String outputFileName = "Resources//Outputs//economy_mt.txt.pdf";

		engine.load_file_and_characterize_blocks(inputFileName);

		int outputParagraphs = engine.export_pdf(outputFileName);
		assertEquals(17,outputParagraphs);
	}

	
	
	@Test
	public final void testLoadProcessWriteMarkupHTMLHippo() {
		String inputFileName = "Resources/SampleDocs/hippocratesOath.html";
		engine = new MainEngine("ANNOTATED");
		setupProfileHTLMHippo();
		List<String> prefixes = new ArrayList<>();
		prefixes.add("<H1>");prefixes.add("<H2>");prefixes.add("<i>");prefixes.add("<b>");prefixes.add("<p>");
		engine.register_input_rule_set_for_annotated_files(inputSpec, prefixes);

		engine.load_file_and_characterize_blocks(inputFileName);

		String outputFileName = "Resources//Outputs//hippocratesOathHtml.md";
		int outputParagraphs = engine.export_markdown(outputFileName);
		assertEquals(17,outputParagraphs);
	}
	
	
	private Boolean compareFiles(File outputFile, File outputFileRef, String caller) {
		boolean localComparison = false;
		try {
			localComparison = FileUtils.contentEquals(outputFile, outputFileRef);
		} catch (IOException e) {
			System.err.println("[TestMainEngine] IO Exception at "+ caller);
			e.printStackTrace();
		}
		return localComparison;
	}//end compareFiles

}//end class