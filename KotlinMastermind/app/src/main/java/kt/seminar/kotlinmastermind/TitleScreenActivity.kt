package kt.seminar.kotlinmastermind

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class TitleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)
        supportActionBar?.hide() // Set an empty string to remove the title

        var imageView = findViewById<ImageView>(R.id.backgroundImage)
        val originalBitmap = (imageView.drawable as BitmapDrawable).bitmap
        val blurredBitmap: Bitmap = BlurBuilder.blur(this, originalBitmap)

        imageView.setImageBitmap(blurredBitmap)

        val startGameButton = findViewById<Button>(R.id.startGameButton)
        val howToPlayButton = findViewById<Button>(R.id.howToPlayButton)

        startGameButton.setOnClickListener {
            val intent = Intent(this, MastermindActivity::class.java)
            startActivity(intent)
        }
        howToPlayButton.setOnClickListener {
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
    }
}

