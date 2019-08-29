package com.shoutoutsoftware.sift

/**
 * Created on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

class Sift {

    //MARK: Functions to read from dictionary

    @Throws(SiftException::class)
    fun readString(fromMap: Map<String, Any?>?, key: String): String = read(fromMap, key)

    fun readString(fromMap: Map<String, Any?>?, key: String, defaultValue: String?): String? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readStringList(fromMap: Map<String, Any?>?, key: String): List<String> = read(fromMap, key)

    fun readStringList(fromMap: Map<String, Any?>?, key: String, defaultValue: List<String>?): List<String>? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumber(fromMap: Map<String, Any?>?, key: String): Number = read(fromMap, key)

    fun readNumber(fromMap: Map<String, Any?>?, key: String, defaultValue: Number?): Number? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumberList(fromMap: Map<String, Any?>?, key: String): List<Number> = read(fromMap, key)

    fun readNumberList(fromMap: Map<String, Any?>?, key: String, defaultValue: List<Number>?): List<Number>? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readMap(fromMap: Map<String, Any?>?, key: String): Map<String, Any?> = read(fromMap, key)

    fun readMap(fromMap: Map<String, Any?>?, key: String, defaultValue: Map<String, Any?>?): Map<String, Any?>? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readMapList(fromMap: Map<String, Any?>?, key: String): List<Map<String, Any?>> = read(fromMap, key)

    fun readMapList(fromMap: Map<String, Any?>?, key: String, defaultValue: List<Map<String, Any>>?): List<Map<String, Any?>>? = read(fromMap, key, defaultValue)

    @Throws(SiftException::class)
    fun readBoolean(fromMap: Map<String, Any?>?, key: String): Boolean = read(fromMap, key)

    fun readBoolean(fromMap: Map<String, Any?>?, key: String, defaultValue: Boolean?): Boolean? = read(fromMap, key, defaultValue)

    private inline fun <reified T : Any> read(map: Map<*, *>?, key: String, defaultValue: T?): T? {
        return try {
            read(map, key)
        } catch (e: SiftException) {
            defaultValue
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> read(map: Map<*, *>?, key: String): T {
        if (map == null) throw SiftException("the map is null")

        if (map.containsKey(key)) {
            return parseValue(map[key])
        } else {
            throw SiftException("key not found")
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> parseValue(value: Any?): T {
        if (value == null) throw SiftException("the value is null")

        if (value is T) {
            return value
        } else {
            throw SiftException("the value type is not the same as the requested one" +
                    "\nRequested: " + T::class + "\nFound: " + value::class)
        }
    }

    //MARK: Functions to read from list

    @Throws(SiftException::class)
    public fun readString(fromList: List<Any?>?, atIndex: Int?): String = readValue(fromList, atIndex)

    public fun readString(fromList: List<Any?>?, atIndex: Int?, defaultValue: String?): String? =
            readValue(fromList, atIndex, defaultValue)

    @Throws(SiftException::class)
    public fun readNumber(fromList: List<Any?>?, atIndex: Int?): Number = readValue(fromList, atIndex)

    public fun readNumber(fromList: List<Any?>?, atIndex: Int?, defaultValue: Number?): Number? =
            readValue(fromList, atIndex, defaultValue)

    @Throws(SiftException::class)
    public fun readMap(fromList: List<Any?>?, atIndex: Int?): Map<String, Any?> = readValue(fromList, atIndex)

    public fun readMap(fromList: List<Any?>?, atIndex: Int?, defaultValue: Map<String, Any?>?): Map<String, Any?>? =
            readValue(fromList, atIndex, defaultValue)

    @Throws(SiftException::class)
    private inline fun <reified T : Any> readValue(fromList: List<Any?>?, atIndex: Int?, defaultValue: T?): T? {
        return try {
            readValue(fromList, atIndex)
        } catch (e: SiftException) {
            return defaultValue
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> readValue(fromList: List<Any?>?, atIndex: Int?): T {
        if (fromList == null) throw SiftException("the list is null")

        if (atIndex == null) throw SiftException("the index is null")

        if (fromList.size > atIndex) {
            val value = fromList[atIndex]
            return parseValue(value)
        } else {
            throw SiftException("index $atIndex out of bounds")
        }
    }

}