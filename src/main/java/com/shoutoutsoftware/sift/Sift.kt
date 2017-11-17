package com.shoutoutsoftware.sift

/**
 * Created by Obaid Ahmed Mohammed on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

class Sift {

    @Throws(SiftException::class)
    fun readString(map: HashMap<String, Any?>, key: String): String = read(map, key)

    fun readString(map: HashMap<String, Any?>, key: String, defaultValue: String?): String? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readStringList(map: HashMap<String, Any?>, key: String): List<String> = read(map, key)

    fun readStringList(map: HashMap<String, Any?>, key: String, defaultValue: List<String>?): List<String>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumber(map: HashMap<String, Any?>, key: String): Number = read(map, key)

    fun readNumber(map: HashMap<String, Any?>, key: String, defaultValue: Number?): Number? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readNumberList(map: HashMap<String, Any?>, key: String): List<Number> = read(map, key)

    fun readNumberList(map: HashMap<String, Any?>, key: String, defaultValue: List<Number>?): List<Number>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readHashMap(map: HashMap<String, Any?>, key: String): HashMap<String, Any?> = read(map, key)

    fun readHashMap(map: HashMap<String, Any?>, key: String, defaultValue: HashMap<String, Any?>?): HashMap<String, Any?>? = read(map, key, defaultValue)

    @Throws(SiftException::class)
    fun readHashMapList(map: HashMap<String, Any?>, key: String): List<HashMap<String, Any?>> = read(map, key)

    fun readHashMapList(map: HashMap<String, Any?>, key: String, defaultValue: List<HashMap<String, Any?>>?): List<HashMap<String, Any?>>? = read(map, key, defaultValue)

    private inline fun <reified T : Any> read(map: HashMap<String, Any?>, key: String, defaultValue: T?): T? {
        return try {
            read(map, key)
        } catch (e: SiftException) {
            defaultValue
        }
    }

    @Throws(SiftException::class)
    private inline fun <reified T : Any> read(map: HashMap<String, Any?>, key: String): T {
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