package com.shoutoutsoftware.sift;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;


/**
 * Created by Obaid Ahmed Mohammed on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

public class SiftTest {

    private HashMap<String, Object> stringData = new HashMap<>();

    @Before
    public void initData() {
        stringData.put("wrongType", 2);
        stringData.put("nullValue", null);
        stringData.put("correctValue", "some string");
    }

    @Test(expected = SiftException.class)
    public void testThrowsExceptionWhenKeyIsNotFound() throws SiftException {
        Sift.Companion.readString(stringData, "some random key");
    }

    @Test(expected = SiftException.class)
    public void testThrowsExceptionIfValueIsNotAString() throws SiftException {
        Sift.Companion.readString(stringData, "wrongType");
    }

    @Test(expected = SiftException.class)
    public void testThrowsExceptionIfValueIsNull() throws SiftException {
        Sift.Companion.readString(stringData, "nullValue");
    }

    @Test
    public void testStringIsReadSuccessfully() throws SiftException {
        String value = Sift.Companion.readString(stringData, "correctValue");
        Assert.assertEquals(value, "some string");
    }

    @Test
    public void testReturnsDefaultValueWhenKeyIsNotFound() {
        String value = Sift.Companion.readString(stringData, "some random key", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsNotAString() {
        String value = Sift.Companion.readString(stringData, "wrongType", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testReturnsDefaultValueWhenValueIsNull() {
        String value = Sift.Companion.readString(stringData, "nullValue", "default val");
        Assert.assertEquals(value, "default val");
    }

    @Test
    public void testDefaultValueIsIgnoredWhenStringIsReadSuccessfully() {
        String value = Sift.Companion.readString(stringData, "correctValue", "default val");
        Assert.assertEquals(value, "some string");
    }

}
