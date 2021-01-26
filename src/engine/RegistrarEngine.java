package engine;

import datamodel.Document;
import rules.RuleSetCreator;

import java.util.List;

/**
 * @class RegistrarEngine
 * @brief A helper controller for performing the registration tasks
 */
public class RegistrarEngine implements IEngine<Void> {
    /**
     * document -> The main document where information is stored
     */
    private final Document document;

    /**
     * @message document
     * @brief Getter for document
     * @return document
     */
    private Document document() {
        return this.document;
    }

    /**
     * @message annotated_rules_are_all_start_with
     * @brief Check whether or not all rules for the annotated files are `START_WITH` rules
     * @param input_spec -> The input spec to check
     * @return -> true otherwise false if an invalid rule is found
     */
    private boolean annotated_rules_are_all_start_with(List<List<String>> input_spec) {
        /* TODO -> IMPROVE */
        for(List<String> l : input_spec)
            if(l.size() != 3 || !l.get(1).strip().equalsIgnoreCase("STARTS_WITH")) {
                /* TODO -> DELEGATE ERROR THROUGH TO THE VIEW CLASS */
                System.err.println("Error in annotation spec");
                return false;
            }
        return true;
    }

    /**
     * @message register_rules
     * @brief Creates a rule_set and saves it in the document field
     */
    private void register_rules() {
        RuleSetCreator rule_set_creator = new RuleSetCreator(
                document().line_blocks(),
                document().input_spec(),
                "input_rule_set");
        document().rule_set_eq(rule_set_creator.create_rule_set());
    }

    /** @Constructor **/
    public RegistrarEngine(Document document) {
        this.document = document;
    }

    /**
     * @message perform_task
     * @brief Main API call for registering rules according to file type
     */
    @Override
    public Void perform_task() {
        switch(document().doc_type()) {
            case RAW -> register_rules();
            case ANNOTATED -> {
                if(annotated_rules_are_all_start_with(document().input_spec()))
                    register_rules();
            }
        }

        /* TODO -> FIX INTO RETURNING SOMETHING CONCRETE */
        return null;
    }
}
