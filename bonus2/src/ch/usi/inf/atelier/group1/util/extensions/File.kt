/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.util.extensions

import ch.usi.inf.atelier.group1.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * Get the content of the file as String
 *
 * @return this File content
 */
fun File.getContent(): String {
    val content = StringBuilder()
    try {
        val reader = BufferedReader(FileReader(this))
        var line: String? = ""

        while (line != null) {
            content.append(line)
                    .append('\n')
            line = reader.readLine()
        }
    } catch (e: IOException) {
        Log.e(e, true)
    }

    return content.toString()
}