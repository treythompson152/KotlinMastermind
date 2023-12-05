package kt.seminar.kotlinmastermind

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class MastermindGame {

    private val secretCode: List<Color> // The secret code to be guessed
    private var numberOfGuesses: Int // Track the number of guesses made by the player

    // Constructor to initialize the game
    init {
        secretCode = generateSecretCode()
        numberOfGuesses = 0
    }

    enum class Color {
        RED, BLUE, YELLOW, GREEN, ORANGE, PINK, EMPTY
    }

    private fun generateSecretCode(): List<Color> {
        val colors = Color.values()
        val secretCode = mutableListOf<Color>()

        // Generate a random secret code by picking colors from the enum
        for (i in 1..4) { // Assuming a 4-color code, adjust as needed
            var randomColor = colors.random()
            while (randomColor == Color.EMPTY) {
                randomColor = colors.random()
            }
            secretCode.add(randomColor)
        }
        return secretCode
    }

    private val colorCodeMap = mapOf(
        -48060 to Color.RED,
        -13388315 to Color.BLUE,
        -256 to Color.YELLOW,
        -16711936 to Color.GREEN,
        -26368 to Color.ORANGE,
        -39246 to Color.PINK
    )

    fun guess(currentGuess: List<Drawable>): FeedbackResult {
        val guessArray = translateGuess(currentGuess)
        return Feedback(secretCode).provideFeedback(guessArray)
    }

    private fun translateGuess(currentGuess: List<Drawable>): List<Color> {
        return currentGuess.map { drawable ->
            val color = getColorFromDrawable(drawable)
            colorCodeMap[color] ?: Color.EMPTY // Default to EMPTY if color not found
        }
    }

    private fun getColorFromDrawable(drawable: Drawable): Int {
        // Extract color from the Drawable
        return if (drawable is ColorDrawable) {
            drawable.color
        } else {
            // Handle other Drawable types if needed
            -1
        }
    }

    fun getSecretCode() : List<Color> { // only debugging purposes
        return secretCode
    }

}
