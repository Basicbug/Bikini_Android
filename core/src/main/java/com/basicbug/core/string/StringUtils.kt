/*
 * StringUtils.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.string

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