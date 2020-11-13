package test.datamodel.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.RuleUndefined;

/**
 * @class: TestRuleUndefined
 */
public class TestRuleUndefined {
    RuleUndefined ruleApi;

    @Before
    public final void setupRuleApi() {
        this.ruleApi = new RuleUndefined();
    }

    @Test
    public final void test_isValid_for_invalid_rule() {
        assertFalse(this.ruleApi.isValid(new LineBlock(new ArrayList<String>())));
    }

    @Test
    public final void test_toString() {
        assertEquals("RuleUndefined", this.ruleApi.toString());
    }
}