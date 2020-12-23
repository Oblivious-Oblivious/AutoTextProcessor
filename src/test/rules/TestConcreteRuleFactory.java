package test.rules;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datamodel.LineBlock;
import rules.ConcreteRuleFactory;

/**
 * @class TestConcreteRuleFactory
 */
public class TestConcreteRuleFactory {
    private ConcreteRuleFactory ruleApi = new ConcreteRuleFactory();

    @Before
    public final void setupConcreteRuleFactoryApi() {
        this.ruleApi = new ConcreteRuleFactory();
    }

    @Test
    public final void test_createRuleUndefined() {
        assertNotNull(this.ruleApi.create_rule_undefined());
    }

    @Test
    public final void test_createRuleAllCaps() {
        assertNotNull(this.ruleApi.create_rule_undefined());
    }

    @Test
    public final void test_createRuleInPosition() {
        List<LineBlock> line_blocks = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        assertNotNull(this.ruleApi.create_rule_in_position(line_blocks, positions));
    }

    @Test
    public final void test_createRuleStartWith() {
        String prefix = "STH";
        assertNotNull(this.ruleApi.create_rule_start_with(prefix));
    }
}