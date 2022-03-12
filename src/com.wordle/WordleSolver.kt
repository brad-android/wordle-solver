package com.wordle

class WordleSolver(var wordList: List<String>) {

    private lateinit var wordWeightList: MutableList<Int>

    init {
        preProcessData()
    }

    private fun preProcessData() {
        wordWeightList = ArrayList(wordList.size)
        wordList.forEach { word ->
            wordWeightList.add(calculateWeight(word))
        }
    }

    /**
     * calculate how many different chars in a word,
     * since a word can be quite short, use a simple for loop here
     */
    private fun calculateWeight(word: String): Int {
        var weight = word.length
        for (i in word.indices) {
            for (j in i + 1 until word.length) {
                if (word[i] == word[j]) {
                    weight--
                }
            }
        }
        return weight
    }

    fun trySovler(lastGuess: String?, lastResult: IntArray?): String {
        filterWord(lastGuess, lastResult)
        return guessByWeight()
    }

    // todo implement different guess strategy
    private fun guessByWeight(): String {
        val requireWeight = when {
            wordList.size > 200 -> 5
            else -> 4
        }
        var result: String? = null
        var maxWeightIndex = 0
        for (i in wordWeightList.indices) {
            if (wordWeightList[i] >= requireWeight) {
                result = wordList[i]
                break
            } else if (wordWeightList[i] > wordWeightList[maxWeightIndex]) {
                maxWeightIndex = i
            }
        }
        return result ?: wordList[maxWeightIndex]
    }

    private fun filterWord(lastGuess: String?, lastResult: IntArray?) {
        if (lastGuess != null && lastResult != null) {
            val filterWeightList = mutableListOf<Int>()
            wordList = wordList.filterIndexed { index, word ->
                val match = matchWord(word, lastGuess, lastResult)
                if (match) {
                    filterWeightList.add(wordWeightList[index])
                }
                match
            }
            wordWeightList = filterWeightList
        }
    }

    private fun matchWord(word: String, lastGuess: String, lastResult: IntArray): Boolean {
        var result = true
        loop@ for (index in lastResult.indices) {
            when (lastResult[index]) {
                WordleChecker.CHECK_RESULT_GREEN ->
                    if (word[index] != lastGuess[index]) {
                        result = false
                        break@loop
                    }
                WordleChecker.CHECK_RESULT_GREY ->
                    if (word.contains(lastGuess[index])) {
                        result = false
                        break@loop
                    }
                WordleChecker.CHECK_RESULT_YELLOW ->
                    if (word[index] == lastGuess[index] || !word.contains(lastGuess[index])) {
                        result = false
                        break@loop
                    }
            }
        }
        return result
    }

}

