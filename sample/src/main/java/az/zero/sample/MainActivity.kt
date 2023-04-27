package az.zero.sample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import az.zero.az_edit_text.AZEditText
import az.zero.xmlplayground.R

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edPhone = findViewById<AZEditText>(R.id.ed_phone)
        var toast: Toast? = null

        edPhone.setOnStartDrawableClick {
            toast = makeText(toast, "start drawable clicked")
            toast?.show()
        }

        edPhone.setOnStartTextClick {
            toast = makeText(toast, "start text clicked")
            toast?.show()
        }

        edPhone.setOnEndDrawableClick {
            if (edPhone.errorText.isNullOrBlank()) edPhone.errorText = "Test error message"
            else edPhone.errorText = null
            toast = makeText(toast, "end drawable clicked")
            toast?.show()
        }

        edPhone.setOnTextChange { text, length ->
            Log.d(TAG, "text changed=> length:$length text:$text\n")
        }

    }

    private fun Activity.makeText(toast: Toast?, text: String): Toast? {
        toast?.cancel()
        return makeText(this, text, Toast.LENGTH_SHORT)
    }
}