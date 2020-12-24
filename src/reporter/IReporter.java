package reporter;

import java.util.List;

/**
 * @interface IReporter
 * @brief Interface for implementing a generate_report message
 */
public interface IReporter {
    /**
     * @message generate_report
     * @brief Find out statistics about the line_blocks stored
     * @return A list of aggregations
     */
    List<String> generate_report();
}
