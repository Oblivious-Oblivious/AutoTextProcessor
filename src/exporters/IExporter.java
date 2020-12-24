package exporters;

/**
 * @interface IExporter
 * @brief Interface for the exporter classes
 *        Each class should at least have an api for an export message
 */
public interface IExporter {
    /**
     * @message export
     * @brief Exports paragraphs to an external file system according to type
     * @return The number of total paragraphs exported
     */
    int export();
}
