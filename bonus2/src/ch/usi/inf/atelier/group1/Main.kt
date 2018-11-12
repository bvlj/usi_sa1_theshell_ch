/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1

import ch.usi.inf.atelier.group1.html.HtmlParser

object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        for (arg in args) {
            println(HtmlParser(arg).parse())
        }
    }
}