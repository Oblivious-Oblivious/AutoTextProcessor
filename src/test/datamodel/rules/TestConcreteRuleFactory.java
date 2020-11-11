package test.datamodel.rules;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.ConcreteRuleFactory;

/**
 * @class: TestConcreteRuleFactory
 */
public class TestConcreteRuleFactory {
    private ConcreteRuleFactory ruleApi = new ConcreteRuleFactory();

    @Before
    public final void setupConcreteRuleFactoryApi() {
        this.ruleApi = new ConcreteRuleFactory();
    }

    @Test
    public final void test_createRuleUndefined() {
        assertNotNull(this.ruleApi.createRuleUndefined());
    }

    @Test
    public final void test_createRuleAllCaps() {
        assertNotNull(this.ruleApi.createRuleAllCaps());
    }

    @Test
    public final void test_createRuleInPosition() {
        List<LineBlock> pLineblocks = new ArrayList<LineBlock>();
        List<Integer> pPositions = new ArrayList<Integer>();
        assertNotNull(this.ruleApi.createRuleInPosition(pLineblocks, pPositions));
    }

    @Test
    public final void test_createRuleStartWith() {
        String prefix = "STH";
        assertNotNull(this.ruleApi.createRuleStartWith(prefix));
    }

    /* TODO -> COMPLETE */
}