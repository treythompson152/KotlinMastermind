package kt.seminar.kotlinmastermind

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat

class MastermindActivity : Activity() {

    private var currentColumn = 0
    private var currentRow = 0
    private val numCircleRows = 12
    private val numCirclesPerRow = 4
    private val currentRowBackground = Black.toArgb()
    private val mastermindGame = MastermindGame()
    private val colorList = mutableListOf<Drawable>()
    private var isDarkModeActive: Boolean = true
    private var isMuted: Boolean = false
    private lateinit var rootLayout: RelativeLayout
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        createCircleRows(numCircleRows, numCirclesPerRow)

        val colorButton1 = findViewById<Button>(R.id.colorButton1)
        val colorButton2 = findViewById<Button>(R.id.colorButton2)
        val colorButton3 = findViewById<Button>(R.id.colorButton3)
        val colorButton4 = findViewById<Button>(R.id.colorButton4)
        val colorButton5 = findViewById<Button>(R.id.colorButton5)
        val colorButton6 = findViewById<Button>(R.id.colorButton6)

        val circlesContainer = findViewById<LinearLayout>(R.id.circlesContainer)
        circlesContainer.getChildAt(currentRow).setBackgroundColor(currentRowBackground)
        val makeGuessButton = findViewById<Button>(R.id.makeGuessButton)
        val clearButton = findViewById<Button>(R.id.clearButton)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val infoButton = findViewById<ImageButton>(R.id.infoButton)
        val gearButton = findViewById<ImageButton>(R.id.gearButton)

        rootLayout = findViewById<RelativeLayout>(R.id.rootLayout)
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // Set click listeners for color buttons
        colorButton1.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        colorButton2.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        colorButton3.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        colorButton4.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        colorButton5.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        colorButton6.setOnClickListener {
            if (currentColumn > 3) {
                showTooManyColorError()
            } else {
                changeCircleColor(it.background, currentRow, currentColumn, circlesContainer)
            }
        }
        // Set click listeners for make guess and clear buttons
        makeGuessButton.setOnClickListener { makeGuess(circlesContainer) }
        clearButton.setOnClickListener { clearCircles(circlesContainer) }

        // Listeners for setting, back, and info buttons
        backButton.setOnClickListener { showExitConfirmationDialog() }
        infoButton.setOnClickListener { showHowtoPlay() }
        gearButton.setOnClickListener { showPopupMenu(it, R.menu.settings_menu) }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun showTooManyColorError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Too Many!")
        builder.setMessage("You can only enter 4 guesses.")
        builder.show()
    }

    private fun showPopupMenu(view: View, menuResId: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(menuResId, popupMenu.menu)

        // Find menu items by their IDs
        val darkModeMenuItem = popupMenu.menu.findItem(R.id.menu_item_dark_mode)
        val muteMusicMenuItem = popupMenu.menu.findItem(R.id.menu_item_mute_music)

        // Set checked state based on your variables (isDarkModeEnabled, isMusicMuted)
        darkModeMenuItem.isChecked = isDarkModeActive
        muteMusicMenuItem.isChecked = isMuted

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            // Handle menu item click
            when (item.itemId) {
                R.id.menu_item_dark_mode -> {
                    toggleDarkMode()
                    // Update the checked state after toggling
                    item.isChecked = isDarkModeActive
                    true
                }
                R.id.menu_item_mute_music -> {
                    toggleMute()
                    // Update the checked state after toggling
                    item.isChecked = isMuted
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun toggleMute() {
        if(isMuted) {
            mediaPlayer.start()
        } else {
            mediaPlayer.pause()
        }

        isMuted = !isMuted
    }

    private fun toggleDarkMode() {
        if(isDarkModeActive) {
            rootLayout.setBackgroundColor(Color.parseColor("#D6dce0"))

        } else {
            rootLayout.setBackgroundColor(Color.parseColor("#808080"))
        }


        isDarkModeActive = !isDarkModeActive
    }

    private fun showHowtoPlay() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("How to Play")
        builder.setMessage("" +
                "The plan is simple...\n\n" +
                "There is a color code that is hidden...Your job is to figure it out!\n" +
                "There are 6 colors to choose from and colors can be repeated.\n\n" +
                "Not to worry, You will be given hints in the form of smaller circles.\n\n" +
                "Little BLACK circles represent that you have a CORRECT color in the CORRECT place\n" +
                "Little WHITE circles represent that you have a CORRECT color in the WRONG place.\n\n" +
                "Can you become the Mastermind?" +
                "")
        builder.show()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Confirmation")
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            // User clicked Yes, perform exit action
            mediaPlayer.stop()
            startActivity(Intent(this, TitleActivity::class.java)) // Reload the Title Screen

        }
        builder.setNegativeButton("No") { _, _ ->
            // User clicked No, do nothing
        }
        builder.show()
    }

    private fun changeCircleColor(color: Drawable, rowPosition: Int, circlePosition: Int, circlesContainer: LinearLayout) {
        val circleRow = circlesContainer.getChildAt(rowPosition) as LinearLayout
        val circle = circleRow.getChildAt(circlePosition) as ImageView

        // Create a GradientDrawable with shape oval
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setStroke(2, Black.toArgb())
        gradientDrawable.setColorFilter(getColorFromDrawable(color), PorterDuff.Mode.SRC_IN)

        // Set the GradientDrawable as the background of the circle ImageView
        circle.setImageDrawable(gradientDrawable)

        colorList.add(color)
        currentColumn++
    }

    private fun getColorFromDrawable(drawable: Drawable): Int {
        // Extract color from the Drawable
        if (drawable is ColorDrawable) {
            return drawable.color
        } else if (drawable is GradientDrawable) {
            val colorArray = drawable.colors
            if (colorArray != null && colorArray.isNotEmpty()) {
                return colorArray[0]
            }
        }
        return -1
    }


    private fun makeGuess(circlesContainer: LinearLayout) {
        if(colorList.size != 4) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Too Few!")
            builder.setMessage("You must enter 4 colors.")
            builder.show()
            clearCircles(circlesContainer)
        } else if(currentRow == numCircleRows - 1) {
            mediaPlayer.stop()
            val builder = AlertDialog.Builder(this)
            MediaPlayer.create(this, R.raw.aww).start()
            builder.setTitle("Bummer...you ran out of guesses.")
            builder.setMessage("Would you like to try again?")
            builder.setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(this, MastermindActivity::class.java))
            }
            builder.setNegativeButton("No") { _, _ ->
                startActivity(Intent(this, TitleActivity::class.java))
            }
            builder.show()
            return
        } else {
            circlesContainer.getChildAt(currentRow).background = null
            val feedbackResult = mastermindGame.guess(colorList)
            updateFeedbackCircles(feedbackResult, (circlesContainer.getChildAt(currentRow) as LinearLayout).getChildAt(4) as LinearLayout)
            if(feedbackResult.isGuessCorrect()) {
                val builder = AlertDialog.Builder(this)
                mediaPlayer.stop()
                MediaPlayer.create(this, R.raw.tada).start()
                builder.setTitle("Congrats! You won!")
                builder.setMessage("Would you like to play again?")
                builder.setPositiveButton("Yes") { _, _ ->
                    startActivity(Intent(this, MastermindActivity::class.java))
                }
                builder.setNegativeButton("No") { _, _ ->
                    startActivity(Intent(this, TitleActivity::class.java))
                }
                builder.show()
                return
            }
            currentRow++
            currentColumn = 0
            circlesContainer.getChildAt(currentRow).setBackgroundColor(currentRowBackground)
            colorList.clear()
        }
    }

    private fun clearCircles(circlesContainer: LinearLayout) {

        // Iterate through all circles in the current row
        for (circle in 0 until numCirclesPerRow) {
            changeCircleColor(getEmptyCircleDrawable(), currentRow, circle, circlesContainer)
        }
        colorList.clear()
        currentColumn = 0
    }

    private fun createCircleRows(numCircleRows: Int, numCirclesPerRow: Int) {
        val circlesContainer = findViewById<LinearLayout>(R.id.circlesContainer)
        for (i in 0 until numCircleRows) {
            val circleRow = LinearLayout(this)
            circleRow.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            circleRow.orientation = LinearLayout.HORIZONTAL

            for (j in 1..numCirclesPerRow) {
                val circle = ImageView(this)
                circle.layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.circle_size),
                    resources.getDimensionPixelSize(R.dimen.circle_size)
                )
                circle.background = getEmptyCircleDrawable()
                circleRow.addView(circle)
            }

            // Add a layout for feedback circles
            val feedbackLayout = LinearLayout(this)
            feedbackLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            feedbackLayout.orientation = LinearLayout.VERTICAL

            for (i in 1..2) {
                // Create a new row for each iteration
                val feedbackRow = LinearLayout(this)
                feedbackRow.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                feedbackRow.orientation = LinearLayout.HORIZONTAL

                for (j in 1..2) {
                    // Create feedback circle
                    val feedbackCircle = ImageView(this)
                    feedbackCircle.layoutParams = LinearLayout.LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.feedback_circle_size),
                        resources.getDimensionPixelSize(R.dimen.feedback_circle_size)
                    )
                    feedbackCircle.background = getFeedbackCircleDrawable()

                    // Add the feedback circle to the current row
                    feedbackRow.addView(feedbackCircle)
                }

                // Add the row to the main feedback layout
                feedbackLayout.addView(feedbackRow)
            }

            circleRow.addView(feedbackLayout)
            circlesContainer.addView(circleRow)
        }
    }

    private fun updateFeedbackCircles(feedbackResult: FeedbackResult, feedbackLayout: LinearLayout) {

        val blackCircleDrawable = getBlackCircleDrawable()
        val whiteCircleDrawable = getWhiteCircleDrawable()

        var numBlackPegs = feedbackResult.blackPegs
        var numWhitePegs = feedbackResult.whitePegs

        // Iterate through each row in the feedback layout
        for (i in 0 until feedbackLayout.childCount) {
            val feedbackRow = feedbackLayout.getChildAt(i) as LinearLayout

            // Iterate through each circle in the current row
            for (j in 0 until feedbackRow.childCount) {
                val feedbackCircle = feedbackRow.getChildAt(j) as ImageView

                // Update the color based on the number of black and white pegs
                when {
                    numBlackPegs > 0 -> {
                        feedbackCircle.background = blackCircleDrawable
                        numBlackPegs--
                    }
                    numWhitePegs > 0 -> {
                        feedbackCircle.background = whiteCircleDrawable
                        numWhitePegs--
                    }
                }
            }
        }
    }

    private fun getEmptyCircleDrawable(): Drawable {
        return ContextCompat.getDrawable(this, R.drawable.circle_empty)!!
    }

    private fun getFeedbackCircleDrawable(): Drawable {
        return ContextCompat.getDrawable(this, R.drawable.feedback_circle)!!
    }

    private fun getBlackCircleDrawable(): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setColor(Color.BLACK)

        return gradientDrawable
    }

    private fun getWhiteCircleDrawable(): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setColor(Color.WHITE)

        return gradientDrawable
    }



}
