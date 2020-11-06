package client;

import java.util.Scanner;

/**
 * @class EntryPoint
 * @desc: Where main function is found
 * @param s -> A scanner object for inputs
 * @param main -> The main class that communicates with the rest of the API
 */
public class EntryPoint {
    private static Scanner s = new Scanner(System.in);
    private static Main main = new Main();

    /**
     * @func: manageExit
     * @desc: Manages the return types and ensures a safe exit
     * @param s the scanner object
     * @param returnType the way the program exits
     * @return the return type of the function
     */
    public static boolean manageExit(Scanner s, boolean returnType) {
        if(returnType == true)
            /* Continue with the menu loop */
            return returnType;

        s.close();
        return returnType;
    }

    /**
     * @func: mainLoop
     * @desc: Implements a main function but called from a loop
     * @return the return type
     */
    private static boolean mainLoop() {
        System.out.println("\n1) Load data.");
        System.out.println("2) Register Ruleset.");
        System.out.println("3) Get report with stats.");
        System.out.println("4) Export to filetype.");
        System.out.println("5) Exit.");

        String arg = s.nextLine();
        System.out.println();
        switch(arg) {
            case "1":
                return manageExit(s, main.loadData());
            case "2":
                return manageExit(s, main.registerRuleset());
            case "3":
                return manageExit(s, main.getReport());
            case "4":
                return manageExit(s, main.exportToFiletype());
            case "5":
                System.out.println("Goodbye.");
                return manageExit(s, false);
            default:
                return manageExit(s, true);
        }
    }

    public static void main(String args[]) {
        while(mainLoop());
    }
}