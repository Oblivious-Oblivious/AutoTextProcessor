package engine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.Document.DocumentRawType;
import datamodel.ruleset.RuleSet;
import datamodel.ruleset.RuleSetCreator;
import exporters.MarkdownExporter;
import exporters.PdfExporter;
import reporter.Reporter;

public class Engine implements IPlainTextDocumentEngine {
	private String alias; 
	private String filePath = null;
	private String simpleInputFileName = null;
	private List<LineBlock> lineblocks;
	private RuleSet inputRuleSet;
	private List<List<String>> inputSpec = null;
	private List<String> prefixes = null;
	private Document document;

	/**
	 * @func: handleError
	 * @desc: Exit after logging an error the the console
	 * @param err -> The error message
	 * @return null
	 */
	private Object handleError(String err) {
		System.err.println(err);
		return null;
	}

	/**
	 * @func: loadRawDocument
	 * @desc: Loads a filename and stores dat ainto the lineblocks value
	 * @param fileName -> The filename to read
	 * @return -> The size of the lineblocks List
	 */
	private int loadRawDocument(String fileName) {
		RawFileLineLoader loader = new RawFileLineLoader();
		loader.load(this.filePath, this.lineblocks); 
		return lineblocks.size();
	}

	/**
	 * @func: characterizeRawFile
	 * @desc: Set the style and format of the each line in the lineblocks List
	 * @param lineblocks -> The List of lineblocks to modify
	 * @param ruleSet -> The ruleset according to which we characterize
	 */
	private void characterizeRawFile(List<LineBlock> lineblocks, RuleSet ruleSet) {
		for(LineBlock l : lineblocks) {
			l.setStyle(ruleSet.determineHeadingStatus(l));
			l.setFormat(ruleSet.determineFormatStatus(l));
		}
	}

	/**
	 * @func: characterizeAnnotatedFile
	 * @desc: Set style, format and prefix
	 * @param lineblocks -> The List of lineblocks to modify
	 * @param ruleSet -> The ruleset according to which we characterize
	 * @return 0
	 */
	private int characterizeAnnotatedFile(List<LineBlock> lineblocks, RuleSet ruleSet) {
		for(LineBlock l : lineblocks) {
			l.setStyle(ruleSet.determineHeadingStatus(l));
			l.setFormat(ruleSet.determineFormatStatus(l));
            
            for(String prefix : prefixes) {
				if(l.getLines().get(0).startsWith(prefix)) {
					String newString = l.getLines().get(0).replaceFirst(prefix, "");
					l.getLines().set(0, newString);
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * @func: characterizeLineblocks
	 * @desc: Change format and prefixes for the document
	 */
	private void characterizeLineblocks(Document document, RuleSet ruleSet) {
		List<LineBlock> lineblocks = document.getLineblocks();
		Objects.requireNonNull(document);
		Objects.requireNonNull(lineblocks);
		Objects.requireNonNull(ruleSet);
		
		if(document.getInputFileType() == DocumentRawType.RAW)
			characterizeRawFile(lineblocks, ruleSet);
		else if(document.getInputFileType() == DocumentRawType.ANNOTATED)
			characterizeAnnotatedFile(lineblocks, ruleSet);
		else {
			System.err.println("   WRONG FILE TYPE !!!");
			System.exit(-100);
		}
	}
	
	/**
	 * @Constructor
	 * @desc: Constructs a new engine object to serve a specific input file
	 * 		  The document, lineblocks, alias, filePath and simpleInputFileName attributes are all populated.
	 * @param pFilePath the String with the path of the input file
	 * @param pInputType the String characterizing the document as raw (DocumentRawType.RAW) unless "ANNOTATED" is given as input, in which case, the input file is characterized as annotated (DocumentRawType.ANNOTATED)
	 * @param pAlias a String with a short name, i.e., an alias for the file
	 */
	public Engine(String pFilePath, String pInputType, String pAlias) {
		Document.DocumentRawType docType = DocumentRawType.RAW;
		if(pInputType.equalsIgnoreCase("ANNOTATED"))
			docType = DocumentRawType.ANNOTATED;

		this.document = new Document(pFilePath, docType);
		this.lineblocks = this.document.getLineblocks();
		this.alias = pAlias;
		this.filePath = pFilePath;
		Path p = Paths.get(this.filePath);
		this.simpleInputFileName = p.getFileName().toString();
	}
	
	/**
	 * @func: registerInputRuleSetForPlainFiles
	 * @desc: Registers a global rule set for a plain file at the main engine
	 * 		  Returns a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 * 		  The inputSpec parameter representing the specification of the rules is a List of (List of String)
	 * 		  Each category of rule (OMIT, H1, H2, <B>, <I>) has a dedicated (List of String). 
	 *   	  Every category absent is setup to undefined status and eventually mapped to the resp. RuleUndefined object that always returns false for isValid()
	 * 		  The 0th element is the aforementioned category representative string
	 * 		  The 1st element is STARTS_WITH, POSITIONS, ALL_CAPS 
	 * 		  The 3rd element is a prefix string for STARTS_WITH or a comma-separated string of positions (starting from 0) for POSITIONS
	 * @param inputSpec a List of (List of String) with the specification of the rules on how to handle paragraphs
	 * @return a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 */
	@Override
	public RuleSet registerInputRuleSetForPlainFiles(List<List<String>> inputSpec) {
		this.inputSpec = inputSpec;
		RuleSetCreator ruleSetCreator = new RuleSetCreator(lineblocks, inputSpec, "inputRuleSet");
		this.inputRuleSet =  ruleSetCreator.createRuleSet();
		
		return this.inputRuleSet;
	}

	/**
	 * Registers a global rule set for an annotated  file at the main engine
	 * 
	 * Returns a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 * The inputSpec parameter representing the specification of the rules is a List of (List of String)
	 * Each category of rule (OMIT, H1, H2, <B>, <I>) has a dedicated (List of String). 
	 * Every category absent is setup to undefined status and eventually mapped to the resp. RuleUndefined object that always returns false for isValid()
	 * The 0th element is the aforementioned category representative string
	 * The 1st element _must_ always be STARTS_WITH  
	 * The 3rd element is a prefix string for STARTS_WITH or a comma-separated string of positions (starting from 0) for POSITIONS
	 * 
	 * The prefixes parameter represents the List<String> to report on the marks at the beginning of each paragraph.
	 * 
	 *  
	 * @param inputSpec a List of (List of String) with the specification of the rules on how to handle paragraphs
	 * @param prefixes a List of Strings as the prefixes for the annotated paragraphs of the file
	 * @return a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 */
	@Override
	public RuleSet registerInputRuleSetForAnnotatedFiles(List<List<String>> inputSpec, List<String> prefixes) {
		this.inputSpec = inputSpec;
		this.prefixes = prefixes;
		
		if(this.inputSpec == null)
			return null;
		
		for(List<String> l : this.inputSpec)
			if(l.size() != 3 || !l.get(1).strip().toUpperCase().equals("STARTS_WITH"))
				return (RuleSet)handleError("Error in annotation spec");
			// prefixes.add(l.get(2));
	
		RuleSetCreator ruleSetCreator = new RuleSetCreator(lineblocks, inputSpec, "inputRuleSet");
		this.inputRuleSet = ruleSetCreator.createRuleSet();
		
		return this.inputRuleSet;
	}
		
	/**
	 * @func: loadFileAndCharacterizeBlocks
	 * @desc: Takes the input file specified at the constructor, loads it and processes it according to the rule set specified at the constructor
	 * 		  The blocks of the file are represented in a List in main memory, as the this.lineblocks attribute.
	 * @return the number of LineBlocks that were identified and represented in-memory from the input file
	 */
	@Override
	public int loadFileAndCharacterizeBlocks() {
		if(this.lineblocks.size() == 0)
			loadRawDocument(this.filePath);
		System.out.println(this.filePath);		
		
		if((this.lineblocks != null) && (this.inputRuleSet != null))
			characterizeLineblocks(this.document, this.inputRuleSet);
		
		return this.lineblocks.size();
	}

	/**
	 * @func: reportWithStats
	 * @desc: Outputs a List<String> to be used as a report on the number of paragraphs and words of a file.
	 * 		  If the input file has not been previously loaded and processed, the method does so.
	 * 		  Then, it creates a List<String> with the following elements:
	 * 		  the 0th element reporting on the total number of paragraphs
	 * 		  the 1st element re porting on the total number of words
	 * 		  each subsequent element reporting on the number of words of each paragraph
	 * @return the List<String> with the report's elements
	 */
	@Override
	public List<String> reportWithStats() {
		int numParagraphs = this.lineblocks.size();

        if(numParagraphs == 0)
			loadFileAndCharacterizeBlocks();

		Reporter reporter = new Reporter(this.lineblocks);
		return reporter.generateReport();
	}

	/**
	 * @func: exportMarkDown
	 * @desc: Exports the input file of the constructor as the MarkDown file at the path specified by outputFileName
	 * 		  If the input files has not been processed, and the this.lineblocks attribute has a size of 0, the method loads and characterizes the input
	 * @param outputFileName the path where the exported MarkDown file will be written
	 * @return the number of LineBlocks exported in the output file
	 */
	@Override
	public int exportMarkDown(String outputFileName) {
		/*
		 * if(this.lineblocks.size() == 0) loadRawDocument(this.filePath);
		 * System.out.println("Loaded: " + this.filePath + " to generate " +
		 * outputFileName); // for(LineBlock l: lineblocks) {
		 * //System.out.println(l.toString()); // }
		 * 
		 * if((this.lineblocks !=null) && (this.inputRuleSet!= null))
		 * characterizeLineblocks(this.document, this.inputRuleSet);
		 */
		if(this.lineblocks.size() == 0)
			loadFileAndCharacterizeBlocks();
		
		MarkdownExporter exporter = new MarkdownExporter(this.document, outputFileName);
		int outputNumParagraphs = exporter.export();
		System.out.println("[Engine.loadProcessMarkup] [file: " + simpleInputFileName + "] exported as " + outputFileName +"\n Input #pars: " + this.lineblocks.size() + " Output #pars: " + outputNumParagraphs);
		
		return outputNumParagraphs;
	}

	/**
	 * @func: exportPdf
	 * @desc: Exports the input file of the constructor as the pdf file at the path specified by outputFileName
	 * 		  If the input files has not been processed, and the this.lineblocks attribute has a size of 0, the method loads and characterizes the input
	 * @param outputFileName the path where the exported pdf file will be written
	 * @return the number of LineBlocks exported in the output file
	 */
	@Override
	public int exportPdf(String outputFileName) {
		if(this.lineblocks.size() == 0)
			loadFileAndCharacterizeBlocks();

		PdfExporter exporter = new PdfExporter(this.document, outputFileName);
		int outputNumParagraphs = exporter.export();
        System.out.println("[Engine.loadProcessPdf] [file: " + simpleInputFileName + "] Input #pars: " + this.lineblocks.size() + " Output #pars: " + outputNumParagraphs);
        
		return outputNumParagraphs;
	}
}
