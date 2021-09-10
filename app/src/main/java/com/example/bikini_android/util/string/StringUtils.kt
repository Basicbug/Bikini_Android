/*
 * StringUtils.kt 2021. 9. 9
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.string

/**
 * @author MyeongKi
 */
object StringUtils {
    fun appendString(vararg texts: String): String {
        return texts.reduce { acc, s ->
            acc + SEPARATOR_STRING + s
        }
    }
}