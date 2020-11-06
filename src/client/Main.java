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

    String pInputType;

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
        this.engineApi = new Engine();
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
        String pFilePath = scanInput("Input the path of the input document: ");
        this.pInputType = scanInput("Specify whether the document is raw or annotated (RAW|ANNOTATED): ");
        String pAlias = scanInput("Input an alias for the file: ");
        
        // this.engineApi.loadFileAndCharacterizeBlocks();
        this.engineApi.loadFile(pFilePath, pInputType, pAlias);
        return true;
    }
    
    /**
     * @func: registerRuleset
     * @desc: Option for registering a specific ruleset for the editing
     * @return true if all compiles correctly else exit with an error message
     */
	public boolean registerRuleset() {
        List<List<String>> inputSpec = new ArrayList<List<String>>();
        List<String> omList = new ArrayList<String>();
        List<String> h1List = new ArrayList<String>();
        List<String> boldList = new ArrayList<String>();
        inputSpec.add(omList);
        inputSpec.add(h1List);
        inputSpec.add(boldList);
        omList.add("");
        omList.add("");
        omList.add("");
        h1List.add("");
        h1List.add("");
        h1List.add("");
        boldList.add("");
        boldList.add("");
        boldList.add("");
        /* TODO -> ASK FOR INPUTSPEC */

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
