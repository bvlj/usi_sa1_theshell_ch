/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.util

import java.text.SimpleDateFormat
import java.util.*

object Log {

    /**
     * Log an exception as an error
     *
     * @param exception The error exception
     * @param shouldThrow Whether the exception should be thrown
     */
    fun <T : Exception> e(exception: T, shouldThrow: Boolean) {
        e(exception.message ?: "Unknown error")

        if (shouldThrow) {
            throw exception
        }
    }

    /**
     * Log an error
     *
     * @param message The log message
     */
    fun e(message: String) {
        print('E', message , true)
    }

    /**
     * Log an object using its toString() method.
     *
     * @param obj The object which content will be printed.
     */
    fun i(obj: Any) {
        print('I', obj.toString(), false)
    }

    /**
     * Print a log with a date and prefix
     *
     * @param prefix The prefix of the log. Helps differentiating the various types of logs
     * @param message The message that will be displayed in the log
     * @param isErr Whether the log should be printed as an error
     */
    private fun print(prefix: Char, message: String, isErr: Boolean) {
        val time = SimpleDateFormat("yyyy-MM-dd hh:mm").format(Date())

        if (isErr) {
            System.err.println("$prefix $time\t$message")
        } else {
            System.out.println("$prefix $time\t$message")
        }
    }
}