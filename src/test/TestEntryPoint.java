package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import client.EntryPoint;

/**
 * @class TestEntryPoint
 * @desc: Test the class where main resides
 */
public class TestEntryPoint {
    Scanner s;

    @Before
    public final void setupScanner() {
        this.s = new Scanner(System.in);
    }

    @Test
    public final void test_manageExit_true() {
        boolean result = EntryPoint.manageExit(this.s, true);
        assertTrue(result);
    }

    @Test
    public final void test_manageExit_false() {
        boolean result = EntryPoint.manageExit(this.s, false);
        assertFalse(result);
    }
}