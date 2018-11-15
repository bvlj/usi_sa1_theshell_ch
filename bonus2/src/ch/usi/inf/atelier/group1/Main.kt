/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1

import ch.usi.inf.atelier.group1.util.Log
import java.io.File

object Main {
    private lateinit var parser: HtmlParser

    @JvmStatic
    fun main(args: Array<String>) {

        if (args.isEmpty()) {
            Log.e("At least one argument is needed")
            return
        }

        parser = HtmlParser(arrayOf("-s", "--singlepage").contains(args[0]))

        args.forEach(this::convert)

        parser.save()
    }

    /**
     * Convert html files to LaTex files
     *
     * @param path of the file (or directory containing files) to be converted
     */
    private fun convert(path: String) {
        // Ignore the flags
        if (path.startsWith("-")) {
            return
        }

        val file = File(path)

        if (!file.isDirectory) {
            parser.parse(file)
            return
        }

        // Recursively search for other html files inside a directory
        file.listFiles().filter { it.name.endsWith(".html") || it.isDirectory }
                .map { it.path }
                .forEach(this::convert)
    }
}