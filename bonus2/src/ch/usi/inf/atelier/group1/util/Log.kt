/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.util

import java.lang.Exception

object Log {

    fun e(message: String = "", exception: Exception) {
        e("$message: ${exception.message ?: "Unknown error"}")
    }

    fun e(message: String) {
        System.err.println(message)
    }

    fun i(obj: Any) {
        println(obj.toString())
    }
}