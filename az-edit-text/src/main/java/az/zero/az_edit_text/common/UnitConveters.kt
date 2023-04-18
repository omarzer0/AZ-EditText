package az.zero.az_edit_text.common

import android.content.res.Resources
import androidx.fragment.app.Fragment

val Float.pxToDp get() = this / Resources.getSystem().displayMetrics.density
val Int.dpToPx get() = this * Resources.getSystem().displayMetrics.density.toInt()

val Float.dpToPx get() = this * Resources.getSystem().displayMetrics.density
val Int.pxToDp get() = this / Resources.getSystem().displayMetrics.density.toInt()
