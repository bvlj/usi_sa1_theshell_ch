/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.util

import java.text.SimpleDateFormat
import java.util.*

object Log {

    fun e(exception: Exception) {
        print('E', exception.message ?: "Unknown error", true)
    }

    fun i(obj: Any) {
        print('I', obj.toString(), false)
    }

    private fun print(prefix: Char, message: String, isErr: Boolean) {
        val time = SimpleDateFormat("yyyy-MM-dd hh:mm").format(Date())

        if (isErr) {
            System.err.println("$prefix $time\t$message")
        } else {
            System.out.println("$prefix $time\t$message")
        }
    }
}