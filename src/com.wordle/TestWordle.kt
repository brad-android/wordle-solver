package com.wordle

import com.wordle.WordleReader.readWordsFromFile

const val MAX_GUESS_TIMES = 6

fun main(args: Array<String>) {
    val list = readWordsFromFile()
    println("words count: ${list.size}")
    // reuse checker object
    val checker = WordleChecker()
    var failTimes = 0
    var totalTris = 0
    for (i in 0..1000) {
        val times = guessOnce(list, checker)
        if (times == null) {
            failTimes++
        } else {
            totalTris += times
        }
    }
    println("failed with in 6 tries: $failTimes")
    println("succeed average tries: ${totalTris / (1000.0 - failTimes)}")
}

fun guessOnce(wordList: List<String>, checker: WordleChecker): Int? {
    val word = pickAWord(wordList)
//    println("picked word: $word")
    val solver = WordleSolver(wordList)

    var guessTimes = 0
    var guessWord = solver.trySovler(null, null)
    var correctGuess = false
    while (guessTimes < MAX_GUESS_TIMES) {
        val result = checker.checkWord(guessWord, word)
        if (result.sum() == WordleChecker.CHECK_RESULT_GREEN * result.size) {
            correctGuess = true
            break
        } else {
            guessTimes++
            guessWord = solver.trySovler(guessWord, result)
        }
    }
    return if (correctGuess) {
//        println("guess $guessTimes time to fine the correct word!")
        guessTimes
    } else {
//        println("fail to get the right word")
        null
    }

}

/**
 * randomly pick a word from the list
 */
fun pickAWord(list: List<String>): String {
    val randomIndex = (Math.random() * list.size).toInt()
//    println(randomIndex)
    return list[randomIndex]
}