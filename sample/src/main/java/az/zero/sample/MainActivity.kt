package az.zero.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import az.zero.az_edit_text.AZEditText
import az.zero.xmlplayground.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edPassword = findViewById<AZEditText>(R.id.ed3)
        edPassword.setOnEndDrawableClick {
            Log.e("setOnEndDrawableClick", "clicked")
        }

    }
}