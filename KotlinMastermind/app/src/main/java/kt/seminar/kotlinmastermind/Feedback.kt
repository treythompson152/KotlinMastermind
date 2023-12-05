package kt.seminar.kotlinmastermind

import kt.seminar.kotlinmastermind.MastermindGame.Color

class Feedback(secretCode: List<Color>) {
    private val secretCode = secretCode.toMutableList()

    // Compare the guess with the secret code and return the feedback
    fun provideFeedback(guess: List<Color>): FeedbackResult {
        val result = FeedbackResult()

        // Check for black pegs (correct color and position)
        for (i in guess.indices) {
            if (guess[i] == secretCode[i]) {
                result.addBlackPeg()
                secretCode[i] = Color.EMPTY // Marking the position as used
            }
        }

        // Check for white pegs (correct color but wrong position)
        val unmatchedGuess = mutableListOf<Color>()
        val unmatchedSecret = mutableListOf<Color>()

        for (i in guess.indices) {
            if (guess[i] != secretCode[i]) {
                unmatchedGuess.add(guess[i])
                unmatchedSecret.add(secretCode[i])
            }
        }

        for (color in unmatchedGuess) {
            val index = unmatchedSecret.indexOf(color)
            if (index != -1) {
                result.addWhitePeg()
                unmatchedSecret[index] = Color.EMPTY // Marking the color as used
            }
        }

        return result
    }

}

data class FeedbackResult(
    var blackPegs: Int = 0,
    var whitePegs: Int = 0
) {
    fun addBlackPeg() {
        blackPegs++
    }

    fun addWhitePeg() {
        whitePegs++
    }

    fun isGuessCorrect(): Boolean {
        return blackPegs == 4
    }
}
