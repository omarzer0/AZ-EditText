package az.zero.az_edit_text.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

fun Context.getResourceColor(color: Int): Int = ContextCompat.getColor(this, color)
fun Context.getResourceDrawable(@DrawableRes resourceId: Int): Drawable? =
    ContextCompat.getDrawable(this, resourceId)


fun Context.getResourceDrawableIfNotNull(@DrawableRes resourceId: Int?): Drawable? =
    if (resourceId == null) null
    else ContextCompat.getDrawable(this, resourceId)

@ColorInt
fun Context.getThemeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(intArrayOf(themeAttrId)).use {
        it.getColor(0, Color.MAGENTA)
    }
}

@ColorInt
fun Context.getColorCompact(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}