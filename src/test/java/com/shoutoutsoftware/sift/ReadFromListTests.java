package com.shoutoutsoftware.sift;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ReadFromListTests {

    private final Sift sift = new Sift();

    @Test
    public void testThrowsExceptionIfListIsNull() {
        try {
            sift.readString(null, 2);
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "The source list is null");
        }
    }

    @Test
    public void testThrowsErrorIfIndexIsOutOfBounds() {
        List<String> list = Arrays.asList("hello", "hey", "hi");
        try {
            sift.readString(list, 4);
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "Index 4 out of bounds");
        }
    }

    @Test
    public void testThrowsExceptionIfValueTypeIsWrong() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        try {
            sift.readString(list, 1);
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "The value type is not the same as the requested one.\nIndex: 1\nRequested: class java.lang.String (Kotlin reflection is not available)\nFound: class java.lang.Integer (Kotlin reflection is not available)");
        }
    }

    @Test
    public void testThrowsExceptionIfValueIsNull() {
        List<String> list = Arrays.asList("hello", "hey", null);
        try {
            sift.readString(list, 2);
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "The value is null for Index: 2");
        }
    }

    @Test
    public void testValueIsReadSuccessfully() throws SiftException {
        List<String> list = Arrays.asList("hello", "hey", "hi");
        String value = sift.readString(list, 2);
        assertEquals(value, "hi");
    }

    @Test
    public void testReturnsDefaultValueIfIndexIsOutOfBounds() {
        List<String> list = Arrays.asList("hello", "hey", "hi");
        String value = sift.readString(list, 10, "def val");
        assertEquals(value, "def val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsOfWrongType() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        String value = sift.readString(list, 2, "def val");
        Assert.assertEquals(value, "def val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsNull() {
        List<String> list = Arrays.asList("hello", "hey", null);
        String value = sift.readString(list, 2, "def val");
        Assert.assertEquals(value, "def val");
    }

    @Test
    public void testDefaultValueIsIgnoredWhenValueIsReadSuccessfully() {
        List<String> list = Arrays.asList("hello", "hey", "hi");
        String value = sift.readString(list, 1, "def val");
        Assert.assertEquals(value, "hey");
    }

}
