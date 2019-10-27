package cmu.sem.fridgely;

import org.junit.Test;

import cmu.sem.fridgely.util.Formatter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FormatterTest {
    @Test
    public void castCaloriesToTwoDecimalsTest() {
        String actualCalorie = Formatter.castCaloriesToTwoDecimals("254.9318098");
        assertEquals("254.93", actualCalorie);

        String actualCalorie2 = Formatter.castCaloriesToTwoDecimals("190");
        assertEquals("190", actualCalorie2);

        String actualCalorie3 = Formatter.castCaloriesToTwoDecimals("0");
        assertEquals("0", actualCalorie3);
    }
}