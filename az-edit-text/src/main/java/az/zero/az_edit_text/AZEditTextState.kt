package az.zero.az_edit_text

import android.os.Parcelable
import android.view.Gravity
import android.view.View
import az.zero.az_edit_text.AZEditText.Companion.DEFAULT_TEXT_INPUT_TYPE
import az.zero.az_edit_text.AZEditText.Companion.ERROR_TEXT_COLOR
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class AZEditTextState(
    val parcel: Parcelable?,
    val text: String = "",
    val hint: String = "",
    val startText: String = "",
    val errorText: String? = "",
    val maxLength: Int? = null,
    val inputType: Int = DEFAULT_TEXT_INPUT_TYPE,
    val handlePassword: Boolean = false,
    val passwordVisible: Boolean = false,

    val textSize: Int = -1,
    val isEdEnabled: Boolean = true,
    val activeStrokeColor: Int = AZEditText.CONTAINER_BG_COLOR,
    val inactiveStrokeColor: Int = AZEditText.CONTAINER_BG_COLOR,
    val errorColor: Int = AZEditText.CONTAINER_STROKE_ERROR_COLOR,
    val containerBackgroundColor: Int = AZEditText.CONTAINER_BG_COLOR,
    val containerRadius: Float = 0f,
    val strokeWidth: Int = 0,
    val isContainerActive: Boolean = false,

    val edTextHeight: Int,
    val textGravity: Int = Gravity.CENTER or Gravity.START,
    val errorTextColor: Int = ERROR_TEXT_COLOR,


//    val passwordShownDrawable: @RawValue Drawable? = null,
//    val passwordHiddenDrawable: @RawValue Drawable? = null,
) : Parcelable, View.BaseSavedState(parcel)
