package az.zero.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import az.zero.az_edit_text.AZEditText
import az.zero.xmlplayground.R

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edPhone = findViewById<AZEditText>(R.id.ed_phone)

        edPhone.setOnStartDrawableClick {
            Toast.makeText(this, "start drawable clicked", Toast.LENGTH_SHORT).show()
        }

        edPhone.setOnStartTextClick {
            Toast.makeText(this, "start text clicked", Toast.LENGTH_SHORT).show()
        }

        edPhone.setOnEndDrawableClick {
            if (edPhone.errorText == null) edPhone.errorText = "Test error message"
            else edPhone.errorText = null
            Toast.makeText(this, "end drawable clicked", Toast.LENGTH_SHORT).show()
        }

        edPhone.setOnTextChange { text, length ->
            Log.d(TAG, "text changed=> length:$length text:$text\n")
        }

    }
}