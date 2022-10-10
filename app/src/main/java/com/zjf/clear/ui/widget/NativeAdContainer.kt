package com.zjf.clear.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * create by colin
 * 2022/3/2
 */
class NativeAdContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var needIntercept = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return needIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (needIntercept)
            return true
        return super.onTouchEvent(event)
    }
}