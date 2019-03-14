package com.shoutoutsoftware.sift

/**
 * Created on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

class Sift {

    @Throws(SiftException::class)
    fun readString(map: Map<*, *>?, key: String): String = read(map, key)

    fun readString(map: Map<*, *>?, key: String, defaultValue: String?): String? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readStringList(map: Map<*, *>?, key: String): List<String> = read(map, key)

    fun readStringList(map: Map<*, *>?, key: String, defaultValue: List<String>?): List<String>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumber(map: Map<*, *>?, key: String): Number = read(map, key)

    fun readNumber(map: Map<*, *>?, key: String, defaultValue: Number?): Number? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumberList(map: Map<*, *>?, key: String): List<Number> = read(map, key)

    fun readNumberList(map: Map<*, *>?, key: String, defaultValue: List<Number>?): List<Number>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readMap(map: Map<*, *>?, key: String): Map<*, *> = read(map, key)

    fun readMap(map: Map<*, *>?, key: String, defaultValue: Map<*, *>?): Map<*, *>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readMapList(map: Map<*, *>?, key: String): List<Map<*, *>> = read(map, key)

    fun readMapList(map: Map<*, *>?, key: String, defaultValue: List<Map<*, *>>?): List<Map<*, *>>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readBoolean(map: Map<*, *>?, key: String): Boolean = read(map, key)

    fun readBoolean(map: Map<*, *>?, key: String, defaultValue: Boolean?): Boolean? = read(map, key, defaultValue)

    private inline fun <reified T : Any> read(map: Map<*, *>?, key: String, defaultValue: T?): T? {
        return try {
            read(map, key)
        } catch (e: SiftException) {
            defaultValue
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> read(map: Map<*, *>?, key: String): T {
        if (map == null) {
            throw SiftException("the map is null")
        }

        if (map.containsKey(key)) {
            return parseValue(map[key])
        } else {
            throw SiftException("key not found")
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> parseValue(value: Any?): T {
        if (value == null) {
            throw SiftException("the value is null")
        } else {
            if (value is T) {
                return value
            } else {
                throw SiftException("the value type is not the same as the requested one" +
                        "\nRequested: " + T::class + "\nFound: " + value::class)
            }
        }
    }

}