package com.shoutoutsoftware.sift

import java.util.*

/**
 * Created by Obaid Ahmed Mohammed on 20 September 2017
 * Copyright © 2017 ShoutOut Software. All rights reserved.
 */

class Sift {

    companion object {

        fun readString(map: HashMap<String, Any?>, key: String, defaultValue: String?): String? =
                read(map, key, defaultValue)

        @Throws(SiftException::class)
        fun readString(map: HashMap<String, Any?>, key: String): String = read(map, key)

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
                val value = map[key]
                if (value == null) {
                    throw SiftException("the value is null")
                } else {
                    if (value is T) {
                        return value
                    } else {
                        throw SiftException("the value type is not the same as the requested one")
                    }
                }
            } else {
                throw SiftException("key not found")
            }
        }
    }

}