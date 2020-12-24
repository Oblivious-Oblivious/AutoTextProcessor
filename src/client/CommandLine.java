package client;

import java.util.*;
import java.util.stream.Collectors;

import engine.MainEngine;

/**
 * @class CommandLine
 * @brief Interface for communicating with MainEngine
 */
public class CommandLine implements IMain {
    /**
     * main_engine_api -> The MainEngine object to use for implementing the use cases
     * s -> A scanner object for talking to the user
     */
    private MainEngine main_engine_api;
    private final Scanner s = new Scanner(System.in);

    /**
     * @message main_engine_api
     * @brief Getter for main_engine_api
     * @return main_engine_api
     */
    private MainEngine main_engine_api() {
        return this.main_engine_api;
    }

    /**
     * @message create_engine_api
     * @brief Setup engine variables
     */
    private void create_engine_api(String p_input_type) {
        this.main_engine_api = new MainEngine(p_input_type);
    }

    /**
     * @message input
     * @brief Scans an input from the user
     * @param message -> the message to print before receiving an input
     * @return the line captured from the user
     */
    private String input(String message) {
        System.out.print(message);
        return this.s.nextLine();
    }

    /**
     * @message add_rule
     * @brief Gets a rule from the input string, maps the string
     *          into a list of elements and adds to the input_spec
     * @param input_spec -> The list of lists of strings where rules lie
     * @param input_string -> The input prompt for the user to input the rule
     */
    private void add_rule(List<List<String>> input_spec, String input_string) {
        String rule = input(input_string
                + "\nsyntax -> "
                + input_string.split(" ")[5]
                + "<ALL_CAPS|STARTS_WITH|POSITIONS> "
                + "<(opt. for STARTS_WITH) \"BEGINNING PHRASE\" "
                + "| (opt. for POSITIONS) \"p1,p2,p3\">\n"
                + "(press <Enter> for no rule): ");

        List<String> list = Arrays
                .stream(rule
                        .split(" "))
                .map(item -> item
                        .replaceAll("^\"+|\"+$", ""))
                .collect(Collectors
                        .toList());

        /* If the rule is NOT an empty space */
        if(list.size() > 1)
            input_spec.add(list);
        System.out.println();
    }

    /**
     * @message register_raw
     * @brief Execute the engine api call for registering raw files
     * @param input_spec -> The list of rules to pass to the api
     */
    private void register_raw(List<List<String>> input_spec) {
        main_engine_api().register_input_rule_set_for_plain_files(input_spec);
    }

    /**
     * @message register_annotated
     * @brief Get a list of prefixes to replace when
     *          executing the engine api call for registering annotated files
     * @param input_spec -> The list of rules to pass to the api
     */
    private void register_annotated(List<List<String>> input_spec) {
        List<String> prefixes = new ArrayList<>();

        String prefixes_string = input("Input a list of html prefixes separated by a space: ");
        Collections.addAll(prefixes, prefixes_string.split(" "));

        main_engine_api().register_input_rule_set_for_annotated_files(input_spec, prefixes);
    }

    /**
     * @message figure_out_registration
     * @brief Produce the correct message to send according to document type
     * @param input_spec -> The input spec to pass as a parameter
     */
    private void figure_out_registration(List<List<String>> input_spec) {
        String p_input_type = input("Specify the type of the new document (RAW|ANNOTATED): ");
        create_engine_api(p_input_type);

        switch(p_input_type.toUpperCase()) {
            case "RAW" -> register_raw(input_spec);
            case "ANNOTATED" -> register_annotated(input_spec);
            default -> {
                System.out.println("WRONG TYPE");
                figure_out_registration(input_spec);
            }
        }
    }

    /**
     * @message figure_out_export
     * @brief Produce the correct message to send according to output type
     * @param output_type -> The output type gotten from the user
     * @param output_file_name -> The output filename to export at
     */
    private void figure_out_export(String output_type, String output_file_name) {
        switch(output_type.toUpperCase()) {
            case "MD" -> main_engine_api().export_markdown(output_file_name);
            case "PDF" -> main_engine_api().export_pdf(output_file_name);
            default -> System.out.println("WRONG EXPORT TYPE");
        }
    }

    /**
     * @message register_rule_set
     * @brief Option for registering a specific ruleSet for the editing
     * @return true if all compiles correctly else exit with an error message
     */
    @Override
	public boolean register_rule_set() {
        List<List<String>> input_spec = new ArrayList<>();
        add_rule(input_spec, "Define which paragraphs should be OMIT");
        add_rule(input_spec, "Define which paragraphs should be H1");
        add_rule(input_spec, "Define which paragraphs should be H2");
        add_rule(input_spec, "Define which paragraphs should be <B>");
        add_rule(input_spec, "Define which paragraphs should be <I>");

        figure_out_registration(input_spec);
        return true;
    }

    /* TODO -> INSTEAD OF INTERFACE MAKE USER UPLOAD FILE WITH RULES */

    /**
     * @message load_data
     * @brief Option for loading a file to edit
     * @return true
     */
    @Override
	public boolean load_data() {
        String filepath = input("Input the path of the input document: ");

	    return main_engine_api().load_file_and_characterize_blocks(filepath) > 0;
    }
    
    /**
     * @message get_report
     * @brief Option for crafting a report with gathered data
     * @return true
     */
    @Override
	public boolean get_report() {
        main_engine_api().report_with_stats().forEach(System.out::println);
		return true;
    }
    
    /**
     * @message export_to_filetype
     * @brief Option to export_markdown the edited file to pdf or md
     * @return true if all compiles correctly else exit with an error message
     */
    @Override
	public boolean export_to_filetype() {
        String output_type = input("Please input the output type to export_markdown to (MD|PDF): ");
        String output_file_name = input("Please input an output file name: ");

        figure_out_export(output_type, output_file_name);
        return true;
    }
}
