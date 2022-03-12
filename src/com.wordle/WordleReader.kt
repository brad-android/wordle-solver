package com.wordle

import java.io.File
import java.io.FileReader

object WordleReader {

    fun readWordsFromFile(): List<String> {
        val reader = FileReader(File("english_words_original_wordle.txt"))
        return reader.readLines()
    }
}