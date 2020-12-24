package client;

/**
 * @interface IMain
 * @brief Interface for defining the basic use cases
 */
public interface IMain {
    /**
     * @message register_rule_set
     * @brief Option for registering a specific ruleSet for the editing
     * @return true if all compiles correctly else exit with an error message
     */
    boolean register_rule_set();

    /**
     * @message load_data
     * @brief Option for loading a file to edit
     * @return true
     */
    boolean load_data();

    /**
     * @message get_report
     * @brief Option for crafting a report with gathered data
     * @return true
     */
    boolean get_report();

    /**
     * @message export_to_filetype
     * @brief Option to export_markdown the edited file to pdf or md
     * @return true if all compiles correctly else exit with an error message
     */
    boolean export_to_filetype();
}
