package com.shoutoutsoftware.sift;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

/**
 * Created on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

public class ReadFromDictionaryTests {

    private final Sift sift = new Sift();
    private final Map<String, Object> stringData = new HashMap<>();

    @Before
    public void initData() {
        stringData.put("wrongType", 2);
        stringData.put("nullValue", null);
        stringData.put("correctValue", "some string");
    }

    @Test
    public void testThrowsExceptionWhenMapIsNull() {
        try {
            sift.readString(null, "some random key");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "the map is null");
        }
    }

    @Test
    public void testThrowsExceptionWhenKeyIsNotFound() {
        try {
            sift.readString(stringData, "some random key");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "key not found");
        }
    }

    @Test
    public void testThrowsExceptionIfValueTypeIsWrong() {
        try {
            sift.readString(stringData, "wrongType");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "the value type is not the same as the requested one\nRequested: class java.lang.String (Kotlin reflection is not available)\nFound: class java.lang.Integer (Kotlin reflection is not available)");
        }
    }

    @Test
    public void testThrowsExceptionIfValueIsNull() {
        try {
            sift.readString(stringData, "nullValue");
            fail("did not throw an exception when expected");
        } catch (SiftException exception) {
            assertEquals(exception.getLocalizedMessage(), "the value is null");
        }
    }

    @Test
    public void testValueIsReadSuccessfully() throws SiftException {
        String value = sift.readString(stringData, "correctValue");
        Assert.assertEquals(value, "some string");
    }

    @Test
    public void testReturnsDefaultValueWhenKeyIsNotFound() {
        String value = sift.readString(stringData, "some random key", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsOfWrongType() {
        String value = sift.readString(stringData, "wrongType", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsNull() {
        String value = sift.readString(stringData, "nullValue", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testDefaultValueIsIgnoredWhenValueIsReadSuccessfully() {
        String value = sift.readString(stringData, "correctValue", "default val");
        Assert.assertEquals(value, "some string");
    }

    @Test
    public void testReadValuesFromComplexMap() throws SiftException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("null", null);
        data.put("int", 1);
        data.put("string", "some string");
        data.put("float", 2.1);
        data.put("double", 12.432132213);
        data.put("boolean", true);
        data.put("intArray", Arrays.asList(1, 2, 3));
        data.put("stringArray", Arrays.asList("valOne", "valTwo", "valThree"));

        HashMap<String, Object> innerMap1 = new HashMap<>();
        innerMap1.put("innerString", "inner1Val");
        innerMap1.put("innerDoubleArray", Arrays.asList(1.2, 32.3, 32.4423));
        data.put("innerMap", innerMap1);


        HashMap<String, Object> arrayMap1 = new HashMap<>();
        arrayMap1.put("arrayMap1String", "aMS1");

        HashMap<String, Object> arrayMap2 = new HashMap<>();
        arrayMap2.put("arrayMap2Int", 3);

        HashMap<String, Object> arrayMap3 = new HashMap<>();
        arrayMap3.put("arrayMap3Array", Arrays.asList("a", "b", "c"));

        data.put("mapList", Arrays.asList(arrayMap1, arrayMap2, arrayMap3));

        assertNull(sift.readString(data, "null", null));
        assertEquals(1, sift.readNumber(data, "int"));
        assertEquals("some string", sift.readString(data, "string"));
        assertEquals(2.1, sift.readNumber(data, "float"));
        assertEquals(12.432132213, sift.readNumber(data, "double"));
        assertTrue(sift.readBoolean(data, "boolean"));
        assertEquals(Arrays.asList(1, 2, 3), sift.readNumberList(data, "intArray"));
        assertEquals(Arrays.asList("valOne", "valTwo", "valThree"), sift.readStringList(data, "stringArray"));

        Map<String, Object> parsedInnerMap1 = sift.readMap(data, "innerMap");

        assertEquals(innerMap1, parsedInnerMap1);
        assertEquals("inner1Val", sift.readString(parsedInnerMap1, "innerString"));
        assertEquals(Arrays.asList(1.2, 32.3, 32.4423), sift.readNumberList(parsedInnerMap1, "innerDoubleArray"));

        List<Map<String, Object>> parsedMapList = sift.readMapList(data, "mapList");
        assertEquals(Arrays.asList(arrayMap1, arrayMap2, arrayMap3), parsedMapList);
        assertEquals("aMS1", sift.readString(parsedMapList.get(0), "arrayMap1String"));
        assertEquals(3, sift.readNumber(parsedMapList.get(1), "arrayMap2Int"));
        assertEquals(Arrays.asList("a", "b", "c"), sift.readStringList(parsedMapList.get(2), "arrayMap3Array"));
    }

}
