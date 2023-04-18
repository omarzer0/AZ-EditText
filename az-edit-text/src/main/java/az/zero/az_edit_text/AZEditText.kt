package az.zero.az_edit_text

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import az.zero.az_edit_text.common.getResourceColor
import az.zero.az_edit_text.common.getResourceDrawable
import com.google.android.material.card.MaterialCardView

class AZEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var cvContainer: MaterialCardView
    private lateinit var edMainText: EditText
    private lateinit var tvStartText: TextView

    private lateinit var ivStart: ImageView
    private lateinit var ivEnd: ImageView
    private lateinit var tvError: TextView

    var text: String = ""
        set(value) {
            field = value
            edMainText.setText(value)
            edMainText.setSelection(value.length)
        }
        get() = edMainText.text?.toString() ?: ""

    var hint: String = ""
        set(value) {
            field = value
            edMainText.hint = value
        }

    var startText: String = ""
        set(value) {
            field = value
            tvStartText.text = value
            tvStartText.isVisible = value.isNotBlank()
        }

    var errorText: String? = null
        set(value) {
            field = value
            tvError.isVisible = !value.isNullOrBlank()
            tvError.text = value
        }

    var startDrawable: Drawable? = null
        set(value) {
            field = value
            ivStart.isVisible = value != null
            ivStart.setImageDrawable(value)
        }

    var endDrawable: Drawable? = null
        set(value) {
            field = value
            ivEnd.isVisible = value != null
            if (handlePassword) ivEnd.setImageDrawable(
                if (passwordVisible) ContextCompat.getDrawable(context, R.drawable.ic_eye_closed)
                else ContextCompat.getDrawable(context, R.drawable.ic_eye_opened)
            )
            else ivEnd.setImageDrawable(value)
        }

    var maxLength: Int? = null
        set(value) {
            field = value
            edMainText.filters = arrayOf(InputFilter.LengthFilter(value ?: Int.MAX_VALUE))
        }

    var inputType: Int = DEFAULT_TEXT_INPUT_TYPE
        set(value) {
            field = value
            edMainText.inputType = value
        }

    var handlePassword: Boolean = false
        set(value) {
            field = value
            if (value) {
                ivEnd.isVisible = true
                //TODO test these lines
                ivEnd.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_eye_closed))
                //             if (passwordVisible) ContextCompat.getDrawable(context, R.drawable.ic_eye_closed)
                //                else ContextCompat.getDrawable(context, R.drawable.ic_eye)
            }
        }

    private var passwordVisible: Boolean = false

    var textSize: Int = 0
        set(value) {
            field = value
            if (textSize == -1) return
            else edMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, value.toFloat())
        }

    var isEdEnabled: Boolean = true
        set(value) {
            field = value
            edMainText.isEnabled = value
        }

    var activeStrokeColor: Int = ContextCompat.getColor(this.context, CONTAINER_BG_COLOR)
        set(value) {
            field = value
            handleStrokeColor()
        }

    var inactiveStrokeColor: Int = CONTAINER_BG_COLOR
        set(value) {
            field = value
            handleStrokeColor()
        }

    var errorStrokeColor: Int = CONTAINER_STROKE_ERROR_COLOR
        set(value) {
            field = value
            handleStrokeColor()
        }


    var containerBackgroundColor: Int = CONTAINER_BG_COLOR
        set(value) {
            field = value
            cvContainer.setCardBackgroundColor(value)
        }

    var containerRadius: Float = 0f
        set(value) {
            field = value
            cvContainer.radius = value
        }

    var strokeWidth: Int = 0
        set(value) {
            field = value
            cvContainer.strokeWidth = value
        }


    private var isContainerActive: Boolean = false
        set(value) {
            field = value
            cvContainer.isActivated = value
            ivStart.isActivated = value
            ivEnd.isActivated = value

            handleStrokeColor(
                isActive = value,
                isError = !errorText.isNullOrBlank(),
            )
        }

    var edTextHeight: Int = resources.getDimensionPixelSize(R.dimen.default_ed_text_size)
        set(value) {
            field = value
            cvContainer.layoutParams = LayoutParams(MATCH_PARENT, value)
        }

    var textGravity: Int = Gravity.CENTER or Gravity.START
        set(value) {
            field = value
            edMainText.gravity = value
        }

    var errorTextColor: Int = ERROR_TEXT_COLOR
        set(value) {
            field = value
            tvError.setTextColor(value)
        }

    var passwordShownDrawable = context.getResourceDrawable(R.drawable.ic_eye_opened)
        set(value) {
            field = value
            handleSetPasswordEndDrawable()
        }

    var passwordHiddenDrawable = context.getResourceDrawable(R.drawable.ic_eye_closed)
        set(value) {
            field = value
            handleSetPasswordEndDrawable()
        }

    fun setOnStartTextClick(action: (String) -> Unit) {
        tvStartText.setOnClickListener { action(tvStartText.text.toString()) }
    }

    fun setOnStartDrawableClick(action: () -> Unit) {
        ivStart.setOnClickListener { action() }
    }

    fun setOnEndDrawableClick(action: () -> Unit) {
        ivEnd.setOnClickListener {
            if (handlePassword) handleIvEndClickIfHandlePassword()
            action()
        }
    }

    fun setOnTextChange(action: (text: String, length: Int) -> Unit) {
        edMainText.doOnTextChanged { text, _, _, _ ->
            action(text?.toString() ?: "", text?.length ?: 0)
        }
    }

    init {
        setUp(attrs, defStyle)
    }

    private fun setUp(attrs: AttributeSet? = null, defStyleAttr: Int? = null) {
        val root = LayoutInflater.from(context).inflate(R.layout.include_custom_ed, this, true)
        cvContainer = root.findViewById(R.id.ed_container)
        edMainText = cvContainer.findViewById(R.id.ed_main)
        ivStart = cvContainer.findViewById(R.id.iv_start_drawable)
        ivEnd = cvContainer.findViewById(R.id.iv_end_drawable)
        tvStartText = cvContainer.findViewById(R.id.tv_start_text)
        tvError = root.findViewById(R.id.tv_error_text)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AZEditText)
            text = typedArray.getText(R.styleable.AZEditText_text)?.toString() ?: ""
            hint = typedArray.getText(R.styleable.AZEditText_hint)?.toString() ?: ""
            startText = typedArray.getText(R.styleable.AZEditText_startText)?.toString() ?: ""
            errorText = typedArray.getText(R.styleable.AZEditText_errorText)?.toString() ?: ""
            startDrawable = typedArray.getDrawable(R.styleable.AZEditText_startDrawable)
            endDrawable = typedArray.getDrawable(R.styleable.AZEditText_endDrawable)
            endDrawable = typedArray.getDrawable(R.styleable.AZEditText_endDrawable)
            maxLength = typedArray.getInt(R.styleable.AZEditText_maxLength, Int.MAX_VALUE)
            handlePassword = typedArray.getBoolean(R.styleable.AZEditText_handlePassword, false)
            inputType = typedArray.getInt(R.styleable.AZEditText_inputType, DEFAULT_TEXT_INPUT_TYPE)
            isEdEnabled = typedArray.getBoolean(R.styleable.AZEditText_isEdEnabled, true)
            textSize = typedArray.getDimensionPixelSize(R.styleable.AZEditText_az_textSize, -1)
            containerRadius = typedArray.getDimension(R.styleable.AZEditText_containerRadius, 0f)
            strokeWidth =
                typedArray.getDimensionPixelSize(R.styleable.AZEditText_containerStrokeWidth, 0)

            passwordShownDrawable =
                typedArray.getDrawable(R.styleable.AZEditText_passwordShownDrawable)

            passwordHiddenDrawable =
                typedArray.getDrawable(R.styleable.AZEditText_passwordHiddenDrawable)

            val defaultColor = context.getResourceColor(CONTAINER_BG_COLOR)
            val defaultErrorStrokeColor = context.getResourceColor(CONTAINER_STROKE_ERROR_COLOR)

            containerBackgroundColor = typedArray.getColor(
                R.styleable.AZEditText_containerBackgroundColor, defaultColor
            )

            activeStrokeColor =
                typedArray.getColor(R.styleable.AZEditText_activeStrokeColor, defaultColor)

            inactiveStrokeColor =
                typedArray.getColor(R.styleable.AZEditText_inactiveStrokeColor, defaultColor)

            errorStrokeColor = typedArray.getColor(
                R.styleable.AZEditText_errorStrokeColor,
                defaultErrorStrokeColor
            )

            edTextHeight = typedArray.getDimensionPixelSize(
                R.styleable.AZEditText_edTextHeight,
                resources.getDimensionPixelSize(R.dimen.default_ed_text_size)
            )

            textGravity = typedArray.getInt(
                R.styleable.AZEditText_textGravity,
                Gravity.CENTER or Gravity.START
            )

            errorTextColor = typedArray.getColor(
                R.styleable.AZEditText_errorStrokeColor, defaultErrorStrokeColor
            )

            ivEnd.setOnClickListener {
                if (handlePassword) handleIvEndClickIfHandlePassword()
            }

            handleIsContainerActive()

            handleStrokeColor()

            typedArray.recycle()
        }
    }


    private fun handleIsContainerActive() {
        isContainerActive = edMainText.hasFocus() || !edMainText.text.isNullOrBlank()
        edMainText.setOnFocusChangeListener { _, _ ->
            isContainerActive = edMainText.hasFocus() || !edMainText.text.isNullOrBlank()
        }
    }


    private fun handleStrokeColor(
        isError: Boolean = !this.errorText.isNullOrBlank(),
        isActive: Boolean = this.isContainerActive,
        @ColorInt errorColor: Int? = this.errorStrokeColor,
        @ColorInt activeStrokeColor: Int? = this.activeStrokeColor,
        @ColorInt inactiveStrokeColor: Int? = this.inactiveStrokeColor,
    ) {
        cvContainer.apply {
            when {
                isError -> {
                    errorColor?.let { strokeColor = it }
                }

                isActive -> {
                    activeStrokeColor?.let { strokeColor = it }
                }

                else -> {
                    inactiveStrokeColor?.let { strokeColor = it }
                }

            }
        }
    }

    private fun handleIvEndClickIfHandlePassword() {
        edMainText.apply {
            transformationMethod = if (passwordVisible) {
                ivEnd.setImageDrawable(
                    passwordHiddenDrawable ?: context.getResourceDrawable(R.drawable.ic_eye_closed)
                )
                PasswordTransformationMethod()
            } else {
                ivEnd.setImageDrawable(
                    passwordShownDrawable ?: context.getResourceDrawable(R.drawable.ic_eye_opened)
                )
                HideReturnsTransformationMethod()
            }
            setSelection(this.length())
        }
        passwordVisible = !passwordVisible
    }

    private fun handleSetPasswordEndDrawable() {
        if (!handlePassword) return
        if (isVisible) {
            ivEnd.setImageDrawable(
                passwordHiddenDrawable ?: context.getResourceDrawable(R.drawable.ic_eye_closed)
            )
        } else {
            ivEnd.setImageDrawable(
                passwordShownDrawable ?: context.getResourceDrawable(R.drawable.ic_eye_opened)
            )
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable {
        val parcel = super.onSaveInstanceState()
        return AZEditTextState(
            parcel = parcel,
            text = this.text,
            hint = this.hint,
            startText = this.startText,
            errorText = this.errorText,
            maxLength = this.maxLength,
            inputType = this.inputType,
            handlePassword = this.handlePassword,
            passwordVisible = this.passwordVisible,
            textSize = this.textSize,
            isEdEnabled = this.isEdEnabled,
            activeStrokeColor = this.activeStrokeColor,
            inactiveStrokeColor = this.inactiveStrokeColor,
            errorColor = this.errorStrokeColor,
            containerBackgroundColor = this.containerBackgroundColor,
            containerRadius = this.containerRadius,
            strokeWidth = this.strokeWidth,
            isContainerActive = this.isContainerActive,
            edTextHeight = this.edTextHeight,
            textGravity = this.textGravity,
            errorTextColor = this.errorTextColor,
//            passwordShownDrawable = this.passwordShownDrawable,
//            passwordHiddenDrawable = this.passwordHiddenDrawable,
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is AZEditTextState) {
            this.text = state.text
            this.hint = state.hint
            this.startText = state.startText
            this.errorText = state.errorText
            this.maxLength = state.maxLength
            this.inputType = state.inputType
            this.handlePassword = state.handlePassword
            this.passwordVisible = state.passwordVisible
            this.textSize = state.textSize
            this.isEdEnabled = state.isEdEnabled
            this.activeStrokeColor = state.activeStrokeColor
            this.inactiveStrokeColor = state.inactiveStrokeColor
            this.errorStrokeColor = state.errorColor
            this.containerBackgroundColor = state.containerBackgroundColor
            this.containerRadius = state.containerRadius
            this.strokeWidth = state.strokeWidth
            this.isContainerActive = state.isContainerActive
            this.edTextHeight = edTextHeight
            this.textGravity = textGravity
            this.errorTextColor = errorTextColor
            this.passwordShownDrawable = passwordShownDrawable
            this.passwordHiddenDrawable = passwordHiddenDrawable
        }
        super.onRestoreInstanceState(state)
    }

    companion object {
        const val DEFAULT_TEXT_INPUT_TYPE = 0x00000001
        val CONTAINER_BG_COLOR = R.color.light_gray
        const val CONTAINER_STROKE_ERROR_COLOR = android.R.color.holo_red_dark
        const val ERROR_TEXT_COLOR = android.R.color.holo_red_dark
    }

}