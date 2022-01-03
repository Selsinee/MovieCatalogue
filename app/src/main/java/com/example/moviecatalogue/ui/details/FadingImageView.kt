package com.example.moviecatalogue.ui.details

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue


/**
 * Created by Seline on 22/12/2021 15:26
 */
class FadingImageView : androidx.appcompat.widget.AppCompatImageView {
    private var mFadeSide: FadeSide? = null
    private var c: Context

    enum class FadeSide {
        RIGHT_SIDE, LEFT_SIDE, TOP_SIDE, BOTTOM_SIDE
    }

    constructor(c: Context, attrs: AttributeSet?, defStyle: Int) : super(c, attrs, defStyle) {
        this.c = c
        init()
    }

    constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        this.c = c
        init()
    }

    constructor(c: Context) : super(c) {
        this.c = c
        init()
    }

    private fun init() {
        // Enable horizontal fading
        this.isVerticalFadingEdgeEnabled = true
        // Apply default fading length
        setEdgeLength(100)
        // Apply default side
        setFadeDirection(FadeSide.BOTTOM_SIDE)
    }

    fun setFadeDirection(side: FadeSide?) {
        mFadeSide = side
    }

    fun setEdgeLength(length: Int) {
        this.setFadingEdgeLength(getPixels(length))
    }

    override fun getLeftFadingEdgeStrength(): Float {
        return if (mFadeSide == FadeSide.LEFT_SIDE) 1.0f else 0.0f
    }

    override fun getRightFadingEdgeStrength(): Float {
        return if (mFadeSide == FadeSide.RIGHT_SIDE) 1.0f else 0.0f
    }

    override fun getBottomFadingEdgeStrength(): Float {
        return if (mFadeSide == FadeSide.BOTTOM_SIDE) 1.0f else 0.0f
    }

    override fun getTopFadingEdgeStrength(): Float {
        return if (mFadeSide == FadeSide.TOP_SIDE) 1.0f else 0.0f
    }

    override fun hasOverlappingRendering(): Boolean {
        return true
    }

    override fun onSetAlpha(alpha: Int): Boolean {
        return false
    }

    private fun getPixels(dipValue: Int): Int {
        val r: Resources = c.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue.toFloat(), r.displayMetrics
        ).toInt()
    }
}