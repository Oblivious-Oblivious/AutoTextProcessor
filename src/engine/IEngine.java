package engine;

/**
 * @interface IEngine
 * @brief Describes an API for the helper engines
 */
public interface IEngine<T> {
    /**
     * @message perform_task
     * @brief Sends this message to start the engine
     * @return A generic object that can be anything according to the engine's role
     */
    T perform_task();
}
