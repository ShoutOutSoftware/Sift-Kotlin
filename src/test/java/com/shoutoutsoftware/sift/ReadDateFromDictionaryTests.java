package com.shoutoutsoftware.sift;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ReadDateFromDictionaryTests {

    private final Sift sift = new Sift();

    @Test
    public void testThrowsError_IfDateStringIsNotCorrect() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("date", "wrong date string");

        try {
            sift.readDate(data, "date", "yyyy MM dd");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "Failed to parse date for Key: date, Date: wrong date string, Format: yyyy MM dd");
        }

    }

    @Test
    public void testThrowsError_IfDateFormatStringIsNotCorrect() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("date", "1990 03 25");

        try {
            sift.readDate(data, "date", "dd xyz");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "Failed to parse date for Key: date, Date: 1990 03 25, Format: dd xyz");
        }

    }

    @Test
    public void testSuccessfullyReadingDates() throws SiftException {
        HashMap<String, Object> data = new HashMap<>();

        data.put("date", "1990 03 25");
        sift.readDate(data, "date", "yyyy MM dd");

        data.put("date", "25/03/1990");
        sift.readDate(data, "date", "dd/MM/yy");

        data.put("date", "25 03 90 12:35");
        sift.readDate(data, "date", "dd MM yy hh:mm");
    }

}
