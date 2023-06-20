package com.semid.blurView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * ConstraintLayout that blurs its underlying content.
 * Can have children and draw them over blurred background.
 */
class BlurView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    var blurController: BlurController = NoOpController()
    var isBlurEnabled = true
        private set

    @ColorInt
    private var overlayColor = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BlurView)

        overlayColor =
            a.getColor(R.styleable.BlurView_blurOverlayColor, BlockingBlurController.TRANSPARENT)

        a.recycle()
    }

    override fun draw(canvas: Canvas?) {
        val shouldDraw = blurController.draw(canvas)
        if (shouldDraw) {
            super.draw(canvas)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        blurController.updateBlurViewSize()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        blurController.setBlurAutoUpdate(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isHardwareAccelerated) {
            Log.e(TAG, "BlurView can't be used in not hardware-accelerated window!")
        } else {
            blurController.setBlurAutoUpdate(true)
        }
    }

    /**
     * @param rootView root to start blur from.
     * Can be Activity's root content layout (android.R.id.content)
     * or (preferably) some of your layouts. The lower amount of Views are in the root, the better for performance.
     * @return [BlurView] to setup needed params.
     */
    fun setupWith(rootView: ViewGroup): BlurViewFacade {
        val blurController: BlurController = BlockingBlurController(this, rootView, overlayColor)
        this.blurController.destroy()
        this.blurController = blurController
        return blurController
    }
    // Setters duplicated to be able to conveniently change these settings outside of setupWith chain
    /**
     * @see BlurViewFacade.setBlurRadius
     */
    fun setBlurRadius(radius: Float): BlurViewFacade? {
        return blurController.setBlurRadius(radius)
    }

    /**
     * @see BlurViewFacade.setOverlayColor
     */
    fun setOverlayColor(@ColorInt overlayColor: Int): BlurViewFacade? {
        this.overlayColor = overlayColor
        return blurController.setOverlayColor(overlayColor)
    }

    /**
     * @see BlurViewFacade.setBlurAutoUpdate
     */
    fun setBlurAutoUpdate(enabled: Boolean): BlurViewFacade? {
        return blurController.setBlurAutoUpdate(enabled)
    }

    /**
     * @see BlurViewFacade.setBlurEnabled
     */
    fun setBlurEnabled(enabled: Boolean): BlurViewFacade? {
        isBlurEnabled = enabled

        return blurController.setBlurEnabled(enabled)
    }

    companion object {
        private val TAG = BlurView::class.java.simpleName
    }
}