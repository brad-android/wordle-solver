package com.wordle

class WordleChecker {

    companion object {
        // if guess char is in correct place
        const val CHECK_RESULT_GREEN = 0
        // if guess char is not in the word
        const val CHECK_RESULT_GREY = 1
        // if guess char is in the word but in a wrong place
        const val CHECK_RESULT_YELLOW = 2
    }

    /**
     * check if guess word is correct
     */
    fun checkWord(guessWord: String, targetWord: String): IntArray {
        assert(guessWord.length == targetWord.length)
        val array = IntArray(guessWord.length)
        for (i in targetWord.indices) {
            when {
                guessWord[i] == targetWord[i] ->
                    array[i] = CHECK_RESULT_GREEN
                targetWord.contains(guessWord[i], false) ->
                    array[i] = CHECK_RESULT_YELLOW
                else ->
                    array[i] = CHECK_RESULT_GREY
            }
        }
        return array
    }
}