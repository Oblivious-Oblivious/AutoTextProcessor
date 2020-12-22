package client;

import java.util.Scanner; /* nextLine */

/**
 * @class EntryPoint
 * @brief Where main function is found
 */
public class EntryPoint {
    /**
     * s -> A scanner object for inputs
     * main -> The main class that communicates with the rest of the API
     */
    private static final Scanner s = new Scanner(System.in);
    private static final IMain main = new CommandLine();

    /**
     * @message manage_exit
     * @brief Manages the return types and ensures a safe exit closing `s`
     * @param return_type -> the way the program exits
     * @return the return type of the function
     */
    private static boolean manage_exit(boolean return_type) {
        if(!return_type)
            s.close();
        return return_type;
    }

    /**
     * @message scan_argument_and_switch
     * @brief Switch on the argument read and send the appropriate message
     * @return true on successful execution, false on exit or error
     */
    private static boolean scan_argument_and_switch() {
        boolean ret_value = true;

        String arg = s.nextLine();
        switch(arg) {
            case "1" -> ret_value = manage_exit(main.register_rule_set());
            case "2" -> ret_value = manage_exit(main.load_data());
            case "3" -> ret_value = manage_exit(main.get_report());
            case "4" -> ret_value = manage_exit(main.export_to_filetype());
            case "5" -> ret_value = manage_exit(false);
            default  -> manage_exit(true);
        }

        return ret_value;
    }

    /**
     * @message main_loop
     * @brief Implements a main function but called from a loop
     * @return the return type
     */
    private static boolean main_loop() {
        System.out.println("\n1) Register RuleSet.");
        System.out.println("2) Load data.");
        System.out.println("3) Get report with stats.");
        System.out.println("4) Export to filetype.");
        System.out.println("5) Exit.");
        System.out.print("$> ");

        return scan_argument_and_switch();
    }

    public static void main(String[] args) {
        while(main_loop());
    }
}
