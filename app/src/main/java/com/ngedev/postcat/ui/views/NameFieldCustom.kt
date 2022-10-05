package com.ngedev.postcat.ui.views

import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ngedev.postcat.R

class NameFieldCustom : AppCompatEditText, View.OnTouchListener {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

    }


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Name"
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        inputType = InputType.TYPE_CLASS_TEXT
        background = ContextCompat.getDrawable(context, R.drawable.rounded_text_field)
    }

}