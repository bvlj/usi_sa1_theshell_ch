/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1

import java.io.File

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        args.forEach(this::convert)
    }

    /**
     * Convert html files to LaTex files
     *
     * @param path of the file (or directory containing files) to be converted
     */
    private fun convert(path: String) {
        val file = File(path)

        if (!file.isDirectory) {
            HtmlParser(file).parse()
            return
        }

        // Recursively search for other html files inside a directory
        file.listFiles().filter { it.name.endsWith(".html") || it.isDirectory }
                .map { it.path }
                .forEach(this::convert)
    }
}