/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.util.extensions

import ch.usi.inf.atelier.group1.util.Log
import java.io.File
import java.io.IOException

fun String.toFile(): File? {
    try {
        File(this)
    } catch (e: IOException) {
        Log.e("Failed to open $this as File", e)
        throw e
    }

    return null
}

fun String.getClosingTag() = "</${substring(1, length)}"