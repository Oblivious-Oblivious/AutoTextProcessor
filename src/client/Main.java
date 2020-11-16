package client;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import engine.Engine;

/**
 * @class: Main
 * @desc: Inteface for communicating with Engine
 * @param pFilePath -> the String with the path of the input file
 * @param pInputType -> the String characterizing the document as raw (DocumentRawType.RAW) unless "ANNOTATED" is given as input, in which case, the input file is characterized as annotated (DocumentRawType.ANNOTATED)
 * @param pAlias -> a String with a short name, i.e., an alias for the file 
 */
public class Main {
    private Engine engineApi;
    private Scanner s = new Scanner(System.in);

    private String pFilePath;
    private String pInputType;
    private String pAlias;

    /**
     * @func: scanInput
     * @desc: Scans an input from the user
     * @param message the message to print before receiving an input
     * @return the line captured from the user
     */
    private String scanInput(String message) {
        System.out.print(message);
        return this.s.nextLine();
    }

    /**
     * @func: intefaceError
     * @desc: Wraps an error statement and returns false for the program to exit
     * @param error -> The type of error produced
     * @return -> false (unchanged)
     */
    private boolean interfaceError(String error) {
        System.err.println(error);
        return false;
    }

    /**
     * @func: createEngineApi
     * @desc: Setup engine variables
     */
    private void createEngineApi() {
        this.pFilePath = scanInput("Input the path of the input document: ");
        this.pInputType = scanInput("Specify whether the document is raw or annotated (RAW|ANNOTATED): ");
        this.pAlias = scanInput("Input an alias for the file: ");
        this.engineApi = new Engine(this.pFilePath, this.pInputType, this.pAlias);
    }

    /** @Constructor **/
    public Main() {
        createEngineApi();
    }
    
    /**
     * @func: getEngineApi
     * @desc: Return the engineApi variable (used for testing)
     * @return
     */
    public Engine getEngineApi() {
        return this.engineApi;
    }

    /**
     * @func: loadData
     * @desc: Option for loading a file to edit
     * @return true
     */
	public boolean loadData() {
        this.engineApi.loadFileAndCharacterizeBlocks();
        return true;
    }
    
    public void addRule(List<List<String>> inputSpec, String inputString) {
        List<String> list = new ArrayList<String>();
        String rule = scanInput(inputString + "\nsyntax -> <\"OMIT\"> <\"ALL_CAPS\"|\"STARTS_WITH\"|\"POSITIONS\"> <(opt. for STARTS_WITH) \"BEGINNING PHRASE\" | (opt. for POSITIONS) \"p1,p2,p3\">\n(press <Enter> for no rule): ");

        if(rule.equals("")) {
            System.out.println("");
            return;
        }

        String ruleArr[] = rule.split(" ");
        list.add(ruleArr[0].replaceAll("^\"+|\"+$", ""));
        list.add(ruleArr[1].replaceAll("^\"+|\"+$", ""));

        if(ruleArr.length > 2)
            list.add(ruleArr[2].replaceAll("^\"+|\"+$", ""));

        inputSpec.add(list);
        System.out.println("");
    }

    /* TODO -> MAKE EXCEPTIONS FOR RULES OF ANNOTATED FILES */
    /* TODO -> INSTEAD OF INTERFACE MAKE USER UPLOAD FILE WITH RULES */
    /* TODO -> INSTEAD OF SPLITTING USE FOR LOOPS WITH STRINGS */
    
    /* TODO -> USE QUOTES ON STARTING PHRASE, OR QUOTES ON ALL RULES */
    /**
     * @func: registerRuleset
     * @desc: Option for registering a specific ruleset for the editing
     * @return true if all compiles correctly else exit with an error message
     */
	public boolean registerRuleset() {
        List<List<String>> inputSpec = new ArrayList<List<String>>();
        addRule(inputSpec, "Define which paragraphs should be ommited");
        addRule(inputSpec, "Define which paragraphs should be H1 headings");
        addRule(inputSpec, "Define which paragraphs should be H2 headings");
        addRule(inputSpec, "Define which paragraphs will have a bold format");
        addRule(inputSpec, "Define which paragraphs will have a italics format");
        

        if(this.pInputType.equalsIgnoreCase("RAW"))
            this.engineApi.registerInputRuleSetForPlainFiles(inputSpec);
        else if(this.pInputType.equalsIgnoreCase("ANNOTATED")) {
            List<String> prefixes = new ArrayList<String>();
            prefixes.add("");
            prefixes.add("");
            prefixes.add("");
            prefixes.add("");
            prefixes.add("");
            /* TODO -> ASK FOR PREFIXES */

            this.engineApi.registerInputRuleSetForAnnotatedFiles(inputSpec, prefixes);
        }
        else
            return interfaceError("WRONG TYPE");
		return true;
    }
    
    /**
     * @func: getReport
     * @desc: Option for crafting a report with gathered data
     * @return true
     */
	public boolean getReport() {
        this.engineApi.reportWithStats();
		return true;
    }
    
    /**
     * @func: exportToFiletype
     * @desc: Option to export the edited file to pdf or md
     * @return true if all compiles correctly else exit with an error message
     */
	public boolean exportToFiletype() {
        String outputType = scanInput("Please input the output type to export to (MD|PDF): ");
        String outputFileName = scanInput("Please input an output file name: ");

        if(outputType.equalsIgnoreCase("MD"))
            this.engineApi.exportMarkDown(outputFileName);
        else if(outputType.equalsIgnoreCase("PDF"))
            this.engineApi.exportPdf(outputFileName);
        else
            return interfaceError("WRONG EXPORT TYPE");
		return true;
	}
}
