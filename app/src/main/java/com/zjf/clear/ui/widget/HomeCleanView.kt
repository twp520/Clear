package com.zjf.clear.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.zjf.clear.R

/**
 * create by colin
 * 2022/8/14
 */
class HomeCleanView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val wave1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.wave1)

    private lateinit var waveBitmap: Bitmap
    private lateinit var waveCanvas: Canvas

    private lateinit var circleBitmap: Bitmap
    private lateinit var circleCanvas: Canvas

    private lateinit var resultBitmap: Bitmap
    private lateinit var resultCanvas: Canvas

    private val mode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    private val waveMatrix = Matrix()

    private val mPaint = Paint().apply {
        color = Color.parseColor("#5994CC")
        style = Paint.Style.STROKE
        strokeWidth = SizeUtils.dp2px(2f).toFloat()
        isAntiAlias = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        waveBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        waveCanvas = Canvas(waveBitmap)

        circleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        circleCanvas = Canvas(circleBitmap)

        resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        resultCanvas = Canvas(resultBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        waveMatrix.reset()
        waveMatrix.postTranslate(0f, (height - wave1.height).toFloat())
        waveMatrix.postScale(width.toFloat() / wave1.width, 1f)
        waveCanvas.drawBitmap(wave1, waveMatrix, null)

        mPaint.style = Paint.Style.FILL
        circleCanvas.drawCircle(width / 2f, height / 2f, width / 2f - mPaint.strokeWidth, mPaint)


        mPaint.xfermode = null
        resultCanvas.drawBitmap(waveBitmap, 0f, 0f, mPaint)
        mPaint.xfermode = mode
        resultCanvas.save()
        resultCanvas.drawBitmap(circleBitmap, 0f, 0f, mPaint)
        resultCanvas.restore()
        mPaint.xfermode = null
        canvas.drawBitmap(resultBitmap, 0f, 0f, null)
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(width / 2f, height / 2f, width / 2f - mPaint.strokeWidth, mPaint)

    }


}